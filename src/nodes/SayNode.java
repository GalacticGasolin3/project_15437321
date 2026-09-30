package nodes;

public class SayNode extends CommandNode {
    private ExpressionNode message;

    public SayNode(ExpressionNode message) {
        this.message = message;
    }

    public ExpressionNode getMessage() {
        return message;
    }

    @Override public String getType() {
        return "Say" + super.getType();
    }
}
