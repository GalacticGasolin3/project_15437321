package nodes;

public abstract class ExpressionNode extends Node {
    @Override public String getType() {
        return "ExpressionNode";
    }


    public boolean operationFriendly() {
        return false;
    }
}