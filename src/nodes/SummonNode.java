package nodes;

public class SummonNode extends CommandNode {
    //entity types as numbers
    public static final int ACACIA_BOAT = 0;
    public static final int ACACIA_CHEST_BOAT = 1;
    public static final int ALLAY = 2;
    public static final int AREA_EFFECT_CLOUD = 3;
    public static final int ARMADILLO = 4;
    public static final int ARMOR_STAND = 5;
    public static final int ARROW = 6;
    public static final int AXOLOTL = 7;
    public static final int BAMBOO_CHEST_RAFT = 8;
    public static final int BAMBOO_RAFT = 9;
    public static final int BAT = 10;
    public static final int BEE = 11;
    public static final int BIRCH_BOAT = 12;
    public static final int BIRCH_CHEST_BOAT = 13;
    public static final int BLAZE = 14;
    public static final int BLOCK_DISPLAY = 15;
    public static final int BOGGED = 16;
    public static final int BREEZE = 17;
    public static final int BREEZE_WIND_CHARGE = 18;
    public static final int CAMEL = 19;
    public static final int CAMEL_HUSK = 20;
    public static final int CAT = 21;
    public static final int CAVE_SPIDER = 22;
    public static final int CHERRY_BOAT = 23;
    public static final int CHERRY_CHEST_BOAT = 24;
    public static final int CHEST_MINECART = 25;
    public static final int CHICKEN = 26;
    public static final int COD = 27;
    public static final int COMMAND_BLOCK_MINECART = 28;
    public static final int COPPER_GOLEM = 29;
    public static final int COW = 30;
    public static final int CREAKING = 31;
    public static final int CREEPER = 32;
    public static final int DARK_OAK_BOAT = 33;
    public static final int DARK_OAK_CHEST_BOAT = 34;
    public static final int DOLPHIN = 35;
    public static final int DONKEY = 36;
    public static final int DRAGON_FIREBALL = 37;
    public static final int DROWNED = 38;
    public static final int EGG = 39;
    public static final int ELDER_GUARDIAN = 40;
    public static final int ENDER_CRYSTAL = 41;
    public static final int ENDER_DRAGON = 42;
    public static final int ENDER_PEARL = 43;
    public static final int ENDERMAN = 44;
    public static final int ENDERMITE = 45;
    public static final int EVOKER = 46;
    public static final int EVOKER_FANGS = 47;
    public static final int EXPERIENCE_BOTTLE = 48;
    public static final int EXPERIENCE_ORB = 49;
    public static final int EYE_OF_ENDER = 50;
    public static final int FALLING_BLOCK = 51;
    public static final int FIREBALL = 52;
    public static final int FIREWORK_ROCKET = 53;
    public static final int FISHING_BOBBER = 54; // can't be summoned with /summon
    public static final int FOX = 55;
    public static final int FROG = 56;
    public static final int FURNACE_MINECART = 57;
    public static final int GHAST = 58;
    public static final int GIANT = 59;
    public static final int GLOW_ITEM_FRAME = 60;
    public static final int GLOW_SQUID = 61;
    public static final int GOAT = 62;
    public static final int GUARDIAN = 63;
    public static final int HAPPY_GHAST = 64;
    public static final int HOGLIN = 65;
    public static final int HOPPER_MINECART = 66;
    public static final int HORSE = 67;
    public static final int HUSK = 68;
    public static final int ILLUSIONER = 69;
    public static final int INTERACTION = 70;
    public static final int IRON_GOLEM = 71;
    public static final int ITEM = 72;
    public static final int ITEM_DISPLAY = 73;
    public static final int ITEM_FRAME = 74;
    public static final int JUNGLE_BOAT = 75;
    public static final int JUNGLE_CHEST_BOAT = 76;
    public static final int LEASH_KNOT = 77;
    public static final int LIGHTNING_BOLT = 78;
    public static final int LINGERING_POTION = 79;
    public static final int LLAMA = 80;
    public static final int LLAMA_SPIT = 81;
    public static final int MAGMA_CUBE = 82;
    public static final int MANGROVE_BOAT = 83;
    public static final int MANGROVE_CHEST_BOAT = 84;
    public static final int MARKER = 85;
    public static final int MINECART = 86;
    public static final int MOOSHROOM = 87;
    public static final int MULE = 88;
    public static final int NAUTILUS = 89;
    public static final int OAK_BOAT = 90;
    public static final int OAK_CHEST_BOAT = 91;
    public static final int OCELOT = 92;
    public static final int OMINOUS_ITEM_SPAWNER = 93;
    public static final int PAINTING = 94;
    public static final int PALE_OAK_BOAT = 95;
    public static final int PALE_OAK_CHEST_BOAT = 96;
    public static final int PANDA = 97;
    public static final int PARCHED = 98;
    public static final int PARROT = 99;
    public static final int PHANTOM = 100;
    public static final int PIG = 101;
    public static final int PIGLIN = 102;
    public static final int PIGLIN_BRUTE = 103;
    public static final int PILLAGER = 104;
    public static final int PLAYER = 105;
    public static final int POLAR_BEAR = 106;
    public static final int PUFFERFISH = 107;
    public static final int RABBIT = 108;
    public static final int RAVAGER = 109;
    public static final int SALMON = 110;
    public static final int SHEEP = 111;
    public static final int SHULKER = 112;
    public static final int SHULKER_BULLET = 113;
    public static final int SILVERFISH = 114;
    public static final int SKELETON = 115;
    public static final int SKELETON_HORSE = 116;
    public static final int SLIME = 117;
    public static final int SMALL_FIREBALL = 118;
    public static final int SNIFFER = 119;
    public static final int SNOW_GOLEM = 120;
    public static final int SNOWBALL = 121;
    public static final int SPAWNER_MINECART = 122;
    public static final int SPECTRAL_ARROW = 123;
    public static final int SPIDER = 124;
    public static final int SPLASH_POTION = 125;
    public static final int SQUID = 126;
    public static final int SPRUCE_BOAT = 127;
    public static final int SPRUCE_CHEST_BOAT = 128;
    public static final int STRAY = 129;
    public static final int STRIDER = 130;
    public static final int SULFUR_CUBE = 131;
    public static final int TADPOLE = 132;
    public static final int TEXT_DISPLAY = 133;
    public static final int TNT = 134;
    public static final int TNT_MINECART = 135;
    public static final int TRADER_LLAMA = 136;
    public static final int TRIDENT = 137;
    public static final int TROPICAL_FISH = 138;
    public static final int TURTLE = 139;
    public static final int VEX = 140;
    public static final int VILLAGER = 141;
    public static final int VINDICATOR = 142;
    public static final int WANDERING_TRADER = 143;
    public static final int WARDEN = 144;
    public static final int WIND_CHARGE = 145;
    public static final int WITCH = 146;
    public static final int WITHER = 147;
    public static final int WITHER_SKELETON = 148;
    public static final int WITHER_SKULL = 149;
    public static final int WOLF = 150;
    public static final int ZOGLIN = 151;
    public static final int ZOMBIE = 152;
    public static final int ZOMBIE_HORSE = 153;
    public static final int ZOMBIE_NAUTILUS = 154;
    public static final int ZOMBIE_VILLAGER = 155;
    public static final int ZOMBIFIED_PIGLIN = 156;
    public static final int MANNEQUIN = 157;

