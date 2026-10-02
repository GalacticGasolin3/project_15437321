package nodes;

import java.util.ArrayList;

public class FunctionNode extends CommandNode {
    private ArrayList<Node> nodeList;
    private String name;
    private ParamNode params;

    public FunctionNode(ArrayList<Node> nodeList, String name, ParamNode params) {
        this.nodeList = nodeList;
        this.name = name;
        this.params = params;
    }
    public FunctionNode(String name,ParamNode params) {
        nodeList = new ArrayList<>();
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public ParamNode getParams() {
        return params;
    }

    //appends a new node to the tree.
    public void append(Node nodeToAppend) {
        nodeList.add(nodeToAppend);
    }

    //gets an item from the list
    public Node get(int index) {
        return nodeList.get(index);
    }

    public int length() {
        return nodeList.size();
    }


    @Override public String getType() {
        return "Function" + super.getType();
    }

    @Override public String toString() {
        return nodeList.toString();
    }


    public static FunctionNode getFunctionFromRoot(String name,ParamNode params, RootNode root) {

        return new FunctionNode(root.getNodeList(), name,params);
    }
}
