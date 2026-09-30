import DatapackComponents.*;
import DatapackComponents.JSON;
import nodes.FunctionNode;

//defines the construction of a datapack
public class DatapackTemplate {
    //functions
    private Folder functions;

    //creates a new empty datapack template
    public DatapackTemplate(Folder functions) {
        this.functions = functions;
    }


    public Folder create(String namespace) {
        //creating the tags folder
        Folder mcFolder = new Folder("minecraft");
        Folder jsonTags = new Folder("tags");
        Folder mcFunctionTags = new Folder("function");
        mcFolder.add(jsonTags);
        jsonTags.add(mcFunctionTags);
        JSON load = new JSON("load","{\"values\":[\"" + namespace + ":on_load\"]}");
        JSON tick = new JSON("tick","{\"values\":[\"" + namespace + ":on_tick\"]}");
        mcFunctionTags.add(load);
        mcFunctionTags.add(tick);

        //creating the data folder
        Folder dataFolder = new Folder("data");
        //add the default package.
        dataFolder.add(generateDefaultPackage());

        //create the namespace
        Folder inNamespace = new Folder(namespace);
        //add the functions
        inNamespace.add(functions);

        //add namespace and JSON tags to data folder
        dataFolder.add(inNamespace);
        dataFolder.add(mcFolder);

        //create and return the datapack;
        Folder datapack = new Folder(namespace);
        datapack.add(dataFolder);
        //add a mcmeta file.
        datapack.add(new McMeta());
        return datapack;
    }


    //adds the functions needed for MCScript to work.
    public static Folder generateDefaultPackage() {
        //create folder.
        Folder mcScript =  new Folder("mc_script");
        Folder function = new Folder("function");
        //math and commands folder.
        Folder cmds = new Folder("commands");
        Folder math = new Folder("math");
        //command functions.
        Function say = new Function("say","$execute as $(root_selector) run say $(msg)");
        Function kill = new Function("kill","$execute as $(root_selector) run kill $(target)");
        Function summon = new Function("summon","$execute as $(root_selector) at $(location_selector) run summon $(entity) $(rel)$(x) $(rel)$(y) $(rel)$(z) $(nbt)");
        Function tellraw = new Function("tellraw","$execute as $(root_selector) run tellraw $(selector) $(msg)");
        cmds.add(say);
        cmds.add(kill);
        cmds.add(summon);
        cmds.add(tellraw);

        //math functions
        Function add = new Function("add","$data modify storage mc_script:memory math.result set compute default float {\"type\":\"add\",\"inputs\":[$(a),$(b)]}");
        Function subtract = new Function("subtract","$data modify storage mc_script:memory math.result set compute default float {\"type\":\"sub\",\"left\":$(a),\"right\":$(b)}");
        Function multiply = new Function("multiply","$data modify storage mc_script:memory math.result set compute default float {\"type\":\"mul\",\"inputs\":[$(a),$(b)]}");
        Function divide = new Function("divide","$data modify storage mc_script:memory math.result set compute default float {\"type\":\"div\",\"left\":$(a),\"right\":$(b)}");
        Function mod = new Function("mod","$data modify storage mc_script:memory math.result set compute default float {\"type\":\"mod\",\"left\":$(a),\"right\":$(b)}");
        math.add(add);
        math.add(subtract);
        math.add(multiply);
        math.add(divide);
        math.add(mod);


        function.add(math);
        function.add(cmds);
        mcScript.add(function);

        return mcScript;
    }

}
