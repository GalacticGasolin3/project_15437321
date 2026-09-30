package nodes;

import java.util.ArrayList;

public class RootNode extends Node {
    private ArrayList<Node> nodeList;
    public RootNode(String name) {
        nodeList = new ArrayList<>();
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

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }


    @Override public String getType() {
        return "RootNode";
    }

    @Override public String toString() {
        return nodeList.toString();
    }
}
