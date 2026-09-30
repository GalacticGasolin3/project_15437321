package nodes;

public class OperationNodeConcatenate extends OperationNode{
    public OperationNodeConcatenate(ExpressionNode left, ExpressionNode right) {
        super(left, right);
    }

    @Override public String getType() {
        return super.getType() + "Concatenate";
    }
}
