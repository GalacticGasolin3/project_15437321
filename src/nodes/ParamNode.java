package nodes;

import java.util.ArrayList;

//node that represents a list of a parameters a function can have.
public class ParamNode extends Node {
    private ArrayList<ExpressionNode> params;

    public ParamNode(ArrayList<ExpressionNode> params) {
        this.params = params;
    }
    public ParamNode() {
        this(new ArrayList<>());
    }

    public void add(ExpressionNode param) {
        params.add(param);
    }

    public ExpressionNode get(int index) {
        return params.get(index);
    }

    public int length() {
        return params.size();
    }

    @Override
    public String getType() {
        return "ParamNode";
    }
}
