package DatapackComponents;

//represents a File
public abstract class FileElement extends Component {
    //name of file and the text to input into the file.
    private String data;

    public FileElement(String name, String data) {
        super(name);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
