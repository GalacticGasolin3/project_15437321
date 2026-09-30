package DatapackComponents;

import java.util.ArrayList;
import java.util.HashMap;

//represents a datapack folder
public class Folder extends Component {
    private final ArrayList<Component> items;

    public Folder(ArrayList<Component> items, String name) {
        super(name);
        this.items = items;
    }
    public Folder(String name) {
        this(new ArrayList<>(),name);
    }

    //adds a component to the folder.
    public void add(Component component) {
        items.add(component);
    }

    public Component get(int index) {
        return items.get(index);
    }

    public int length() {
        return items.size();
    }
}
