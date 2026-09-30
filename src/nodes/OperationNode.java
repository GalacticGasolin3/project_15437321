package nodes;

//performs an operation
public abstract class OperationNode extends ExpressionNode {
    private ExpressionNode operandLeft;
    private ExpressionNode operandRight;

    public OperationNode(ExpressionNode op1, ExpressionNode op2) {
        this.operandLeft = op1;
        this.operandRight = op2;
    }

    public ExpressionNode getLeft() {
        return operandLeft;
    }

    public ExpressionNode getRight() {
        return operandRight;
    }

    @Override public String getType() {
        return "OperationNode";
    }

    @Override
    public boolean operationFriendly() {
        return true;
    }
}
