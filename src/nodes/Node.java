package nodes;

//node class that defines other nodes
public abstract class Node {
    @Override public String toString() {
        return "Type: " + getType();
    }

    public String getType() {
        return "Node";
    }
}

