package nodes;

public class OperationNodeAdd extends OperationNode{
    public OperationNodeAdd(ExpressionNode left, ExpressionNode right) {
        super(left, right);
    }

    @Override public String getType() {
        return super.getType() + "Add";
    }
}
