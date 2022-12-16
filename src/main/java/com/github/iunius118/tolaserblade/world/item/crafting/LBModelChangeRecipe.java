package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

public class LBModelChangeRecipe extends UpgradeRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private final ResourceLocation recipeId;
    private ItemStack sample;

    public LBModelChangeRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, int type) {
        super(recipeId, base, addition, getResultItemStack(base));
        this.recipeId = recipeId;
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    private static ItemStack getResultItemStack(Ingredient base) {
        ItemStack[] matchingStacks = base.getItems();

        if (matchingStacks.length > 0 && matchingStacks[0] != null) {
            return matchingStacks[0].copy();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (base.test(container.getItem(0)) && addition.test(container.getItem(1))) {
            ItemStack baseStack = container.getItem(0);
            LaserBladeVisual visual = LaserBlade.visualOf(baseStack);
            int baseType = visual.getModelType();
            // If type < 0, set today date number or reset model type
            return type < 0 || baseType != type;
        }

        return false;
    }

    @Override
    public ItemStack assemble(Container container) {
        ItemStack baseStack = container.getItem(0);
        ItemStack itemstack = baseStack.copy();
        return getResult(itemstack);
    }

    private ItemStack getResult(ItemStack input) {
        LaserBladeVisual visual = LaserBlade.visualOf(input);
        int fixedType = Math.max(type, 0);

        visual.setModelType(fixedType);
        visual.write(input.getOrCreateTag());
        return input;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return super.canCraftInDimensions(i, j);
    }

    @Override
    public ItemStack getResultItem() {
        if (sample != null) return sample;

        ItemStack output = super.getResultItem();

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getResult(output.copy());
        return sample;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MODEL_CHANGE;
    }

    @Override
    public RecipeType<?> getType() {
        // Treat as RecipeType.SMITHING to use on smithing table
        return super.getType();
    }

    public static class Serializer implements RecipeSerializer<LBModelChangeRecipe> {
        @Override
        public LBModelChangeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            JsonObject result = GsonHelper.getAsJsonObject(json, "result");
            JsonElement modelType = result.get("model_type");
            int type = modelType.getAsInt();
            return new LBModelChangeRecipe(recipeId, base, addition, type);
        }

        @Nullable
        @Override
        public LBModelChangeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            int type = buffer.readInt();
            return new LBModelChangeRecipe(recipeId, base, addition, type);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, LBModelChangeRecipe recipe) {
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeInt(recipe.type);
        }
    }
}
