package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;

public interface Upgrader {
    boolean canApply(ItemStack base, ItemStack addition);
    UpgradeResult apply(ItemStack base, int baseCost);
}
