package com.github.iunius118.tolaserblade.item;

import net.minecraft.util.Mth;

public enum LaserBladeColor {
    WHITE(0xFFFFFFFF, 0xFFF9FFFE, "white"),
    ORANGE(0xFFFFA500, 0xFFF9801D, "orange"),
    MAGENTA(0xFFFF00FF, 0xFFC74EBD, "magenta"),
    LIGHT_BLUE(0xFF00AAFF, 0xFF3AB3DA, "light_blue"),
    YELLOW(0xFFFFEE00, 0xFFFED83D, "yellow"),
    LIME(0xFF00FF00, 0xFF80C71F, "lime"),
    PINK(0xFFFFC0CB, 0xFFF38BAA, "pink"),
    GRAY(0xFF808080, 0xFF474F52, "gray"),
    LIGHT_GRAY(0xFFD3D3D3, 0xFF9D9D97, "light_gray"),
    CYAN(0xFF00FFFF, 0xFF169C9C, "cyan"),
    PURPLE(0xFF9D00FF, 0xFF8932B8, "purple"),
    BLUE(0xFF0000FF, 0xFF3C44AA, "blue"),
    BROWN(0xFF964B00, 0xFF835432, "brown"),
    GREEN(0xFF32CD32, 0xFF5E7C16, "green"),
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
    ;

    private final int outerBladeColor;
    private final int innerBladeColor;
    private final int gripColor;
    private final boolean isOuterBladeSubColor;
    private final boolean isInnerBladeSubColor;
    private final String colorName;

    LaserBladeColor(int outerBladeColor, int innerBladeColor, int gripColor,
                    boolean isOuterBladeSubColor, boolean isInnerBladeSubColor, String colorName) {
        this.outerBladeColor = outerBladeColor;
        this.innerBladeColor = innerBladeColor;
        this.gripColor = gripColor;
        this.isOuterBladeSubColor = isOuterBladeSubColor;
        this.isInnerBladeSubColor = isInnerBladeSubColor;
        this.colorName = colorName;
    }

    LaserBladeColor(int bladeColor, int gripColor,
                    boolean isOuterBladeSubColor, boolean isInnerBladeSubColor, String colorName) {
        this(bladeColor, bladeColor, gripColor, isOuterBladeSubColor, isInnerBladeSubColor, colorName);
    }

    LaserBladeColor(int outerBladeColor, int innerBladeColor, int gripColor, String colorName) {
        this(outerBladeColor, innerBladeColor, gripColor, false, false, colorName);
    }

    LaserBladeColor(int bladeColor, int gripColor, String colorName) {
        this(bladeColor, bladeColor, gripColor, colorName);
    }


    public static LaserBladeColor get(int index) {
        final LaserBladeColor[] values = LaserBladeColor.values();
        return values[Mth.clamp(index, 0, values.length - 1)];
    }

    public int bladeColor() {
        return outerBladeColor;
    }

    public int outerBladeColor() {
        return outerBladeColor;
    }

    public int innerBladeColor() {
        return innerBladeColor;
    }

    public int gripColor() {
        return gripColor;
    }

    public boolean isOuterBladeSubColor() {
        return isOuterBladeSubColor;
    }

    public boolean isInnerBladeSubColor() {
        return isInnerBladeSubColor;
    }

    public String colorName() {
        return colorName;
    }

    public int partColor(int part) {
        return switch(part) {
            case 0 -> gripColor;
            case 1 -> outerBladeColor;
            case 2 -> innerBladeColor;
            default -> 0;
        };
    }
}
