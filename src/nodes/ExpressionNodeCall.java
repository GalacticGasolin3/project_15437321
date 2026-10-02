package nodes;

//node representing a function call.
public class ExpressionNodeCall extends ExpressionNodeSingleton {
    private ParamNode params;

    public ExpressionNodeCall(Token functionName,ParamNode parameters) {
        super(functionName);
        this.params = parameters;
    }

    public ParamNode getParams() {
        return params;
    }

    @Override
    public String getType() {
        return super.getType() + "Call";
    }


    @Override
    public boolean operationFriendly() {
        return true;
    }
}
