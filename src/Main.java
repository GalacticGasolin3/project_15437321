public class Main {
    public static void main(String[] args) {
        DatapackBuilder builder = new DatapackBuilder("test","mcsrc/main.mcscript","out/datapack");
        builder.compile();
    }
}