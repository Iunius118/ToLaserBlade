package com.github.iunius118.tolaserblade.laserblade;

import javax.annotation.Nullable;

public enum ColorPart {
    INNER_BLADE(0, "inner_blade"),
    OUTER_BLADE(1, "outer_blade"),
    GRIP(2, "grip");

    private final int index;

    private final String name;

    ColorPart(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getPartName() {
        return name;
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
