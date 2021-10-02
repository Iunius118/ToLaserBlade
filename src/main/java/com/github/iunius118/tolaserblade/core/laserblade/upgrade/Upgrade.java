package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class Upgrade {
    public static final Upgrade NONE = new Upgrade(
            new Upgrader() {
                @Override public boolean canApply(ItemStack base, ItemStack addition) { return false; }
                @Override public UpgradeResult apply(ItemStack base, int baseCost) { return UpgradeResult.of(base, baseCost); }
            }, () -> Ingredient.EMPTY, "00");

    private final Upgrader upgrader;
    // Supplier of upgrade ingredient.
    // Lazy evaluation via the supplier can wait for the timing of sync of tags from server to client.
    private final Supplier<Ingredient> ingredientSupplier;
    private Ingredient ingredient;
    private final String shortName;

    public Upgrade(Upgrader upgrader, Supplier<Ingredient> ingredientSupplier, String shortName) {
        this.upgrader = upgrader;
        this.ingredientSupplier = ingredientSupplier;
        this.shortName = shortName;
    }

    public static Upgrade of(Upgrader upgrader, Supplier<Ingredient> ingredientSupplier, String shortName) {
        return new Upgrade(upgrader, ingredientSupplier, shortName);
    }

    public Ingredient getIngredient() {
        if (ingredient == null) {
            ingredient = ingredientSupplier.get();
        }

        return ingredient;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean canApply(ItemStack base, ItemStack addition) {
        return upgrader.canApply(base, addition);
    }

    public UpgradeResult apply(ItemStack base, int baseCost) {
        return upgrader.apply(base,baseCost);
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
