package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;

public abstract class Upgrade {
    public static final Upgrade NONE = new Upgrade(Ingredient.EMPTY, "00") {
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
    private final String shortName;

    public Upgrade(Ingredient ingredientIn, String shortNameIn) {
        ingredient = ingredientIn;
        shortName = shortNameIn;
    }

    @Nullable
    public static Upgrade of(Class<? extends Upgrade> upgrade, Ingredient ingredientIn, String shortNameIn) {
        // Get upgrade instance from upgrade Class
        Upgrade instance = null;

        try {
            Constructor<? extends Upgrade> constructor = upgrade.getConstructor(Ingredient.class, String.class);
            instance = constructor.newInstance(ingredientIn, shortNameIn);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return instance;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getShortName() {
        return shortName;
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
