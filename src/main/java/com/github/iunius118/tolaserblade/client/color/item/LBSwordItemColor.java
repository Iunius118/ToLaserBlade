package com.github.iunius118.tolaserblade.client.color.item;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LBSwordItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        LaserBladeItemColor color = LaserBladeItemColor.of(stack);

        return switch (tintIndex) {
            case 0 -> color.gripColor | 0xFF000000;
            case 1 -> color.simpleOuterColor | 0xFF000000;
            case 2 -> color.simpleInnerColor | 0xFF000000;
            default -> 0xFFFFFFFF;
        };
    }
}
