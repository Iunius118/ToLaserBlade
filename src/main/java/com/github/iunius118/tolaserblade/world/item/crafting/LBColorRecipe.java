package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
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

public class LBColorRecipe extends UpgradeRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final LaserBladeColorPart part;
    private final int color;
    private final ResourceLocation recipeId;
    private ItemStack sample;

    public LBColorRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, LaserBladeColorPart part, int color) {
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
    public boolean matches(Container container, Level level) {
        if (!base.test(container.getItem(0)) || !addition.test(container.getItem(1))) return false;

        ItemStack baseStack = container.getItem(0);
        LaserBladeVisual visual = LaserBlade.visualOf(baseStack);

        switch (part) {
            case INNER_BLADE -> {
                LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
                return innerColor.color != color || isSwitchingBlendModeColor();
            }
            case OUTER_BLADE -> {
                LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
                return outerColor.color != color || isSwitchingBlendModeColor();
            }
            default -> {
                LaserBladeVisual.PartColor gripColor = visual.getGripColor();
                return gripColor.color != color || isSwitchingBlendModeColor();
            }
        }
    }

    private boolean isSwitchingBlendModeColor() {
        return LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE.getBladeColor() == color;
    }

    @Override
    public ItemStack assemble(Container container) {
        ItemStack baseStack = container.getItem(0);
        ItemStack itemstack = baseStack.copy();
        return getColoringResult(itemstack);
    }

    private ItemStack getColoringResult(ItemStack input) {
        LaserBladeVisual visual = LaserBlade.visualOf(input);

        switch (part) {
            case INNER_BLADE -> {
                LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
                changeBladeColor(innerColor);
            }
            case OUTER_BLADE -> {
                LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
                changeBladeColor(outerColor);
            }
            default -> {
                LaserBladeVisual.PartColor gripColor = visual.getGripColor();
                gripColor.color = color;
            }
        }

        visual.write(input.getOrCreateTag());
        return input;
    }

    private void changeBladeColor(LaserBladeVisual.PartColor bladePartColor) {
        if (isSwitchingBlendModeColor()) {
            bladePartColor.switchBlendMode();
        } else {
            bladePartColor.color = color;
        }
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
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COLOR;
    }

    @Override
    public RecipeType<?> getType() {
        // Treat as RecipeType.SMITHING to use on smithing table
        return super.getType();
    }

    public static class Serializer implements RecipeSerializer<LBColorRecipe> {
        @Override
        public LBColorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            JsonObject result = GsonHelper.getAsJsonObject(json, "result");
            JsonElement part = result.get("part");
            LaserBladeColorPart colorPart = LaserBladeColorPart.byPartName(part.getAsString());
            JsonElement color = result.get("color");
            int colorValue = color.getAsInt();
            return new LBColorRecipe(recipeId, base, addition, colorPart, colorValue);
        }

        @Nullable
        @Override
        public LBColorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            LaserBladeColorPart colorPart = LaserBladeColorPart.byIndex(buffer.readInt());
            int color = buffer.readInt();
            return new LBColorRecipe(recipeId, base, addition, colorPart, color);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, LBColorRecipe recipe) {
            LaserBladeColorPart part = recipe.part;

            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeInt(part.getIndex());
            buffer.writeInt(recipe.color);
        }
    }
}
