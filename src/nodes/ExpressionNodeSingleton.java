package nodes;

public abstract class ExpressionNodeSingleton extends ExpressionNode {
    private Token value;

    public ExpressionNodeSingleton(Token value) {
        this.value = value;
    }

    public String getValue() {
        return value.value;
    }

    @Override public String getType() {
        return super.getType() + "Singleton";
    }
}
