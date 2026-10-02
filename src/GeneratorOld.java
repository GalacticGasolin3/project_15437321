import DatapackComponents.Component;
import DatapackComponents.Folder;
import DatapackComponents.Function;
import nodes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

//uses nodes from the AST to generate the mcfunction file
public class GeneratorOld {
    private HashSet<String> funcNames;
    private Stack<HashMap<String,Integer>> stack;
    private StringBuilder compiledCode;
    private String namespace;
    private String functionScope;
    private ArrayList<Component> functions;
    private int stackSize;
    private int scope;

    public GeneratorOld(String namespace) {
        this.namespace = namespace;
        funcNames = new HashSet<>();
        compiledCode = new StringBuilder();
        functions = new ArrayList<>();
        stack = new Stack<>();
        stackSize = 0;
        functionScope = "";
        scope = 0;
    }

    public String getCode() {
        return compiledCode.toString();
    }

    //get all datapack functions.
    public Folder getFunctions() {
        return new Folder(functions, "function");
    }

    public void generateCodeFromTree(RootNode root) {
        //first pass to find all existing functions.
        for (int i = 0; i < root.length(); i++) {
            if (root.get(i).getType().equals("FunctionCommandNode"))
                funcNames.add(((FunctionNode) root.get(i)).getName());
        }

        //process all nodes.
        for (int i = 0; i < root.length(); i++) {
            Node cur = root.get(i);
            processNode(cur);
        }
    }


    public void processFunction(FunctionNode function) {
        //save current code and refresh later.
        String currentCode = compiledCode.toString();
        String functionName = function.getName();

        compiledCode.delete(0,compiledCode.length());

        increaseScope();
        ParamNode params = function.getParams();

        //add all the parameters to the scope.
        for (int i = 0; i < params.length(); i++) {
            //check for IdentifierNodes (all parameters should be identifiers.)
            ExpressionNode cur = params.get(i);
            if (cur.getClass() != ExpressionNodeIdentifier.class)
                throw new RuntimeException("Invalid Parameter: " + cur);
            String paramName = ((ExpressionNodeIdentifier) cur).getValue();

            saveVar(paramName);
            addCodeLine("data modify storage mc_script:memory " + getStoreLocFromScope() + paramName + " set from storage " + namespace + ":functions " + functionName + ".var" + i);
        }


        //iterate through each node in the root node
        for (int i = 0; i < function.length(); i++) {
            Node cur = function.get(i);

            processNode(cur);
        }
        decreaseScope();

        //add the function.
        functions.add(new Function(functionName, compiledCode.toString()));

        //add the non function code back to the StringBuilder.
        compiledCode = new StringBuilder(currentCode);
    }

    public void processNode(Node node) {
        switch(node.getType()) {
            case "FunctionCommandNode" -> processFunction((FunctionNode) node);
            case "DeclarationNode" -> saveReferenceToMemory((DeclarationNode) node);
            case "KillCommandNode" -> processKillNode((KillNode) node);
            case "SayCommandNode" -> processSayNode((SayNode) node);
            case "SummonCommandNode" -> processSummonNode((SummonNode) node);
            case "ExpressionNodeSingletonCall" -> processFunctionCall((ExpressionNodeCall) node);
            case "ForkCommandNode" -> processForkCommand((ForkCommandNode) node);
            case "ReturnNode" -> processReturn((ReturnNode) node);
        }
    }


    public void processReturn(ReturnNode node) {
        processExpression(node.getReturnValue());
        popFromStack("function.result");
    }

    //process a forking command.
    //Forking command refer to "execute as <selector>" commands
    //defaults to @s.
    // the method "ensureRootTarget(<cmd name>)" called in each process<command> method makes sure the storage containing the selector is properly modified.
    public void processForkCommand(ForkCommandNode forkCommand) {
        processExpression(forkCommand.getSelector());
        popFromStack("root_perspective.cur");
        CommandNode cmd = forkCommand.getCmd();
        processNode(cmd);
    }


    //processes a function call (no return statements yet.)
    //TODO: Add return statements.
    public void processFunctionCall(ExpressionNodeCall function) {

        String functionName = function.getValue();
        if (!funcNames.contains(functionName))
            throw new RuntimeException("Function \"" + functionName + "\" does not exist");
        ParamNode params = function.getParams();

        for (int i = 0; i < params.length(); i++) {
            //process each parameter and put the parameter into the function variables.
            processExpression(params.get(i));
            popFromStackToStorage(namespace + ":functions " + functionName + ".var" + i);
        }

        addCodeLine("function " + namespace + ":" + functionName + " with storage " + namespace + ":functions " + functionName);
        addCodeLine("data remove storage " + namespace + ":functions " + functionName);
    }

    //processes the "say" node
    public void processSayNode(SayNode node) {
        ensureRootTarget("say");
        ExpressionNode msg = node.getMessage();
        processExpression(msg);
        popFromStack("call.say.msg");
        runCommandWithStorage("say","say");
        deleteCommandParameters("say");
    }

