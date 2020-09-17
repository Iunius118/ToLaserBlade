package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.util.math.MathHelper;

public enum Color {
    WHITE(0xFFFFFFFF, 0xFFF9FFFE),
    ORANGE(0xFFFF681F, 0xFFF9801D),
    MAGENTA(0xFFFF0080, 0xFFC74EBD),
    LIGHT_BLUE(0xFF00AAFF, 0xFF3AB3DA),
    YELLOW(0xFFFFEE00, 0xFFFED83D),
    LIME(0xFFA9FF32, 0xFF80C71F),
    PINK(0xFFFF004C, 0xFFF38BAA),
    GRAY(0xFF555555, 0xFF474F52),
    LIGHT_GRAY(0xFFAAAAAA, 0xFF9D9D97),
    CYAN(0xFF00FFFF, 0xFF169C9C),
    PURPLE(0xFFFF00FF, 0xFF8932B8),
    BLUE(0xFF0000FF, 0xFF3C44AA),
    BROWN(0xFFFF6B00, 0xFF835432),
    GREEN(0xFF80FF00, 0xFF5E7C16),
    RED(0xFFFF0000, 0xFFB02E26),
    BLACK(0xFF020202, 0xFF1D1D21),
    TEMP_DESERT(0xFFA000FF, 0xFFF9FFFE),
    TEMP_SAVANNA(0xFFFF00CC, 0xFFF9FFFE),
    TEMP_JUNGLE(0xFFFFC400, 0xFFF9FFFE),
    TEMP_TAIGA(0xFF00FF00, 0xFFF9FFFE),
    TEMP_ICE_PLAIN(0xFF0080FF, 0xFFF9FFFE),
    TEMP_SNOWY_TAIGA(0xFF0030FF, 0xFFF9FFFE),
    SPECIAL_GAMING(0xFF010101, 0xFF010101);

    private final int bladeColor;
    private final int gripColor;

    Color(int bladeColorIn, int gripColorIn) {
        bladeColor = bladeColorIn;
        gripColor = gripColorIn;
    }

    public int getBladeColor() {
        return bladeColor;
    }

    public int getGripColor() {
        return gripColor;
    }

    public static Color get(int index) {
        final Color[] values = Color.values();
        return values[MathHelper.clamp(index, 0, values.length - 1)];
    }
}
