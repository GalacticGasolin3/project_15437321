package nodes;

public class CommandNode extends Node {

    @Override
    public String getType() {
        return "Command" +super.getType();
    }
}
