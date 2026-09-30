import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** class for creating files */
public class FileMaker {
    //path to put the file in.
    private String path;

    //name of the file with the file extension
    private String name;

    //constructor
    public FileMaker(String name, String path) {
        this.path = path;
        this.name = name;
    }

    public static void writeToFile(String text, String path) {
        try {
            Files.writeString(Paths.get(path),text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeData(String data) {
        writeToFile(data, path + "/" + name);
    }


    //creates the file
    public void build() {
        //gets the directory path
        Path directory = Paths.get(path);
        //adds the file path
        Path filePath = directory.resolve(name);

        //creates the file
        try {
            if (Files.exists(filePath))
                Files.delete(filePath);
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //getters and setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
