package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.item.LBBrandNewItem;
import com.github.iunius118.tolaserblade.item.LBBrokenItem;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

public class LaserBladeItemColor {
    public final boolean isBroken;
    public final int gripColor;
    public final int innerColor;
    public final boolean isInnerSubColor;
    public final int simpleInnerColor;
    public final int outerColor;
    public final boolean isOuterSubColor;
    public final int simpleOuterColor;

    public LaserBladeItemColor(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) {
            isBroken = false;
            gripColor = -1;
            innerColor = -1;
            isInnerSubColor = false;
            simpleInnerColor = -1;
            outerColor = -1;
            isOuterSubColor = false;
            simpleOuterColor = -1;
            return;
        }

        Item item = itemStack.getItem();
        isBroken = (item instanceof LBBrokenItem|| item instanceof LBBrandNewItem);

        gripColor = ModItems.LASER_BLADE.checkGamingColor(ModItems.LASER_BLADE.getGripColor(itemStack));

        Pair<Integer, Boolean> bladeColor = ModItems.LASER_BLADE.getBladeInnerColor(itemStack);
        innerColor = ModItems.LASER_BLADE.checkGamingColor(bladeColor.getLeft());
        isInnerSubColor = bladeColor.getRight();
        simpleInnerColor = (bladeColor.getRight() ? ~innerColor : innerColor) | 0xFF000000;

        bladeColor = ModItems.LASER_BLADE.getBladeOuterColor(itemStack);
        outerColor = ModItems.LASER_BLADE.checkGamingColor(bladeColor.getLeft());
        isOuterSubColor = bladeColor.getRight();
        simpleOuterColor = (bladeColor.getRight() ? ~outerColor : outerColor) | 0xFF000000;
    }
}
