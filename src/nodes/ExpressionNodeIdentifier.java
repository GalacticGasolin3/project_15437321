package nodes;

public class ExpressionNodeIdentifier extends ExpressionNodeSingleton {
    public ExpressionNodeIdentifier(Token value) {
        super(value);
    }


    @Override public String getType() {
        return super.getType() + "Identifier";
    }


    @Override
    public boolean operationFriendly() {
        return true;
    }
}
