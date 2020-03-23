package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.Collection;
import java.util.Collections;

import static com.github.iunius118.tolaserblade.ToLaserBlade.MOD_ID;

interface ModMainItemGroup extends IForgeItem {
    ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return ((LaserBladeItemBase)ModItems.LASER_BLADE).setGripColor(new ItemStack(ModItems.LASER_BLADE), LaserBladeItemBase.LBColor.GRAY.getGripColor());
        }
    };

    @Override
    default Collection<ItemGroup> getCreativeTabs() {
        return Collections.singletonList(ITEM_GROUP);
    }
}
