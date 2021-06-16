package com.github.iunius118.tolaserblade.client.renderer.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LBCasingItemColor implements IItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            LaserBladeItemColor color = LaserBladeItemColor.of(stack);
            return color.gripColor | 0xFF000000;
        }

        return 0xFFFFFFFF;
    }
}
