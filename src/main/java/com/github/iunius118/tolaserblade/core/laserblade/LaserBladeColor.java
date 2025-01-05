package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.util.Mth;

public enum LaserBladeColor {
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
    TEMP_STONY_PEAKS(0xFFFF00CC, 0xFFF9FFFE, "temp_stony_peaks"),
    TEMP_JUNGLE(0xFFFFC400, 0xFFF9FFFE, "temp_jungle"),
    TEMP_TAIGA(0xFF00FF00, 0xFFF9FFFE, "temp_taiga"),
    TEMP_SNOWY_PLAINS(0xFF0080FF, 0xFFF9FFFE, "temp_snowy_plains"),
    TEMP_SNOWY_TAIGA(0xFF0030FF, 0xFFF9FFFE, "temp_snowy_taiga"),
    BIOME_DEEP_DARK(0xFF00FFFF, 0xFFFADCD7, 0xFF052328, false, true, "biome_deep_dark"),
    BIOME_CHERRY_GROVE(0xFFFF004C, 0xFFFADCF0, 0xFF4B2D3C, "biome_cherry_grove"),
    BIOME_PALE_GARDEN(0xFFEC7214, 0xFFEFA337, 0xFF686462, "biome_pale_garden"),
    BIOME_NETHER_A(0xFFFFFFFF, 0xFFF9FFFE, false, true, "biome_nether_a"),
    BIOME_NETHER_B(0xFFFFFFFF, 0xFFF9FFFE, true, false, "biome_nether_b"),
    BIOME_END(0xFFFFFFFF, 0xFFF9FFFE, true, true, "biome_end"),
    SPECIAL_GAMING(0xFF010101, 0xFF010101, "special_gaming"),
    SPECIAL_SWITCH_BLEND_MODE(0x01010101, 0x01010101, "special_sbm"),
    ;

    private final int outerColor;
    private final int innerColor;
    private final int gripColor;
    private final boolean isOuterSubColor;
    private final boolean isInnerSubColor;
    private final String colorName;

    LaserBladeColor(int outerColor, int innerColor, int gripColor, boolean isOuterSubColor, boolean isInnerSubColor, String colorName) {
        this.outerColor = outerColor;
        this.innerColor = innerColor;
        this.gripColor = gripColor;
        this.isOuterSubColor = isOuterSubColor;
        this.isInnerSubColor = isInnerSubColor;
        this.colorName = colorName;
    }

    LaserBladeColor(int bladeColor, int gripColor, boolean isOuterSubColor, boolean isInnerSubColor, String colorName) {
        this(bladeColor, bladeColor, gripColor, isOuterSubColor, isInnerSubColor, colorName);
    }

    LaserBladeColor(int outerColor, int innerColor, int gripColor, String colorName) {
        this(outerColor, innerColor, gripColor, false, false, colorName);
    }

    LaserBladeColor(int bladeColor, int gripColor, String colorName) {
        this(bladeColor, bladeColor, gripColor, colorName);
    }

    public static LaserBladeColor get(int index) {
        final LaserBladeColor[] values = LaserBladeColor.values();
        return values[Mth.clamp(index, 0, values.length - 1)];
    }

    public static LaserBladeColor getColorByTemperature(float temp) {
        if (temp > 1.5F) {
            // t > 1.5
            return LaserBladeColor.TEMP_DESERT;
        } else if (temp >= 1.0F) {
            // 1.5 >= t >= 1.0
            return LaserBladeColor.TEMP_STONY_PEAKS;
        } else if (temp > 0.8F) {
            // 1.0 > t > 0.8
            return LaserBladeColor.TEMP_JUNGLE;
        } else if (temp >= 0.5F) {
            // 0.8 >= t >= 0.5
            return LaserBladeColor.RED;
        } else if (temp >= 0.2F) {
            // 0.5 > t >= 0.2
            return LaserBladeColor.TEMP_TAIGA;
        } else if (temp >= -0.3F) {
            // 0.2 > t >= -0.3
            return LaserBladeColor.TEMP_SNOWY_PLAINS;
        } else {
            // -0.3 > t
            return LaserBladeColor.TEMP_SNOWY_TAIGA;
        }
    }

    public int getBladeColor() {
        return outerColor;
    }

    public int getOuterColor() {
        return outerColor;
    }

    public int getInnerColor() {
        return innerColor;
    }

    public int getGripColor() {
        return gripColor;
    }

    public boolean isOuterSubColor() {
        return isOuterSubColor;
    }

    public boolean isInnerSubColor() {
        return isInnerSubColor;
    }

    public String getColorName() {
        return colorName;
    }
}
