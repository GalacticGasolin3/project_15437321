package DatapackComponents;

//represents a .mcfunction file
public class Function extends FileElement {
    public Function(String name, String code) {
        super(name, code);
    }

    @Override public String getName() {
        return super.getName() + ".mcfunction";
    }
}
