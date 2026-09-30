package nodes;

public class OperationNodeSubtract extends OperationNode{
    public OperationNodeSubtract(ExpressionNode left, ExpressionNode right) {
        super(left, right);
    }

    @Override public String getType() {
        return super.getType() + "Subtract";
    }
}
