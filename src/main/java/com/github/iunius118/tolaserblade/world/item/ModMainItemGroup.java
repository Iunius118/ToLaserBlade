package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.github.iunius118.tolaserblade.ToLaserBlade.MOD_ID;

public class ModMainItemGroup {
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            LaserBladeItemStack icon = LaserBladeItemStack.ICON;
            return icon.getCopy();
        }
    };
}
