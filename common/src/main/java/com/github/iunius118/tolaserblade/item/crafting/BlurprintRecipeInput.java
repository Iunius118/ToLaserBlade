package com.github.iunius118.tolaserblade.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record BlurprintRecipeInput(ItemStack[] items) implements RecipeInput {
    public static final int SIZE = 4;

    public BlurprintRecipeInput {
        if (items.length != SIZE) {
            throw new IllegalArgumentException(
                    "UpgradeRecipeInput requires exactly " + SIZE + " item(s), got " + items.length);
        }
    }

    public static BlurprintRecipeInput of(Container container) {
        ItemStack[] items = new ItemStack[SIZE];

        for (int i = 0; i < SIZE; i++) {
            items[i] = container.getItem(i);
        }

        return new BlurprintRecipeInput(items);
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= items.length) {
            throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }

        return items[slot];
    }

    public ItemStack base() {
        return getItem(0);
    }

    public ItemStack addition(int index) {
        return getItem(index + 1);
    }

    @Override
    public int size() {
        return SIZE;
    }
}
