package com.github.iunius118.tolaserblade.world.item.crafting;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SmithingRecipeDisplay;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public abstract class LBSmithingRecipe implements SmithingRecipe {
    protected final Optional<Ingredient> template;
    protected final Optional<Ingredient> base;
    protected final Optional<Ingredient> addition;
    protected final ItemStack result;
    @Nullable
    private PlacementInfo placementInfo;

    public LBSmithingRecipe(Optional<Ingredient> template, Optional<Ingredient> base, Optional<Ingredient> addition) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        result = getResultItemStack(base);
    }

    private static ItemStack getResultItemStack(Optional<Ingredient> base) {
        if (base.isPresent()) {
            Optional<Holder<Item>> item = base.get().items().findFirst();

            if (item.isPresent()) {
                return item.get().value().getDefaultInstance();
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return template;
    }

    @Override
    public Optional<Ingredient> baseIngredient() {
        return base;
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return addition;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.createFromOptionals(List.of(this.template, this.base, this.addition));
        }

        return this.placementInfo;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new SmithingRecipeDisplay(
                        Ingredient.optionalIngredientToDisplay(template),
                        Ingredient.optionalIngredientToDisplay(base),
                        Ingredient.optionalIngredientToDisplay(addition),
                        new SlotDisplay.ItemStackSlotDisplay(getDisplayResult(result)),
                        new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE)
                )
        );
    }

    protected abstract ItemStack getDisplayResult(ItemStack result);
}
