package com.github.iunius118.tolaserblade.client.color.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.world.item.LBBrandNewItem;
import com.github.iunius118.tolaserblade.world.item.LBBrokenItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record LaserBladeItemColor(
        boolean isBroken, int type,
        int outerColor, boolean isOuterSubColor,
        int innerColor, boolean isInnerSubColor,
        int gripColor) {
    public static final LaserBladeItemColor DEFAULT = new LaserBladeItemColor(false, 0, -1, false, -1, false, -1);

    public static LaserBladeItemColor of(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) {
            return DEFAULT;
        }

        Item item = itemStack.getItem();
        boolean isBroken = (item instanceof LBBrokenItem || item instanceof LBBrandNewItem);

        var appearance = LaserBladeAppearance.of(itemStack);
        int type = appearance.getType();

        int rawOuterColor = appearance.getOuterColor();
        int outerColor = checkGamingColor(rawOuterColor);
        boolean isOuterSubColor = appearance.isOuterSubColor();

        int rawInnerColor = appearance.getInnerColor();
        int innerColor = checkGamingColor(rawInnerColor);
        boolean isInnerSubColor = appearance.isInnerSubColor();

        int rawGripColor = appearance.getGripColor();
        int gripColor = checkGamingColor(rawGripColor);

        return new LaserBladeItemColor(
                isBroken, type,
                outerColor, isOuterSubColor,
                innerColor, isInnerSubColor,
                gripColor
        );
    }

    public int simpleOuterColor() {
        return (isOuterSubColor ? ~outerColor : outerColor) | 0xFF000000;
    }

    public int simpleInnerColor() {
        return (isInnerSubColor ? ~innerColor : innerColor) | 0xFF000000;
    }

    public int simpleGripColor() {
        return gripColor | 0xFF000000;
    }

    private static int checkGamingColor(int color) {
        if (color == LaserBladeColor.SPECIAL_GAMING.getOuterColor()) {
            return getGamingColor();
        }

        return color;
    }

    private static int getGamingColor() {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player != null) {
            float partialTick = minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(true);
            var level = player.level();
            int tick1 = (int) (level.getGameTime() % 30);
            int tick2 = tick1 % 10;
            int colorElement;

            if (tick2 % 10 < 5) {
                colorElement = (int) (((float) tick2 + partialTick) * (float) 0x33) & 0xFF;

                return switch (tick1 / 10) {
                    case 0 -> 0xFFFF0000 | (colorElement << 8);
                    case 1 -> 0xFF00FF00 | colorElement;
                    default -> 0xFF0000FF | (colorElement << 16);
                };
            } else {
                colorElement = (int) (((float) (10 - tick2) - partialTick) * (float) 0x33) & 0xFF;

                return switch (tick1 / 10) {
                    case 0 -> 0xFF00FF00 | (colorElement << 16);
                    case 1 -> 0xFF0000FF | (colorElement << 8);
                    default -> 0xFFFF0000 | colorElement;
                };
            }


        }

        return 0xFF010101;
    }
}
