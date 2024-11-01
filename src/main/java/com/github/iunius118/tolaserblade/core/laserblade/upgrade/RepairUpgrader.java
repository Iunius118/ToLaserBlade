package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RepairUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider) {
        return true;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider) {
        boolean isFireResistant = LaserBladeItemUtil.isFireResistant(base);
        Item brandNew = isFireResistant ? ModItems.LB_BRAND_NEW_FP : ModItems.LB_BRAND_NEW;
        // Copy item data to (fireproof) brand-new laser blade
        ItemStack newStack = base.transmuteCopy(brandNew, base.getCount());
        // Remove attribute modifiers from old stack
        newStack.remove(DataComponents.ATTRIBUTE_MODIFIERS);
        return UpgradeResult.of(newStack, 1);
    }
}
