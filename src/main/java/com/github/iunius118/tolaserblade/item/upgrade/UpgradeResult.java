package com.github.iunius118.tolaserblade.item.upgrade;

import net.minecraft.item.ItemStack;

public class UpgradeResult {
    private final ItemStack stack;
    private final int cost;

    public UpgradeResult(ItemStack stackIn, int costIn) {
        stack = (stackIn != null ? stackIn : ItemStack.EMPTY);
        cost = costIn;
    }

    public static UpgradeResult of(ItemStack stackIn, int costIn) {
        return new UpgradeResult(stackIn, costIn);
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
