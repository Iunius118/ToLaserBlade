package com.github.iunius118.tolaserblade.item.crafting;

import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class BlueprintRecipe implements Recipe<BlurprintRecipeInput> {
    protected final CommonInfo commonInfo;
    protected final List<Ingredient> ingredients;

    protected BlueprintRecipe(Recipe.CommonInfo commonInfo, List<Ingredient> ingredients) {
        this.commonInfo = commonInfo;
        this.ingredients = ingredients;
    }

    public boolean shouldConsumeIngredient() {
        return true;
    }

    @Override
    public boolean matches(BlurprintRecipeInput input, Level level) {
        if (ingredients.size() > BlurprintRecipeInput.SIZE) {
            return false;
        }

        // Test the additions included in the recipe with the input items
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(input.getItem(i))) {
                return false;
            }
        }

        // Remaining slots must be empty
        for (int i = ingredients.size(); i < BlurprintRecipeInput.SIZE; i++) {
            if (!input.getItem(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public abstract RecipeSerializer<? extends BlueprintRecipe> getSerializer();

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.LB_BLUEPRINT;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }
}
