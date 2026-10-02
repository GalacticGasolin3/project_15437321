import DatapackComponents.*;
import DatapackComponents.JSON;
import nodes.RootNode;

//class that creates the appropriate file structure for a minecraft datapack
public class DatapackBuilder {
    //name of the datapack
    private String pack_name;
    //output path
    private String output;
    //source code path
    private String source;

    public DatapackBuilder(String pack_name,String source,String output) {
        this.pack_name = pack_name;
        this.source = source;
        this.output = output;
    }

    //compiles mcscript into a datapack.
    public void compile() {
        Reader r = new Reader(source);
        Tokenizer tokenizer = new Tokenizer(r.getAllFileText());
        Parser p = new Parser(tokenizer.tokenize());
        RootNode root = p.parse();
        Generator g = new Generator(pack_name);
        buildFromTemplate(new DatapackTemplate(g.generateFunctions(root)));
    }

    //builds from a datapack template
    public void buildFromTemplate(DatapackTemplate template) {
        buildHelper(template.create(pack_name), output);
    }

    //recursive helper function that creates the folder structure.
    public void buildHelper(Component component, String path) {
        if (component.getClass() == Function.class || component.getClass() == JSON.class || component.getClass() == McMeta.class) {
            String name = component.getName();
            FileMaker maker = new FileMaker(name, path);
            //creates file
            maker.build();
            //writes data to file.
            maker.writeData(((FileElement) component).getData());
        }
        if (component.getClass() != Folder.class)
            return;

        //only option is a folder.
        Folder f = (Folder) component;
        String name = f.getName();
        String newPath = path + "/" + f.getName();
        DirectoryMaker maker = new DirectoryMaker(name, path);
        maker.build();
        for (int i = 0; i < f.length(); i++) {
            buildHelper(f.get(i), newPath);
        }
    }

}
