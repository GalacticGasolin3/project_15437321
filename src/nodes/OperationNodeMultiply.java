package nodes;

public class OperationNodeMultiply extends OperationNode {
    public OperationNodeMultiply(ExpressionNode left, ExpressionNode right) {
        super(left, right);
    }

    @Override public String getType() {
        return super.getType() + "Multiply";
    }
}