    //string list of all entities.
    private static final String[] ENTITY_STRING_LIST = {
            "minecraft:acacia_boat",
            "minecraft:acacia_chest_boat",
            "minecraft:allay",
          "minecraft:area_effect_cloud",
          "minecraft:armadillo",
          "minecraft:armor_stand",
          "minecraft:arrow",
          "minecraft:axolotl",
          "minecraft:bamboo_chest_raft",
          "minecraft:bamboo_raft",
          "minecraft:bat",
          "minecraft:bee",
          "minecraft:birch_boat",
          "minecraft:birch_chest_boat",
          "minecraft:blaze",
          "minecraft:block_display",
          "minecraft:bogged",
          "minecraft:breeze",
          "minecraft:breeze_wind_charge",
          "minecraft:camel",
          "minecraft:camel_husk",
          "minecraft:cat",
          "minecraft:cave_spider",
          "minecraft:cherry_boat",
          "minecraft:cherry_chest_boat",
          "minecraft:chest_minecart",
          "minecraft:chicken",
          "minecraft:cod",
          "minecraft:command_block_minecart",
          "minecraft:copper_golem",
          "minecraft:cow",
          "minecraft:creaking",
          "minecraft:creeper",
          "minecraft:dark_oak_boat",
          "minecraft:dark_oak_chest_boat",
          "minecraft:dolphin",
          "minecraft:donkey",
          "minecraft:dragon_fireball",
          "minecraft:drowned",
          "minecraft:egg",
          "minecraft:elder_guardian",
          "minecraft:ender_crystal",
          "minecraft:ender_dragon",
          "minecraft:ender_pearl",
          "minecraft:enderman",
          "minecraft:endermite",
          "minecraft:evoker",
          "minecraft:evoker_fangs",
          "minecraft:experience_bottle",
          "minecraft:experience_orb",
          "minecraft:eye_of_ender",
          "minecraft:falling_block",
          "minecraft:fireball",
          "minecraft:firework_rocket",
          "minecraft:fishing_bobber",
          "minecraft:fox",
          "minecraft:frog",
          "minecraft:furnace_minecart",
          "minecraft:ghast",
          "minecraft:giant",
          "minecraft:glow_item_frame",
          "minecraft:glow_squid",
          "minecraft:goat",
          "minecraft:guardian",
          "minecraft:happy_ghast",
          "minecraft:hoglin",
          "minecraft:hopper_minecart",
          "minecraft:horse",
          "minecraft:husk",
          "minecraft:illusioner",
          "minecraft:interaction",
          "minecraft:iron_golem",
          "minecraft:item",
          "minecraft:item_display",
          "minecraft:item_frame",
          "minecraft:jungle_boat",
          "minecraft:jungle_chest_boat",
          "minecraft:leash_knot",
          "minecraft:lightning_bolt",
          "minecraft:lingering_potion",
          "minecraft:llama",
          "minecraft:llama_spit",
          "minecraft:magma_cube",
          "minecraft:mangrove_boat",
          "minecraft:mangrove_chest_boat",
          "minecraft:marker",
          "minecraft:minecart",
          "minecraft:mooshroom",
          "minecraft:mule",
          "minecraft:nautilus",
          "minecraft:oak_boat",
          "minecraft:oak_chest_boat",
          "minecraft:ocelot",
          "minecraft:ominous_item_spawner",
          "minecraft:painting",
          "minecraft:pale_oak_boat",
          "minecraft:pale_oak_chest_boat",
          "minecraft:panda",
          "minecraft:parched",
          "minecraft:parrot",
          "minecraft:phantom",
          "minecraft:pig",
          "minecraft:piglin",
          "minecraft:piglin_brute",
          "minecraft:pillager",
          "minecraft:player",
          "minecraft:polar_bear",
          "minecraft:pufferfish",
          "minecraft:rabbit",
          "minecraft:ravager",
          "minecraft:salmon",
          "minecraft:sheep",
          "minecraft:shulker",
          "minecraft:shulker_bullet",
          "minecraft:silverfish",
          "minecraft:skeleton",
          "minecraft:skeleton_horse",
          "minecraft:slime",
          "minecraft:small_fireball",
          "minecraft:sniffer",
          "minecraft:snow_golem",
          "minecraft:snowball",
          "minecraft:spawner_minecart",
          "minecraft:spectral_arrow",
          "minecraft:spider",
          "minecraft:splash_potion",
          "minecraft:squid",
          "minecraft:spruce_boat",
          "minecraft:spruce_chest_boat",
          "minecraft:stray",
          "minecraft:strider",
          "minecraft:sulfur_cube",
          "minecraft:tadpole",
          "minecraft:text_display",
          "minecraft:tnt",
          "minecraft:tnt_minecart",
          "minecraft:trader_llama",
            "minecraft:trident",
          "minecraft:tropical_fish",
          "minecraft:turtle",
          "minecraft:vex",
          "minecraft:villager",
          "minecraft:vindicator",
          "minecraft:wandering_trader",
          "minecraft:warden",
          "minecraft:wind_charge",
          "minecraft:witch",
          "minecraft:wither",
          "minecraft:wither_skeleton",
          "minecraft:wither_skull",
          "minecraft:wolf",
          "minecraft:zoglin",
          "minecraft:zombie",
          "minecraft:zombie_horse",
          "minecraft:zombie_nautilus",
          "minecraft:zombie_villager",
          "minecraft:zombified_piglin",
            "minecraft:mannequin"
    };


    //position, entity type, nbt data
    private ExpressionNode entityType;
    private ExpressionNode nbtData;
    private PositionNode pos;

    //constructors
    public SummonNode(ExpressionNode entityType, PositionNode pos, ExpressionNode nbtData) {
        this.entityType = entityType;
        this.pos = pos;
        this.nbtData = nbtData;
    }

    @Override public String getType() {
        return "Summon" + super.getType();
    }


    //getters
    public ExpressionNode getEntityType() {
        return entityType;
    }
    public PositionNode getPos() {
        return pos;
    }
    public ExpressionNode getNbtData() {
        return nbtData;
    }

    //gets an entity's type from the string or -1 if the entity does not exist
    public static int getTypeFromString(String entity) {
        for (int i = 0; i < ENTITY_STRING_LIST.length; i++) {
            if (ENTITY_STRING_LIST[i].equals(entity))
                return i;
        }
        return -1;
    }
    //inverse of the other function, gets the string from the entity type or null if the entity does not exist.
    public static String getStringFromType(int entity) {
        if (entity < 0 || entity > 158)
            return null;
        return ENTITY_STRING_LIST[entity];
    }
}