    public void processKillNode(KillNode node) {
        ensureRootTarget("kill");
        ExpressionNode target = node.getTarget();
        processExpression(target);
        popFromStack("call.kill.target");
        runCommandWithStorage("kill","kill");
        deleteCommandParameters("kill");
    }

    //summon call
    public void processSummonNode(SummonNode entity) {
        ensureRootTarget("summon");
        PositionNode position = entity.getPos();
        ExpressionNode nbtData = entity.getNbtData();

        //process entity type and nbt
        processExpression(entity.getEntityType());
        popFromStack("call.summon.entity");
        processExpression(nbtData);
        popFromStack("call.summon.nbt");
        //position
        processPosition(position, "call.summon");

        runCommandWithStorage("summon","summon");
        deleteCommandParameters("summon");
    }

    //adds a new identifier reference to memory for future reference.
    public void saveReferenceToMemory(DeclarationNode node) {
        ExpressionNode varData = node.getData();
        String varName = node.getIdentifier().getName();

        //prevent duplicate declarations
        if (variableInScope(varName))
            throw new RuntimeException("Declared variable " + varName + " already exists.");

        processExpression(varData);
        popFromStack(getStoreLocFromScope() + varName);
        saveVar(varName);
    }
    public void saveVar(String varName) {
        if (scope <= 0)
            throw new RuntimeException("Invalid Scope \"0\"");

        stack.get(scope-1).put(varName,scope);
    }

    //processes expressions
    public void processSingle(ExpressionNodeSingleton single) {
        if (single.getClass() == ExpressionNodeIdentifier.class) {
            //check if variable exists
            String varName = single.getValue();
            if (variableInScope(varName))
                addRegisterToStack(getStoreLocFromScope() + single.getValue());
            else
                throw new RuntimeException("Variable \"" + varName + "\" does not exist.");
        } else if (single.getType().equals("ExpressionNodeSingletonCall")) {
            processFunctionCall((ExpressionNodeCall) single);
            addRegisterToStack("function.result");
        } else {
            addValueToStack(single.getValue());
        }
    }

    public void processExpression(ExpressionNode expr) {
        if (expr.getType().startsWith("ExpressionNodeSingleton")) {
            processSingle((ExpressionNodeSingleton) expr);
            return;
        }
        //process left side
        processExpression(((OperationNode) expr).getLeft());
        //process right side
        processExpression(((OperationNode) expr).getRight());
        popFromStack("math.input.b");
        popFromStack("math.input.a");

        switch ((expr).getType()) {
            case "OperationNodeAdd" -> runMathOperation("add");
            case "OperationNodeMultiply" -> runMathOperation("multiply");
            case "OperationNodeSubtract" -> runMathOperation("subtract");
            case "OperationNodeDivide" -> runMathOperation("divide");
            case "OperationNodeModulo" -> runMathOperation("mod");
        }


        addRegisterToStack("math.result");
    }

    //processes a positional
    //moves the position elements into the x, y, and z paths of the output register
    public void processPosition(PositionNode pos, String output) {
        if (pos.getType().equals("PositionNodeStatic")) {
            processStaticPosition((PositionNodeStatic) pos, output);
        } else {
            //dynamic position.
            processDynamicPosition((PositionNodeDynamic) pos, output);
        }
    }

    public void processStaticPosition(PositionNodeStatic pos, String output) {
        VectorNode vec = pos.getPos();

        processExpression(vec.get(0));
        popFromStack(output + ".x");
        processExpression(vec.get(1));
        popFromStack(output + ".y");
        processExpression(vec.get(2));
        popFromStack(output + ".z");

        moveValueToRegister(output + ".rel","\"\"");
        moveValueToRegister(output + ".location_selector","\"@s\"");
    }

    public void processDynamicPosition(PositionNodeDynamic pos, String output) {
        VectorNode offset = pos.getOffset();
        //move the offset positions to the call storage
        if (offset == null) {
            moveValueToRegister(output + ".x", "0");
            moveValueToRegister(output + ".y", "0");
            moveValueToRegister(output + ".z", "0");
        } else {
            processExpression(offset.get(0));
            popFromStack(output + ".x");
            processExpression(offset.get(1));
            popFromStack(output + ".y");
            processExpression(offset.get(2));
            popFromStack(output + ".z");
        }

        //move selector into pos
        processExpression(pos.getReference());
        popFromStack(output + ".location_selector");

        //rel
        moveValueToRegister(output + ".rel", "\"~\"");
    }



    //helper functions that help write the "assembly"
    public void moveVarToRegister(String register, String varName) {
        if (!variableInScope(varName))
            throw new RuntimeException("Specified Variable " + varName + " does not exist");
        addCodeLine("data modify storage mc_script:memory " + register + " set from storage mc_script:memory variables." + varName);
    }
    public void moveRegisterToRegister(String to, String from) {
        addCodeLine("data modify storage mc_script:memory " + to + " set from storage mc_script:memory " + from);
    }

