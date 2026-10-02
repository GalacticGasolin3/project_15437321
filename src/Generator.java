import DatapackComponents.Folder;
import nodes.FunctionNode;
import nodes.Node;
import nodes.RootNode;

import java.util.HashSet;

public class Generator {
    private HashSet<String> funcNames;
    private String namespace;

    public Generator(String namespace) {
        this.namespace = namespace;
        funcNames = new HashSet<>();
    }


    public Folder generateFunctions(RootNode root) {
        Folder functions = new Folder("function");

        for (int i = 0; i < root.length(); i++) {
            Node cur  = root.get(i);
            if (cur.getClass() != FunctionNode.class)
                throw new RuntimeException("Invalid function");
            FunctionNode curFunction = (FunctionNode) cur;
            FunctionGenerator gen = new FunctionGenerator(namespace,curFunction.getName(),curFunction.getParams());
            functions.add(gen.asFunctionObject(curFunction));
        }

        return functions;
    }
}
