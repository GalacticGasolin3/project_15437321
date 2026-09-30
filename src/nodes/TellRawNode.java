package nodes;

public class TellRawNode extends Node {
    private ExpressionNode message;

    public TellRawNode(ExpressionNode message) {
        this.message = message;
    }

    public ExpressionNode getMessage() {
        return message;
    }

    @Override public String getType() {
        return "TellRawNode";
    }
}
