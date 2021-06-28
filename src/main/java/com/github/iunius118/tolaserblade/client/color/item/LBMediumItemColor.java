package com.github.iunius118.tolaserblade.client.color.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LBMediumItemColor implements IItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            LaserBladeItemColor color = LaserBladeItemColor.of(stack);
            return color.simpleOuterColor | 0xFF000000;
        }

        return 0xFFFFFFFF;
    }
}
