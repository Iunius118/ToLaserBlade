package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
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

public class ColorRecipe extends SmithingRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final LaserBladeColorPart part;
    private final int color;
    private final ResourceLocation recipeId;
    private ItemStack sample;

    public ColorRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, LaserBladeColorPart part, int color) {
        super(recipeId, base, addition, getResultItemStack(base));
        this.recipeId = recipeId;
        this.base = base;
        this.addition = addition;
        this.part = part;
        this.color = color;
    }

    private static ItemStack getResultItemStack(Ingredient base) {
        ItemStack[] matchingStacks = base.getItems();

        if (matchingStacks.length > 0 && matchingStacks[0] != null) {
            return matchingStacks[0].copy();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (base.test(inv.getItem(0)) && addition.test(inv.getItem(1))) {
            ItemStack baseStack = inv.getItem(0);
            LaserBladeVisual visual = LaserBlade.visualOf(baseStack);

            switch (part) {
                case INNER_BLADE:
                    LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
                    return innerColor.color != color;
                case OUTER_BLADE:
                    LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
                    return outerColor.color != color;
                default:
                    LaserBladeVisual.PartColor gripColor = visual.getGripColor();
                    return gripColor.color != color;
            }
        }

        return false;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        ItemStack baseStack = inv.getItem(0);
        ItemStack itemstack = baseStack.copy();
        return getColoringResult(itemstack);
    }

    private ItemStack getColoringResult(ItemStack input) {
        LaserBladeVisual visual = LaserBlade.visualOf(input);

        switch (part) {
            case INNER_BLADE:
                LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
                innerColor.color = color;
                break;
            case OUTER_BLADE:
                LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
                outerColor.color = color;
                break;
            default:
                LaserBladeVisual.PartColor gripColor = visual.getGripColor();
                gripColor.color = color;
        }

        visual.write(input.getOrCreateTag());
        return input;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        if (sample != null) return sample;

        ItemStack output = super.getResultItem();

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getColoringResult(output.copy());
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
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COLOR;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.SMITHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ColorRecipe> {
        @Override
        public ColorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "addition"));
            JsonObject result = JSONUtils.getAsJsonObject(json, "result");
            JsonElement part = result.get("part");
            LaserBladeColorPart colorPart = LaserBladeColorPart.byPartName(part.getAsString());
            JsonElement color = result.get("color");
            int colorValue = color.getAsInt();
            return new ColorRecipe(recipeId, base, addition, colorPart, colorValue);
        }

        @Nullable
        @Override
        public ColorRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            LaserBladeColorPart colorPart = LaserBladeColorPart.byIndex(buffer.readInt());
            int color = buffer.readInt();
            return new ColorRecipe(recipeId, base, addition, colorPart, color);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ColorRecipe recipe) {
            LaserBladeColorPart part = recipe.part;

            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeInt(part.getIndex());
            buffer.writeInt(recipe.color);
        }
    }
}
