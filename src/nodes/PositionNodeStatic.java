package nodes;

import java.util.ArrayList;
import java.util.Arrays;

//static fixed position ex: [1,4,3]
public class PositionNodeStatic extends PositionNode {
    private VectorNode pos;


    public PositionNodeStatic(VectorNode pos) {
        this.pos = pos;
    }
    public PositionNodeStatic(ExpressionNode x, ExpressionNode y, ExpressionNode z) {
        this(new VectorNode((ArrayList<ExpressionNode>) Arrays.asList(x,y,z)));
    }

    public VectorNode getPos() {
        return pos;
    }

    @Override public String getType() {
        return super.getType() + "Static";
    }
}
