package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.LaserBladeStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.github.iunius118.tolaserblade.ToLaserBlade.MOD_ID;

public interface ModMainItemGroup {
    ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            LaserBladeStack icon = LaserBladeStack.ICON;
            return icon.getCopy();
        }
    };
}
