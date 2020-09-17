package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.item.ItemStack;

public class UpgradeResult {
    private final ItemStack stack;
    private final int cost;

    public UpgradeResult(ItemStack stackIn, int costIn) {
        stack = (stackIn != null) ? stackIn : ItemStack.EMPTY;
        cost = costIn;
    }

    public static UpgradeResult of(ItemStack stackIn, int costIn) {
        return new UpgradeResult(stackIn, costIn);
    }

    public static UpgradeResult of(ItemStack stackIn) {
        return new UpgradeResult(stackIn, 0);
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public int getCost() {
        return cost;
    }

    public boolean hasUpgraded() {
        return cost > 0;
    }
}
