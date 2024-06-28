package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record Upgrade(Upgrader upgrader, TagKey<Item> ingredientItemTag, String shortName) {
    public static final Upgrade NONE = new Upgrade(
            new Upgrader() {
                @Override
                public boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider) {
                    return false;
                }

                @Override
                public UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider) {
                    return UpgradeResult.of(base, baseCost);
                }
            }, null, "00");

    public Ingredient getIngredient() {
        return ingredientItemTag != null ? Ingredient.of(ingredientItemTag) : Ingredient.EMPTY;
    }

    public boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider) {
        return upgrader.canApply(base, addition, provider);
    }

    public UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider) {
        return upgrader.apply(base, baseCost, provider);
    }

    public enum Type {
        BATTERY,
        MEDIUM,
        EMITTER,
        CASING,
        REPAIR,
        OTHER
    }
}
