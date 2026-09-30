import nodes.*;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<Token> tokenList;
    private  RootNode root;
    private int index;
    private final int length;

    public Parser(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
        this.index = 0;
        this.length = tokenList.size();
        this.root = new RootNode("Main");
    }

    //get the token at the index
    public Token consume() {
        return tokenList.get(index++);
    }

    //returns the character 'count' ahead of the index(or null) and moves that into the "current" character
    public Token peek(int count) {
        if (index + count >= length)
            return null;
        else
            return tokenList.get(index + count);
    }
    public Token peek() {
        return peek(0);
    }

    //parses tokens into an AST
    public RootNode parse() {
        //goes through each token
        while (peek() != null) {
            //if a new variable is being declared
            if (peek().type == TokenType.STORAGE_DECLARATION) {
                //parse the variable declaration
                root.append(parseDeclaration());
                continue;
            }
            //identifier by itself is a function call
            if (peek().type == TokenType.IDENTIFIER) {
                //if there is a period after the identifier it is a fork call, otherwise it's a function call.
                if (safeCheck(TokenType.PERIOD,1)) {
                    root.append(parseFork());
                    continue;
                }
                root.append(parseFunctionCall());
                continue;
            }

            //A selector by itself is a forking command
            if (peek().type == TokenType.SELECTOR) {
                root.append(parseFork());
                continue;
            }

            if (isCommand()) {
                root.append(parseCommand());
                continue;
            }
            index++;
        }

        return root;
    }

    //checks if the next token is valid for a command call.
    public boolean isCommand() {
        return peek().type == TokenType.FUNCTION || peek().type == TokenType.SAY || peek().type == TokenType.KILL || peek().type == TokenType.SUMMON;
    }


    public CommandNode parseCommand() {
        //if the token is type say
        if (peek().type == TokenType.SAY) {
            return parseSay();
        }
        //if the token is a KILL token
        if (peek().type == TokenType.KILL) {
            return parseKill();
        }
        //if an entity is being summoned.
        if (peek().type == TokenType.SUMMON) {
            return parseEntityInitialization();
        }

        if (safeCheck(TokenType.FUNCTION)) {
            return parseFunction();
        }

        throw new RuntimeException("Invalid Command");
    }


    //parses a forking command.
    public ForkCommandNode parseFork() {
        ExpressionNode selector = parseExpression(TokenType.PERIOD);
        checkFor(TokenType.PERIOD);
        CommandNode cmd = parseCommand();

        return new ForkCommandNode(selector,cmd);
    }


    //parses a function and creates a RootNode for the function.
    public FunctionNode parseFunction() {
        //ensuring the syntax is valid.
        if (!safeCheck(TokenType.FUNCTION))
            throw new RuntimeException("Expected Token of type 'FUNCTION'");
        consume();
        if (!safeCheck(TokenType.IDENTIFIER))
            throw new RuntimeException("Invalid Function Name, \"" + peek() + "\"");
        String functionName = consume().value;

        //params
        ParamNode params = parseParamList();

        //function code.
        if (!safeCheck(TokenType.NBT_OBJECT))
            throw new RuntimeException("Invalid Syntax for a function.");

        String functionCode = consume().value;
        //trim the initial curly brackets.
        String trimmed = functionCode.substring(1,functionCode.length()-1);

        //recursive retokenizing
        return FunctionNode.getFunctionFromRoot(functionName,params, new Parser(new Tokenizer(trimmed).tokenize()).parse());
    }

    public ExpressionNodeCall parseFunctionCall() {
        //TODO: add parameters and return statements.
        //consume the identifier.
        Token functionName = checkFor(TokenType.IDENTIFIER);

        ParamNode params = parseParamList();

        return new ExpressionNodeCall(functionName,params);
    }


    public SayNode parseSay() {
        if (peek(1) == null || peek(1).type != TokenType.OPEN_PAREN)
            throw new RuntimeException("Invalid Syntax for function: say <arg1>");
        //consume the say token
        consume();
        //parse the expression
        ExpressionNode expr = parseExpression(TokenType.OPEN_PAREN,TokenType.CLOSE_PAREN);
        checkFor(TokenType.NEWLINE, "Expected line break.");
        return new SayNode(expr);
    }
    public KillNode parseKill() {
        if (peek(1) == null || peek(1).type != TokenType.OPEN_PAREN)
            throw new RuntimeException("Invalid Syntax for function: kill(<arg1>)");
        //consume the KILL token
        consume();
        //parse the expression
        ExpressionNode expr = parseExpression(TokenType.OPEN_PAREN,TokenType.CLOSE_PAREN);
        checkFor(TokenType.NEWLINE, "Expected line break.");
        return new KillNode(expr);
    }





    //checks the syntax for a valid variable declaration.
    public boolean isValidConstSyntax() {
        if (peek() == null || peek(1) == null || peek(2) == null)
            return false;

        return peek().type == TokenType.STORAGE_DECLARATION && peek(1).type == TokenType.IDENTIFIER && peek(2).type == TokenType.ASSIGN;
    }
    //parses a variable declaration.
    public DeclarationNode parseDeclaration() {
        if (!isValidConstSyntax())
            throw new RuntimeException("Invalid Variable Declaration. Expected: 'storage <idt> = <val>' ");
        //consume the 'storage' token
        consume();
        //consume our identifier
        IdentifierNode id = new IdentifierNode(consume().value);
        //consume the assignment operator
        consume();
        //get our expression
        ExpressionNode expr = parseExpression(TokenType.NEWLINE);
        //create new declaration

        //check for new line
        if (peek().type != TokenType.NEWLINE)
            throw new RuntimeException("Expected line break");

        //return if syntax is valid.
        return new DeclarationNode(id, expr);
    }


    public SummonNode parseEntityInitialization() {
        if (peek() == null || peek().type != TokenType.SUMMON || peek(1) == null || peek(1).type != TokenType.OPEN_PAREN)
            throw new RuntimeException("Invalid Entity Initialization");
        //consume the summon and open paren tokens
        consume();
        consume();
        ExpressionNode entityType = parseExpression(TokenType.COMMA);
        checkFor(TokenType.COMMA, "Expected: ','");
        //parse positional for pos
        PositionNode pos = parsePos();
        checkFor(TokenType.COMMA, "Expected: ','");
        ExpressionNode nbt = parseExpression(TokenType.CLOSE_PAREN);
        checkFor(TokenType.CLOSE_PAREN,"Expected: ')'");
        return new SummonNode(entityType,pos,nbt);
    }

    public PositionNode parsePos() {
        //consume the 'at' token
        consume();
        checkFor(TokenType.OPEN_PAREN);
        if (safeCheck(TokenType.SELECTOR) || safeCheck(TokenType.IDENTIFIER)) {
            //check for optional vector or return just the selector.
            ExpressionNode selector;
            if (safeCheck(TokenType.CLOSE_PAREN,1))
                selector = parseExpression(TokenType.CLOSE_PAREN);
            else
                selector = parseExpression(TokenType.COMMA);
            if (safeCheck(TokenType.CLOSE_PAREN)) {
                consume();
                return new PositionNodeDynamic(selector);
            }
            consume();
            VectorNode offset = parseVector();
            checkFor(TokenType.CLOSE_PAREN);
            return new PositionNodeDynamic(selector, offset);
        }
        VectorNode v =  parseVector();
        checkFor(TokenType.CLOSE_PAREN);
        return new PositionNodeStatic(v);
    }

    //parses a vector node
    //TODO: make vectors work with sizes other than 3 lol.
    public VectorNode parseVector() {
        ArrayList<ExpressionNode> list= new ArrayList<>();
        //check for an open square bracket
        checkFor(TokenType.OPEN_SQUARE_BRACKET);
        list.add(parseExpression(TokenType.COMMA));
        checkFor(TokenType.COMMA);
        list.add(parseExpression(TokenType.COMMA));
        checkFor(TokenType.COMMA);
        list.add(parseExpression(TokenType.CLOSE_SQUARE_BRACKET));
        checkFor(TokenType.CLOSE_SQUARE_BRACKET);

        //return the VectorNode
        return new VectorNode(list);
    }

    //gets the params of a function.
    public ParamNode parseParamList() {
        //handle cases with no parameters immediately.
        if (safeCheck(TokenType.OPEN_PAREN) && safeCheck(TokenType.CLOSE_PAREN,1)) {
            consume(); consume();
            return new ParamNode();
        }

        ArrayList<ExpressionNode> params = new ArrayList<>();
        ArrayList<Integer> commaIndices = new ArrayList<>();
        int nestLevel = 0;
        //ensure we start with an opening parenthesis.
        if (checkFor(TokenType.OPEN_PAREN).type == TokenType.OPEN_PAREN)
            nestLevel++;

        int start = index;

        while(nestLevel > 0) {
            if (safeCheck(TokenType.CLOSE_PAREN))
                nestLevel--;
            if (safeCheck(TokenType.OPEN_PAREN))
                nestLevel++;
            //nest == 1 to allow for handling nested function calls
            if (safeCheck(TokenType.COMMA) && nestLevel == 1)
                commaIndices.add(index);
            //maybe tweak this to get an error specific to parsing param list idk. (if > length)
            consume();
        }
        index = start;
        int paramCount = commaIndices.size() + 1;

        for (int i = 0; i < commaIndices.size(); i++) {
            params.add(parseEq(index, commaIndices.get(i)));
            index = commaIndices.get(i) + 1;
        }

        params.add(parseExpression(TokenType.OPEN_PAREN,TokenType.CLOSE_PAREN,1,false));
        //consume the closing parenthesis
        checkFor(TokenType.CLOSE_PAREN, "Expected: ')'");
        return new ParamNode(params);
    }

    //parses an expression
    //parses all token until but NOT INCLUDING the first occurrence of the breakpoint token.
    public ExpressionNode parseExpression(TokenType breakPoint) {
        int startIndex = index;
        int nestLevel = 1;

        while (nestLevel > 0) {
            if (safeCheck(breakPoint)) {
                nestLevel--;
            }
            if (peek() == null)
                break;
            index++;
        }
        return parseEq(startIndex, --index);
    }
    //parses expression with nestable breakpoints
    // nestAdd serves as opener tokens ('(','[','{' etc.)
    // nestRemove serves as closing tokens (')',']','}')
    // parses the equation INCLUDING the external opening and closing nest Tokens.
    //inclusive refers to if the last token of the index should be included.
    //offset can be used to adjust for unequal expressions like "2 + 3)".
    public ExpressionNode parseExpression(TokenType nestAdd, TokenType nestRemove) {
        return parseExpression(nestAdd,nestRemove,0,true);
    }
    public ExpressionNode parseExpression(TokenType nestAdd, TokenType nestRemove, int offset, boolean inclusive) {
        int startIndex = index;
        int nestLevel = offset;
        if (offset == 0 && safeCheck(nestAdd)) {
            nestLevel++;
            index++;
        }

        while (nestLevel > 0) {
            if (safeCheck(nestAdd)) {
                nestLevel++;
            }
            if (safeCheck(nestRemove)) {
                nestLevel--;
            }
            if (peek() == null)
                throw new RuntimeException("Expected: \"" + nestRemove + "\"");
            index++;
        }

        if (inclusive)
            return parseEq(startIndex, index);
        else
            return parseEq(startIndex, --index);
    }

    //parses a singleton expression.
    public ExpressionNode parseSingleton(int index) {
        Token cur = tokenList.get(index);
        if (cur == null)
            throw new RuntimeException("Invalid Expression");

        return switch (cur.type) {
            case TokenType.INT_LIT -> new ExpressionNodeInt(cur);
            case TokenType.IDENTIFIER -> new ExpressionNodeIdentifier(cur);
            case TokenType.STRING -> new ExpressionNodeString(cur);
            case TokenType.NBT_OBJECT -> new ExpressionNodeNBT(cur);
            case TokenType.SELECTOR -> new ExpressionNodeSelector(cur);
            case TokenType.FLOAT_LIT -> new ExpressionNodeFloat(cur);
            default -> throw new RuntimeException("Invalid Singleton: \"" + tokenList.get(index) + "\"");
        };
    }


    //parses a math expression
    //takes 2 parameters defining the start and end scopes to search in the tokenlist
    public ExpressionNode parseEq(int start, int end) {
        //ensures indices are valid
        if (start > end || end > tokenList.size() || start < 0)
            throw new RuntimeException("Failed to Parse Expression, Indices are invalid");

        //check for fully nested expression
        if (tokenList.get(start).type == TokenType.OPEN_PAREN && tokenList.get(end-1).type == TokenType.CLOSE_PAREN)
            return parseEq(start+1,end-1);

        //index of the highest priority operator
        //priority is inverted because the root of the tree is going to be the lowest priority operator.
        int maxIndex = -1;
        int maxPrecedence = -1;
        int nestLevel = 0;
        for (int i = start; i < end; i++) {
            Token cur = tokenList.get(i);

            if (cur.type == TokenType.OPEN_PAREN)
                nestLevel++;
            if (cur.type == TokenType.CLOSE_PAREN)
                nestLevel--;

            if (nestLevel < 0) {
                throw new RuntimeException("Invalid Expression, Expected: \"(\"");
            }

            if (nestLevel > 0) {
                continue;
            }

            // >= because the search is flipped from left to right -> right to left.
            if (precedenceOf(cur) >= maxPrecedence) {
                maxPrecedence = precedenceOf(cur);
                maxIndex = i;
            }
        }

        if (nestLevel > 0)
            throw new RuntimeException("Invalid Expression, Expected: \")\"");

        //expression is an int or other literal with no operators
        if (maxPrecedence == 0) {
            //multiple ints with no operator ex: 19 32
            if (end - start > 1) {
                throw new RuntimeException("Invalid expression");
            }
            return parseSingleton(start);
        }

        //empty expression (no literals or operators)
        //defaults to zero
        if (maxPrecedence == -1) {
            System.out.println("Min: " + start + "Max: " + end);
            throw new RuntimeException("No Meaningful Tokens to parse.");
        }

        //gets the operator type.
        char op = getOperator(tokenList.get(maxIndex));

        //left and right sides of the equation.
        ExpressionNode left = parseEq(start, maxIndex);
        ExpressionNode right = parseEq(maxIndex + 1, end);

        //NBT objects and Selectors can't be used with operators
        if (!left.operationFriendly() || !right.operationFriendly())
            throw new RuntimeException("Invalid Expression, Operators could not be applied.");

        boolean containsString = left.getClass() == ExpressionNodeString.class || right.getClass() == ExpressionNodeString.class;
        boolean containsStringOrConcatenation = containsString || left.getClass() == OperationNodeConcatenate.class || right.getClass() == OperationNodeConcatenate.class;

        //recursively parse expressions.
        //TODO: make sure concatenation actually works as expected (probably some buggy behavior)
        if (op == '+') {
            if (containsString)
                return new OperationNodeConcatenate(left, right);
            return new OperationNodeAdd(left, right);
        }
        if (op == '*') {
            if (containsStringOrConcatenation)
                throw new RuntimeException("Invalid Expression, Strings cannot be multiplied.");
            return new OperationNodeMultiply(left, right);
        }
        if (op == '-') {
            if (containsStringOrConcatenation)
                throw new RuntimeException("Invalid Expression, Strings cannot be subtracted.");
            return new OperationNodeSubtract(left, right);
        }
        if (op == '/') {
            if (containsStringOrConcatenation)
                throw new RuntimeException("Invalid Expression, Strings cannot be divided.");
            return new OperationNodeDivide(left, right);
        }
        if (op == '%') {
            if (containsStringOrConcatenation)
                throw new RuntimeException("Invalid Expression, Strings cannot be divided.");
            return new OperationNodeModulo(left, right);
        }
        //error
        throw new RuntimeException("Invalid Expression, Unknown Error.");
    }

    public static char getOperator(Token token) {
        return switch (token.type) {
            case TokenType.MULTIPLY -> '*';
            case TokenType.MODULO -> '%';
            case TokenType.DIVIDE -> '/';
            case TokenType.ADD -> '+';
            case TokenType.SUBTRACT -> '-';
            default -> 0;
        };
    }


    //returns the precedence of an operator
    //precedence is inverted because of how the parser works
    public static int precedenceOf(Token token) {
        return switch (token.type) {
            //case TokenType.OPEN_PAREN,TokenType.CLOSE_PAREN -> 1;
            case TokenType.CLOSE_PAREN,TokenType.OPEN_PAREN -> -1;
            case TokenType.MULTIPLY, TokenType.MODULO, TokenType.DIVIDE -> 2;
            case TokenType.ADD, TokenType.SUBTRACT -> 3;
            case TokenType.INT_LIT,TokenType.FLOAT_LIT, TokenType.STRING,TokenType.SELECTOR, TokenType.IDENTIFIER, TokenType.NBT_OBJECT -> 0;
            default -> -1;
        };
    }


    //check next character with null safety
    public boolean safeCheck(TokenType typeBeingChecked, int count) {
        return peek(count) != null && peek(count).type == typeBeingChecked;
    }
    public boolean safeCheck(TokenType typeBeingChecked) {
        return safeCheck(typeBeingChecked,0);
    }
    //checks for a specific token and throws an error if the token is not present
    public Token checkFor(TokenType type, String error) {
        if (!safeCheck(type))
            throw new RuntimeException(error);
        return consume();
    }
    public Token checkFor(TokenType type) {
        return checkFor(type, "Invalid Token, Expected: " + type);
    }
}
