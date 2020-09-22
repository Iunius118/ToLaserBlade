package com.github.iunius118.tolaserblade.laserblade;

import javax.annotation.Nullable;

public enum ColorPart {
    INNER_BLADE(0, "inner_blade", "ib"),
    OUTER_BLADE(1, "outer_blade", "ob"),
    GRIP(2, "grip", "gr");

    private final int index;

    private final String name;
    private final String shortName;

    ColorPart(int index, String name, String shortName) {
        this.index = index;
        this.name = name;
        this.shortName = shortName;
    }

    public int getIndex() {
        return index;
    }

    public String getPartName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Nullable
    public static ColorPart byIndex(int index) {
        for (ColorPart part : ColorPart.values()) {
            if (part.index == index) {
                return part;
            }
        }

        return null;
    }

    @Nullable
    public static ColorPart byPartName(String name) {
        for (ColorPart part : ColorPart.values()) {
            if (part.name.equals(name)) {
                return part;
            }
        }

        return null;
    }
}
