package nodes;

public class OperationNodeDivide extends OperationNode{
    public OperationNodeDivide(ExpressionNode left, ExpressionNode right) {
        super(left, right);
    }

    @Override public String getType() {
        return super.getType() + "Divide";
    }
}
