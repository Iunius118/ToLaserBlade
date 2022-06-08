package com.github.iunius118.tolaserblade.world.item.crafting;

import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<?> UPGRADE = new LBUpgradeRecipe.Serializer();
    public static final RecipeSerializer<?> COLOR = new LBColorRecipe.Serializer();
    public static final RecipeSerializer<?> MODEL_CHANGE = new LBModelChangeRecipe.Serializer();
}
