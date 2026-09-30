package nodes;

public class ExpressionNodeString extends ExpressionNodeSingleton {
    public ExpressionNodeString(Token value) {
        super(value);
    }

    @Override public String getValue() {
        return "\"" + super.getValue() + "\"";
    }

    @Override public String getType() {
        return super.getType() + "String";
    }


    @Override
    public boolean operationFriendly() {
        return true;
    }
}
