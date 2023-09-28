package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class LBColorRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final LaserBladeColorPart part;
    private final int color;
    private ItemStack sample;

    public LBColorRecipe(Ingredient template, Ingredient base, Ingredient addition, LaserBladeColorPart part, int color) {
        super(template, base, addition, getResultItemStack(base));
        this.template = template;
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
        if (!super.matches(container, level)) {
            return false;
        }

        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        var visual = LaserBladeVisual.of(baseStack);

        switch (part) {
            case INNER_BLADE -> {
                return (visual.getInnerColor() != color) || isSwitchingBlendModeColor();
            }
            case OUTER_BLADE -> {
                return (visual.getOuterColor() != color) || isSwitchingBlendModeColor();
            }
            default -> {
                return visual.getGripColor() != color;
            }
        }
    }

    private boolean isSwitchingBlendModeColor() {
        return LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE.getBladeColor() == color;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack itemstack = baseStack.copy();
        return getColoringResult(itemstack);
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        if (sample != null) {
            return sample;
        }

        ItemStack output = super.getResultItem(registryAccess);

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getColoringResult(output.copy());
        return sample;
    }

    private ItemStack getColoringResult(ItemStack input) {
        var writer = LaserBladeVisual.Writer.of(input);

        switch (part) {
            case INNER_BLADE -> {
                if (isSwitchingBlendModeColor()) {
                    writer.switchIsInnerSubColor();
                } else {
                    writer.writeInnerColor(color);
                }
            }
            case OUTER_BLADE -> {
                if (isSwitchingBlendModeColor()) {
                    writer.switchIsOuterSubColor();
                } else {
                    writer.writeOuterColor(color);
                }
            }
            default -> {
                writer.writeGripColor(color);
            }
        }

        return input;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COLOR;
    }

    public static class Serializer implements RecipeSerializer<LBColorRecipe> {
        private static final Codec<LBColorRecipe> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(lBColorRecipe -> lBColorRecipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(lBColorRecipe -> lBColorRecipe.base),
                        Ingredient.CODEC.fieldOf("addition").forGetter(lBColorRecipe -> lBColorRecipe.addition),
                        Codec.pair(Codec.STRING.fieldOf("part").codec(), Codec.INT.fieldOf("color").codec())
                                .fieldOf("result").forGetter(lBColorRecipe -> new Pair<>(lBColorRecipe.part.getPartName(), lBColorRecipe.color))
                ).apply(instance, (template, base, addition, result) ->
                        new LBColorRecipe(template, base, addition, LaserBladeColorPart.byPartName(result.getFirst()), result.getSecond())));

        @Override
        public Codec<LBColorRecipe> codec() {
            return CODEC;
        }

        @Override
        public LBColorRecipe fromNetwork(FriendlyByteBuf buffer) {
            Ingredient template = Ingredient.fromNetwork(buffer);
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            LaserBladeColorPart colorPart = LaserBladeColorPart.byIndex(buffer.readInt());
            int color = buffer.readInt();
            return new LBColorRecipe(template, base, addition, colorPart, color);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, LBColorRecipe recipe) {
            LaserBladeColorPart part = recipe.part;

            recipe.template.toNetwork(buffer);
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeInt(part.getIndex());
            buffer.writeInt(recipe.color);
        }
    }
}
