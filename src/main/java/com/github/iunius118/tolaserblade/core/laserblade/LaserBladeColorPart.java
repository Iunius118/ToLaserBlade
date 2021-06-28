package com.github.iunius118.tolaserblade.core.laserblade;

import javax.annotation.Nullable;

public enum LaserBladeColorPart {
    INNER_BLADE(0, "inner_blade", "ib"),
    OUTER_BLADE(1, "outer_blade", "ob"),
    GRIP(2, "grip", "gr");

    private final int index;

    private final String name;
    private final String shortName;

    LaserBladeColorPart(int index, String name, String shortName) {
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
    public static LaserBladeColorPart byIndex(int index) {
        for (LaserBladeColorPart part : LaserBladeColorPart.values()) {
            if (part.index == index) {
                return part;
            }
        }

        return null;
    }

    @Nullable
    public static LaserBladeColorPart byPartName(String name) {
        for (LaserBladeColorPart part : LaserBladeColorPart.values()) {
            if (part.name.equals(name)) {
                return part;
            }
        }

        return null;
    }
}
