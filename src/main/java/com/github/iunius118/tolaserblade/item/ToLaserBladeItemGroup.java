package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.Collection;
import java.util.Collections;

import static com.github.iunius118.tolaserblade.ToLaserBlade.MOD_ID;

interface ToLaserBladeItemGroup extends IForgeItem {
    ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ToLaserBladeItems.LASER_BLADE);
        }
    };

    @Override
    default Collection<ItemGroup> getCreativeTabs() {
        return Collections.singletonList(ITEM_GROUP);
    }
}
