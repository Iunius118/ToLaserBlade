package com.github.iunius118.tolaserblade.integration.jei.recipe;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.crafting.BlueprintRecipe;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeManager;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeTypes;
import com.github.iunius118.tolaserblade.item.crafting.display.BlueprintRecipeDisplay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class BlueprintRecipeCategory extends AbstractRecipeCategory<RecipeHolder<BlueprintRecipe>> {
    public static final IRecipeHolderType<BlueprintRecipe> RECIPE_HOLDER_TYPE =
            IRecipeHolderType.create(ModRecipeTypes.BLUEPRINT);

    private final IGuiHelper guiHelper;

    public BlueprintRecipeCategory(IGuiHelper guiHelper) {
        super(
                RECIPE_HOLDER_TYPE,
                Component.translatable(Constants.Menus.BLUEPRINT_TITLE),
                guiHelper.createDrawableItemLike(ModItems.BL_BLUEPRINT),
                126,
                28
        );
        this.guiHelper = guiHelper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BlueprintRecipe> recipe,
                          IFocusGroup focuses) {
        var recipeDisplay = recipe.value().getRecipeDisplay().getFirst();

        var ingredients = recipeDisplay.ingredients();
        int inputSize = Math.min(ingredients.size(), 4);

        for (int i = 0; i < 4; i++) {
            IRecipeSlotBuilder slotBuilder = builder.addInputSlot(i * 18 + 1, 6).setStandardSlotBackground();

            if (i < inputSize) {
                slotBuilder.add(ingredients.get(i));
            }
        }

        IRecipeSlotBuilder outputSlot = builder.addOutputSlot(109, 6)
                .setStandardSlotBackground()
                .add(recipeDisplay.result());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<BlueprintRecipe> recipe,
                                   IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(79, 6);
    }

    public List<RecipeHolder<BlueprintRecipe>> getRecipeHolders() {
        return ModRecipeManager.getClientSyncedRecipes()
                .byType(ModRecipeTypes.BLUEPRINT)
                .stream()
                .filter(this::isHandled)
                .map(h -> {
                    List<BlueprintRecipeDisplay> recipeDisplays = h.value().getRecipeDisplay();
                    var id = h.id().identifier();
                    return IntStream.range(0, recipeDisplays.size())
                            .mapToObj(i -> {
                                var recipe = BlueprintRecipe.forDisplay(recipeDisplays.get(i));
                                var key = ResourceKey.create(Registries.RECIPE, id.withSuffix("_" + i));
                                return new RecipeHolder<>(key, recipe);
                            })
                            .toList();
                })
                .flatMap(Collection::stream)
                .toList();
    }
}
