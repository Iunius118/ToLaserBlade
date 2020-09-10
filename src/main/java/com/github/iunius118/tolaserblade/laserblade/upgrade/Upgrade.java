package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.item.crafting.Ingredient;

import java.lang.reflect.Constructor;
import java.util.function.Function;

public abstract class Upgrade {
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

    public abstract Function<UpgradeResult, UpgradeResult> getFunction();

    public enum Type {
        BATTERY,
        MEDIUM,
        EMITTER,
        CASING,
        REPAIR,
        OTHER
    }
}
