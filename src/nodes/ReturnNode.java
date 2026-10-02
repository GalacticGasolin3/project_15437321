package nodes;

public class ReturnNode extends Node {
    private ExpressionNode returnValue;

    public ReturnNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    public ExpressionNode getReturnValue() {
        return returnValue;
    }


    @Override
    public String getType() {
        return "Return" + super.getType();
    }
}
