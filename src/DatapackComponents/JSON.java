package DatapackComponents;

//represents a JSON File
public class JSON extends FileElement{
    public JSON(String name, String data) {
        super(name, data);
    }

    @Override public String getName() {
        return super.getName() + ".json";
    }
}
