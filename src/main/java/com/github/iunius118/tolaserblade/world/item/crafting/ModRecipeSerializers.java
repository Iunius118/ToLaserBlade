package com.github.iunius118.tolaserblade.world.item.crafting;

import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<LBColorRecipe> COLOR = new LBColorRecipe.Serializer();
    public static final RecipeSerializer<LBUpgradeRecipe> UPGRADE = new LBUpgradeRecipe.Serializer();
    public static final RecipeSerializer<LBModelChangeRecipe> MODEL_CHANGE = new LBModelChangeRecipe.Serializer();
}
