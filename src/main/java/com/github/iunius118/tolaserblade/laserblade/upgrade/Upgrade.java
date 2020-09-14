package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.lang.reflect.Constructor;

public abstract class Upgrade {
    public static final Upgrade NONE = new Upgrade(Ingredient.EMPTY) {
        @Override
        public boolean test(ItemStack base, ItemStack addition) {
            return false;
        }

        @Override
        public UpgradeResult apply(ItemStack base, int baseCost) {
            return UpgradeResult.of(base, baseCost);
        }
    };

    private final Ingredient ingredient;

    public Upgrade(Ingredient ingredientIn) {
        ingredient = ingredientIn;
    }

    public static Upgrade of(Class<? extends Upgrade> upgrade, Ingredient ingredientIn) {
        Upgrade instance = null;

        try {
            Constructor<? extends Upgrade> constructor = upgrade.getConstructor(Ingredient.class);
            instance = constructor.newInstance(ingredientIn);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return instance;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public abstract boolean test(ItemStack base, ItemStack addition);

    public abstract UpgradeResult apply(ItemStack base, int baseCost);

    public enum Type {
        BATTERY,
        MEDIUM,
        EMITTER,
        CASING,
        REPAIR,
        OTHER
    }
}
