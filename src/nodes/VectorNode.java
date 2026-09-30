package nodes;

import java.util.ArrayList;

//vector of expressions
public class VectorNode extends Node {
    private ArrayList<ExpressionNode> elements;

    //constructors
    public VectorNode(ArrayList<ExpressionNode> elements) {
        this.elements = elements;
    }
    public VectorNode() {
        this(new ArrayList<>());
    }

    //get item
    public ExpressionNode get(int index) {
        return elements.get(index);
    }
    //set element
    public void set (int index, ExpressionNode node) {
        elements.set(index, node);
    }
    //add element
    public void append(ExpressionNode element) {
        elements.add(element);
    }

    //get length
    public int length() {
        return elements.size();
    }
}
