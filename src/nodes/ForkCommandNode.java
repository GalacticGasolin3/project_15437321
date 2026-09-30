package nodes;

//represents a Fork ('execute at <selector>')
public class ForkCommandNode extends CommandNode {
    private ExpressionNode selector;
    private CommandNode cmd;

    public ForkCommandNode(ExpressionNode selector, CommandNode cmd) {
        this.selector = selector;
        this.cmd = cmd;
    }

    public ExpressionNode getSelector() {
        return selector;
    }

    public CommandNode getCmd() {
        return cmd;
    }

    @Override
    public String getType() {
        return "Fork" + super.getType();
    }
}
