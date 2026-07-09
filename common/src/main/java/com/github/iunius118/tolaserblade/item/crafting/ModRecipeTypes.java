package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.Constants;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<BlueprintRecipe> BLUEPRINT = create(Constants.RecipeTypes.BLUEPRINT);

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
