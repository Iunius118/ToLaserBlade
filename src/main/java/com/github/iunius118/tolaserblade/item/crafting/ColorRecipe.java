package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.laserblade.Color;
import com.github.iunius118.tolaserblade.laserblade.ColorPart;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeColor;
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
    private final ColorPart part;
    private final Color color;
    private final ResourceLocation recipeId;
    private ItemStack sample;

    public ColorRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, ColorPart part, Color color) {
        super(recipeId, base, addition, getResultItemStack(base));
        this.recipeId = recipeId;
        this.base = base;
        this.addition = addition;
        this.part = part;
        this.color = color;
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

            switch (part) {
                case INNER_BLADE:
                    LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
                    return innerColor.color != color.getBladeColor();
                case OUTER_BLADE:
                    LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
                    return outerColor.color != color.getBladeColor();
                default:
                    LaserBladeVisual.PartColor gripColor = visual.getGripColor();
                    return gripColor.color != color.getGripColor();
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack baseStack = inv.getStackInSlot(0);
        ItemStack itemstack = baseStack.copy();
        return getColoringResult(itemstack);
    }

    private ItemStack getColoringResult(ItemStack input) {
        LaserBladeVisual visual = LaserBlade.visualOf(input);

        switch (part) {
            case INNER_BLADE:
                LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
                innerColor.color = color.getBladeColor();
                break;
            case OUTER_BLADE:
                LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
                outerColor.color = color.getBladeColor();
                break;
            default:
                LaserBladeVisual.PartColor gripColor = visual.getGripColor();
                gripColor.color = color.getGripColor();
        }

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

        sample = getColoringResult(output.copy());
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
        return ModRecipeSerializers.COLOR;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.SMITHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ColorRecipe> {
        @Override
        public ColorRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
            Ingredient addition = Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition"));
            JsonObject result = JSONUtils.getJsonObject(json, "result");
            String part = result.get("part").getAsString();
            ColorPart colorPart = ColorPart.byPartName(part);
            String color = result.get("color").getAsString();
            DyeColor dyeColor = DyeColor.byTranslationKey(color, DyeColor.WHITE);
            return new ColorRecipe(recipeId, base, addition, colorPart, Color.get(dyeColor.getId()));
        }

        @Nullable
        @Override
        public ColorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient base = Ingredient.read(buffer);
            Ingredient addition = Ingredient.read(buffer);
            ColorPart colorPart = ColorPart.byIndex(buffer.readInt());
            Color color = Color.get(buffer.readInt());
            return new ColorRecipe(recipeId, base, addition, colorPart, color);
        }

        @Override
        public void write(PacketBuffer buffer, ColorRecipe recipe) {
            recipe.base.write(buffer);
            recipe.addition.write(buffer);
            buffer.writeInt(recipe.part.getIndex());
            buffer.writeInt(recipe.color.ordinal());
        }
    }
}
