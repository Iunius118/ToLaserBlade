package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.Constants;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<BlendingRecipe> BLENDING = create(Constants.RecipeTypes.BLENDING);
    public static final RecipeType<ColoringRecipe> COLORING = create(Constants.RecipeTypes.COLORING);
    public static final RecipeType<CraftingRecipe> CRAFTING = create(Constants.RecipeTypes.CRAFTING);
    public static final RecipeType<EnchantmentRecipe> ENCHANTMENT = create(Constants.RecipeTypes.ENCHANTMENT);
    public static final RecipeType<RemodelRecipe> REMODEL = create(Constants.RecipeTypes.REMODEL);

    private static <T extends Recipe<?>> RecipeType<T> create(Identifier id) {
        String name = id.toString();
        return new RecipeType<>() {

            @Override
            public String toString() {
                return name;
            }
        };
    }
}
