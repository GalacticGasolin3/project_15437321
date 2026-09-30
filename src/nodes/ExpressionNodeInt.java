package nodes;

public class ExpressionNodeInt extends ExpressionNodeSingleton {
    public ExpressionNodeInt(Token value) {
        super(value);
    }


    @Override public String getType() {
        return super.getType() + "Int";
    }


    @Override
    public boolean operationFriendly() {
        return true;
    }
}
