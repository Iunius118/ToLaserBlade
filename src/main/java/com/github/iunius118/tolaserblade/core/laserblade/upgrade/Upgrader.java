package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public interface Upgrader {
    boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider);
    UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider);
}
