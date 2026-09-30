package nodes;

//node for the /kill command
public class KillNode extends CommandNode {
    private ExpressionNode target;

    public KillNode(ExpressionNode target) {
        this.target = target;
    }

    public ExpressionNode getTarget() {
        return target;
    }

    @Override public String getType() {
        return "Kill" + super.getType();
    }
}
