package nodes;

//node for variable declarations
public class DeclarationNode extends Node {
    private IdentifierNode id;
    private ExpressionNode data;

    public DeclarationNode(IdentifierNode id, ExpressionNode data) {
        this.id = id;
        this.data = data;
    }

    public IdentifierNode getIdentifier() {
        return id;
    } 

    public ExpressionNode getData() {
        return data;
    }

    @Override public String getType() {
        return "Declaration" + super.getType();
    }
}
