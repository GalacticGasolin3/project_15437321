package DatapackComponents;

public class McMeta extends FileElement {
    private McMeta(String name, String data) {
        super(name, data);
    }
    public McMeta() {
        this("pack","{\"pack\": {\"description\": \"Created With MCScript\"}}");
    }

    @Override
    public String getName() {
        return super.getName() + ".mcmeta";
    }
}
