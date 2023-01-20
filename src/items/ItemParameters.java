package items;

public class ItemParameters {

    private ItemParameters() {

    }
    //public static final int NUMBER_OF_TYPES = 5;
    public enum ItemType { Tower1, Tower2, Tower3, AllTowers, Base };
    public static final int MAX_LEVEL = 100;
    public enum Rarity { Common, Rare, Legendary }
    public enum Attribute { Damage, AttackSpeed, Range, Cost, Health };
    public static final int[] MAX_POSSIBLE_MODIFIER_VALUE = { 300, 300, 200, 50, 100 };

    public static final int INVENTORY_SIZE = 50;
    public static final int MAX_ACTIVE_ITEMS = 5;
}
