package nodes;

import java.util.ArrayList;

public class PositionNodeDynamic extends PositionNode {
    private ExpressionNode reference;
    private VectorNode offset; // can be null, null defaults to 0,0,0

    public PositionNodeDynamic(ExpressionNode reference, VectorNode offset) {
        this.reference = reference;

        if (offset.length() != 3)
            throw new RuntimeException("Expected length of 3");

        this.offset = new VectorNode();
        this.offset.append(offset.get(0));
        this.offset.append(offset.get(1));
        this.offset.append(offset.get(2));
    }

    public PositionNodeDynamic(ExpressionNode reference) {
        this.reference = reference;
        this.offset = null;
    }

    @Override public String getType() {
        return super.getType() + "Dynamic";
    }


    public ExpressionNode getReference() {
        return reference;
    }

    public VectorNode getOffset() {
        return offset;
    }
}
