package main;

public class Modifiers {

    private Modifiers() {

    }
    private static int droprate = 100;             //in %, will be multiplied with Enemy.value
    private static int rareDroprate;
    private static int legendaryDroprate;
    private static int baseHealth;
    private static int startMoney;

    private static float[][] towerModifiers;
    private static float[] attackBoost;
    private static float[] attackSpeedBoost;
    private static float[] attackRangeBoost;
    private static float[] towerCostReduction;

    public static void readSaveFile() {
        //TODO
    }

    public static void updateItemsModifiers() {
        baseHealth = 100;
        //reset
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                towerModifiers[i][j] = 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            attackBoost[i] = 1;
            attackSpeedBoost[i] = 1;
            attackRangeBoost[i] = 1;
            towerCostReduction[i] = 1;
        }
        //TODO: active items
    }

    // Getters and setters
    public static int getDroprate() {
        return droprate;
    }

    public static int getRareDroprate() {
        return rareDroprate;
    }

    public static int getLegendaryDroprate() {
        return legendaryDroprate;
    }
}
