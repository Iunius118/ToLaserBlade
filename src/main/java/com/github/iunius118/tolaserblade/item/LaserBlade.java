package com.github.iunius118.tolaserblade.item;

public class LaserBlade {
    // Blade color table
    public final static int[] colors = {0xFFFF0000, 0xFFD0A000, 0xFF00E000, 0xFF0080FF, 0xFF0000FF, 0xFFA000FF, 0xFFFFFFFF, 0xFF020202, 0xFFA00080};

    public static final int DEFAULT_COLOR_CORE = 0xFFFFFFFF;
    public static final int DEFAULT_COLOR_HALO = 0xFFFF0000;

    public static final String KEY_ATK = "ATK";
    public static final String KEY_SPD = "SPD";
    public static final String KEY_COLOR_CORE = "colorC";
    public static final String KEY_COLOR_HALO = "colorH";
    public static final String KEY_COLOR_GRIP = "colorG";
    public static final String KEY_IS_SUB_COLOR_CORE = "isSubC";
    public static final String KEY_IS_SUB_COLOR_HALO = "isSubH";
    public static final String KEY_IS_CRAFTING = "isCrafting";

    public static final float MOD_SPD_CLASS_1 = 0.0F;
    public static final float MOD_SPD_CLASS_3 = 1.2F;

    public static final float MOD_ATK_MIN = -6.0F;
    public static final float MOD_ATK_CLASS_1 = -1.0F;
    public static final float MOD_ATK_CLASS_2 = 0.0F;
    public static final float MOD_ATK_CLASS_3 = 3.0F;
    public static final float MOD_ATK_CLASS_4 = 7.0F;
    public static final float MOD_ATK_MAX = 2041.0F;

    public static final float MOD_CRITICAL_VS_WITHER = 2.0F;

    public static final int LVL_SMITE_CLASS_1 = 1;
    public static final int LVL_SMITE_CLASS_2 = 2;
    public static final int LVL_SMITE_CLASS_3 = 5;
    public static final int LVL_SMITE_CLASS_4 = 10;

    public static final int COST_LVL_CLASS_2 = 5;
    public static final int COST_LVL_CLASS_4 = 30;

    public static final int MAX_USES = 32000;
}
