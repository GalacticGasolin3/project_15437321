package nodes;

public abstract class PositionNode extends Node {
    @Override public String getType() {
        return super.getType() + "Position";
    }
}
