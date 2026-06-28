package com.github.iunius118.tolaserblade.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BlueprintRecipeInput(List<ItemStack> items) implements RecipeInput {
    public static final int SIZE = 4;

    public BlueprintRecipeInput {
        if (items.size() != SIZE) {
            throw new IllegalArgumentException(
                    "UpgradeRecipeInput requires exactly " + SIZE + " item(s), got " + items.size());
        }
    }

    public static BlueprintRecipeInput of(Container container) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            items.add(container.getItem(i));
        }

        return new BlueprintRecipeInput(Collections.unmodifiableList(items));
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= items.size()) {
            throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }

        return items.get(slot);
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
