package nodes;

public class ExpressionNodeFloat extends ExpressionNodeSingleton {
    public ExpressionNodeFloat(Token value) {
        super(value);
    }


    @Override public String getType() {
        return super.getType() + "Float";
    }


    @Override
    public boolean operationFriendly() {
        return true;
    }
}
