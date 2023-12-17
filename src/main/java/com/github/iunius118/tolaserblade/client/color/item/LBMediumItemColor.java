package com.github.iunius118.tolaserblade.client.color.item;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LBMediumItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            LaserBladeItemColor color = LaserBladeItemColor.of(stack);
            return color.simpleOuterColor | 0xFF000000;
        }

        return 0xFFFFFFFF;
    }
}
