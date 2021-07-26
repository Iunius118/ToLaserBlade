package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public abstract class Upgrade {
    public static final Upgrade NONE = new Upgrade(() -> Ingredient.EMPTY, "00") {
        @Override
        public boolean test(ItemStack base, ItemStack addition) {
            return false;
        }

        @Override
        public UpgradeResult apply(ItemStack base, int baseCost) {
            return UpgradeResult.of(base, baseCost);
        }
    };

    // Supplier of upgrade ingredient.
    // Lazy evaluation via the supplier can wait for the timing of sync of tags from server to client.
    private final Supplier<Ingredient> ingredientSupplier;
    private Ingredient ingredient;
    private final String shortName;

    public Upgrade(Supplier<Ingredient> ingredientSupplierIn, String shortNameIn) {
        ingredientSupplier = ingredientSupplierIn;
        shortName = shortNameIn;
    }

    @Nullable
    public static Upgrade of(Class<? extends Upgrade> upgrade, Supplier<Ingredient> ingredientSupplierIn, String shortNameIn) {
        // Get upgrade instance from upgrade Class
        Upgrade instance = null;

        try {
            Constructor<? extends Upgrade> constructor = upgrade.getConstructor(Supplier.class, String.class);
            instance = constructor.newInstance(ingredientSupplierIn, shortNameIn);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return instance;
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
