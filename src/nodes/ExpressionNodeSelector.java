package nodes;

//stores a selector
public class ExpressionNodeSelector extends ExpressionNodeSingleton {
    public ExpressionNodeSelector(Token value) {
        super(value);
    }

    @Override public String getValue() {
        return "\"" + super.getValue() + "\"";
    }

    @Override public String getType() {
        return super.getType() +  "Selector";
    }
}
