package com.github.iunius118.tolaserblade.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ToLaserBladeDataGenerator {
    public static class Recipes extends RecipeProvider implements IConditionBuilder {
        public Recipes(DataGenerator generatorIn) {
            super(generatorIn);
        }

        public void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        }
    }
}