    public void moveValueToRegister(String register, String val) {
        addCodeLine("data modify storage mc_script:memory " + register + " set value " + val);
    }
    public void addCodeLine(String codeLine) {
        compiledCode.append(codeLine).append("\n");
    }
    // Assumes that 'paramNames' and 'params' ARE THE SAME LENGTH.
    public void runDirectCommand(String cmd, String[] paramNames, String[] params) {
        StringBuilder command = new StringBuilder("function mc_script:command/" + cmd + " {");
        for (int i = 0; i < paramNames.length; i++) {
            command.append(paramNames[i]).append(":").append(params[i]);
        }
        command.append("}");

        addCodeLine(command.toString());
    }
    //commands
    public void runDirectCommand(String cmd, String paramName, String param) {
        runDirectCommand(cmd, new String[] {paramName}, new String[] {param});
    }
    public void runCommandWithStorage(String prefix, String cmd, String register) {
        addCodeLine(prefix + "function mc_script:commands/" + cmd + " with storage mc_script:memory call." + register);
    }
    public void runCommandWithStorage(String cmd, String register) {
        runCommandWithStorage("",cmd,register);
    }

    //math
    public void runMathOperation(String op, String left, String right) {
        addCodeLine("data modify storage mc_script:memory math.input.a set value " + left);
        addCodeLine("data modify storage mc_script:memory math.input.b set value " + right);
        addCodeLine("function mc_script:math/" + op + " with storage mc_script:memory math.input");
    }
    public void runMathOperation(String op, String val) {
        addCodeLine("data modify storage mc_script:memory math.input.val set value " + val);
        addCodeLine("function mc_script:math/" + op + " with storage mc_script:memory math.input");
    }
    public void runMathOperation(String op) {
        addCodeLine("function mc_script:math/" + op + " with storage mc_script:memory math.input");
    }
    //moves from a register to the call stack
    public void addRegisterToStack(String register) {
        addCodeLine("data modify storage mc_script:memory mem.stack append from storage mc_script:memory " + register);
        stackSize++;
    }
    public void addValueToStack(String value) {
        addCodeLine("data modify storage mc_script:memory mem.stack append value " + value);
        stackSize++;
    }
    public void popFromStack(String outputRegister) {
        if (stackSize <= 0)
            throw new RuntimeException("Cannot Retrieve data from Stack: Stack is empty");
        addCodeLine("data modify storage mc_script:memory " + outputRegister + " set from storage mc_script:memory mem.stack[" + (stackSize-1) + "]");
        addCodeLine("data remove storage mc_script:memory mem.stack[" + --stackSize + "]");
    }
    //gives the user full control of the output register
    public void popFromStackToStorage(String outputRegister) {
        if (stackSize <= 0)
            throw new RuntimeException("Cannot Retrieve data from Stack: Stack is empty");
        addCodeLine("data modify storage " + outputRegister + " set from storage mc_script:memory mem.stack[" + (stackSize-1) + "]");
        addCodeLine("data remove storage mc_script:memory mem.stack[" + --stackSize + "]");
    }
    //delete the call storage for a specific command.
    public void deleteCommandParameters(String commandName) {
        addCodeLine("data remove storage mc_script:memory call." + commandName);
        addCodeLine("data remove storage mc_script:memory root_perspective.cur");
    }

    //forking commands
    public void ensureRootTarget(String commandName) {
        addCodeLine("execute unless data storage mc_script:memory root_perspective.cur run data modify storage mc_script:memory root_perspective.cur set value \"@s\" ");
        moveRegisterToRegister("call." + commandName + ".root_selector","root_perspective.cur");
    }


    //scope and variables.
    public void increaseScope() {
        stack.push(new HashMap<>());
        scope++;
    }
    public void decreaseScope(int amount) {
        int finalScope = scope - amount;
        if (finalScope < 0)
            throw new RuntimeException("Scope " + finalScope + " is less than the defined minimum of 0.");

        //deletes all variables from that scope.
        while (scope > finalScope) {
            stack.pop();
            String register = getStoreLocFromScope();
            addCodeLine("data remove storage mc_script:memory " + register.substring(0,register.length()-1));
            scope--;
        }
    }
    public void decreaseScope() {
        decreaseScope(1);
    }


    //gets the name of the storage path to store variables in depending on the scope
    public String getStoreLocFromScope() {
        return "variables." + functionScope + ".scope" + scope + ".";
    }
    //checks if a variable is in scope.
    public boolean variableInScope(String varName, int scope) {
        if (scope < this.scope || scope <= 0)
            return false;
        HashMap<String,Integer> curList = stack.get(scope-1);
        return curList.containsKey(varName) || variableInScope(varName, scope-1);
    }
    public boolean variableInScope(String varName) {
        return variableInScope(varName,scope);
    }
}
