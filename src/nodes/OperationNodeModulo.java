package nodes;

public class OperationNodeModulo extends OperationNode{
    public OperationNodeModulo(ExpressionNode left, ExpressionNode right) {
        super(left, right);
    }

    @Override public String getType() {
        return super.getType() + "Modulo";
    }
}
