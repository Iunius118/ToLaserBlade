package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.util.math.MathHelper;

public enum Color {
    WHITE(0xFFFFFFFF, 0xFFF9FFFE, "white"),
    ORANGE(0xFFFF681F, 0xFFF9801D, "orange"),
    MAGENTA(0xFFFF0080, 0xFFC74EBD, "magenta"),
    LIGHT_BLUE(0xFF00AAFF, 0xFF3AB3DA, "light_blue"),
    YELLOW(0xFFFFEE00, 0xFFFED83D, "yellow"),
    LIME(0xFFA9FF32, 0xFF80C71F, "lime"),
    PINK(0xFFFF004C, 0xFFF38BAA, "pink"),
    GRAY(0xFF555555, 0xFF474F52, "gray"),
    LIGHT_GRAY(0xFFAAAAAA, 0xFF9D9D97, "light_gray"),
    CYAN(0xFF00FFFF, 0xFF169C9C, "cyan"),
    PURPLE(0xFFFF00FF, 0xFF8932B8, "purple"),
    BLUE(0xFF0000FF, 0xFF3C44AA, "blue"),
    BROWN(0xFFFF6B00, 0xFF835432, "brown"),
    GREEN(0xFF80FF00, 0xFF5E7C16, "green"),
    RED(0xFFFF0000, 0xFFB02E26, "red"),
    BLACK(0xFF020202, 0xFF1D1D21, "black"),
    TEMP_DESERT(0xFFA000FF, 0xFFF9FFFE, "temp_desert"),
    TEMP_SAVANNA(0xFFFF00CC, 0xFFF9FFFE, "temp_savanna"),
    TEMP_JUNGLE(0xFFFFC400, 0xFFF9FFFE, "temp_jungle"),
    TEMP_TAIGA(0xFF00FF00, 0xFFF9FFFE, "temp_taiga"),
    TEMP_ICE_PLAIN(0xFF0080FF, 0xFFF9FFFE, "temp_ice_plain"),
    TEMP_SNOWY_TAIGA(0xFF0030FF, 0xFFF9FFFE, "temp_snowy_taiga"),
    SPECIAL_GAMING(0xFF010101, 0xFF010101, "special_gaming"),
    SPECIAL_SWITCH_BLEND_MODE(0x01010101, 0x01010101, "special_sbm"),
    ;

    private final int bladeColor;
    private final int gripColor;
    private final String colorName;

    Color(int bladeColor, int gripColor, String colorName) {
        this.bladeColor = bladeColor;
        this.gripColor = gripColor;
        this.colorName = colorName;
    }

    public static Color get(int index) {
        final Color[] values = Color.values();
        return values[MathHelper.clamp(index, 0, values.length - 1)];
    }

    public int getBladeColor() {
        return bladeColor;
    }

    public int getGripColor() {
        return gripColor;
    }

    public String getColorName() {
        return colorName;
    }
}
