package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class FireproofUpgrader implements Upgrader {
    public static final int COST = 30;

    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        return !base.has(DataComponents.FIRE_RESISTANT);
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        // Copy item data to fireproof brand-new laser blade
        ItemStack newStack = base.transmuteCopy(ModItems.LB_BRAND_NEW_FP, base.getCount());
        // Remove attribute modifiers from old stack
        newStack.remove(DataComponents.ATTRIBUTE_MODIFIERS);
        return UpgradeResult.of(newStack, COST);
    }
}
