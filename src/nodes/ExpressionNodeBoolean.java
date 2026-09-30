package nodes;

public class ExpressionNodeBoolean extends ExpressionNodeSingleton {
    public ExpressionNodeBoolean(Token value) {
        super(value);
    }

    @Override
    public String getType() {
        return super.getType() + "Boolean";
    }
}
