package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.item.crafting.display.BlueprintRecipeDisplay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class BlueprintRecipe implements Recipe<BlueprintRecipeInput> {
    protected final CommonInfo commonInfo;
    protected final List<Ingredient> ingredients;

    protected BlueprintRecipe(Recipe.CommonInfo commonInfo, List<Ingredient> ingredients) {
        this.commonInfo = commonInfo;
        this.ingredients = ingredients;
    }

    public List<Ingredient> ingredients() {
        return ingredients;
    }

    public boolean shouldConsumeIngredient() {
        return true;
    }

    @Override
    public boolean matches(BlueprintRecipeInput input, Level level) {
        if (ingredients.size() > BlueprintRecipeInput.SIZE) {
            return false;
        }

        // Test the additions included in the recipe with the input items
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(input.getItem(i))) {
                return false;
            }
        }

        // Remaining slots must be empty
        for (int i = ingredients.size(); i < BlueprintRecipeInput.SIZE; i++) {
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
    public RecipeType<? extends Recipe<BlueprintRecipeInput>> getType() {
        return ModRecipeTypes.BLUEPRINT;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.BLUEPRINT;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public List<RecipeDisplay> display() {
        return getRecipeDisplay().stream().map(RecipeDisplay.class::cast).toList();
    }

    public abstract List<BlueprintRecipeDisplay> getRecipeDisplay();

    public static BlueprintRecipe forDisplay(BlueprintRecipeDisplay display) {
        return new BlueprintRecipe(null, List.of()) {

            @Override
            public ItemStack assemble(BlueprintRecipeInput input) {
                return null;
            }

            @Override
            public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
                return null;
            }

            @Override
            public List<RecipeDisplay> display() {
                return super.display();
            }

            @Override
            public List<BlueprintRecipeDisplay> getRecipeDisplay() {
                return List.of(display);
            }
        };
    }
}
