package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.github.iunius118.tolaserblade.ToLaserBlade.MOD_ID;

public class ModMainItemGroup {
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            LaserBladeItemStack icon = LaserBladeItemStack.ICON;
            return icon.getCopy();
        }
    };
}
