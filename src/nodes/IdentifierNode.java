package nodes;

public class IdentifierNode extends Node {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override public String getType() {
        return "IdentifierNode";
    }
}
