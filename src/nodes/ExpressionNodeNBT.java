package nodes;

public class ExpressionNodeNBT extends  ExpressionNodeSingleton {

    public ExpressionNodeNBT(Token value) {
        super(value);
    }

    @Override public String getType() {
        return super.getType() + "NBT";
    }
}
