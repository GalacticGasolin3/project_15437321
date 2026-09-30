import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Makes a new directory with the name "name" in the path "path"
public class DirectoryMaker {
    //properties
    private String name;
    private String path;

    //constructor
    public DirectoryMaker(String name, String path) {
        this.name = name;
        this.path = path;
    }

    //creates the directory
    public void build() {
        String newFolderPath = path + "/" + name;
        Path directoryPath = Paths.get(newFolderPath);
        try {
            if (Files.exists(directoryPath))
                Files.delete(directoryPath);
            else
                Files.createDirectories(directoryPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //getters
    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    //setters
    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }
}
