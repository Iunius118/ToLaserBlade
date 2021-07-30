package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;

public record UpgradeResult(ItemStack itemStack, int cost) {
    public static UpgradeResult of(ItemStack itemStack, int cost) {
        return new UpgradeResult(itemStack, cost);
    }

    public static UpgradeResult of(ItemStack itemStack) {
        return new UpgradeResult(itemStack, 0);
    }

    public boolean hasUpgraded() {
        return cost > 0;
    }
}
