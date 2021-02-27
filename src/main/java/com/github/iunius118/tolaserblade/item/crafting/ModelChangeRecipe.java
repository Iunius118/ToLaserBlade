package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ModelChangeRecipe extends SmithingRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private final ResourceLocation recipeId;
    private ItemStack sample;

    public ModelChangeRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, int type) {
        super(recipeId, base, addition, getResultItemStack(base));
        this.recipeId = recipeId;
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    private static ItemStack getResultItemStack(Ingredient base) {
        ItemStack[] matchingStacks = base.getMatchingStacks();

        if (matchingStacks.length > 0 && matchingStacks[0] != null) {
            return matchingStacks[0].copy();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (base.test(inv.getStackInSlot(0)) && addition.test(inv.getStackInSlot(1))) {
            ItemStack baseStack = inv.getStackInSlot(0);
            LaserBladeVisual visual = LaserBlade.visualOf(baseStack);
            int baseType = visual.getModelType();
            return type < 0 || baseType != type;   // If type < 0, set today date number or reset model type
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack baseStack = inv.getStackInSlot(0);
        ItemStack itemstack = baseStack.copy();
        return getResult(itemstack);
    }

    private ItemStack getResult(ItemStack input) {
        LaserBladeVisual visual = LaserBlade.visualOf(input);
        int fixedType = type;

        if (type < 0) {
            int baseType = visual.getModelType();
            fixedType = baseType >= 0 ? -1 : ToLaserBlade.getTodayDateNumber();
        }

        visual.setModelType(fixedType);
        visual.write(input.getOrCreateTag());
        return input;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        if (sample != null) return sample;

        ItemStack output = super.getRecipeOutput();

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getResult(output.copy());
        return sample;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MODEL_CHANGE;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.SMITHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ModelChangeRecipe> {
        @Override
        public ModelChangeRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
            Ingredient addition = Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition"));
            JsonObject result = JSONUtils.getJsonObject(json, "result");
            JsonElement modelType = result.get("model_type");
            int type = modelType.getAsInt();
            return new ModelChangeRecipe(recipeId, base, addition, type);
        }

        @Nullable
        @Override
        public ModelChangeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient base = Ingredient.read(buffer);
            Ingredient addition = Ingredient.read(buffer);
            int type = buffer.readInt();
            return new ModelChangeRecipe(recipeId, base, addition, type);
        }

        @Override
        public void write(PacketBuffer buffer, ModelChangeRecipe recipe) {
            recipe.base.write(buffer);
            recipe.addition.write(buffer);
            buffer.writeInt(recipe.type);
        }
    }
}
