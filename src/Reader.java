import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/** Class for reading and processing .mcscript files */
public class Reader {

    //path of file to read from
    private String path;

    public Reader(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //gets all the text from the file
    public String getAllFileText() {
        try {
            List<String> text = Files.readAllLines(Paths.get(path));
            StringBuilder builder = new StringBuilder();
            for (String line : text) {
                builder.append(line);
                builder.append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
