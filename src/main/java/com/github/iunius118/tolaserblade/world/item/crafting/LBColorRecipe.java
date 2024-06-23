package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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
        var appearance = LaserBladeAppearance.of(baseStack);

        switch (part) {
            case INNER_BLADE -> {
                return (appearance.getInnerColor() != color) || isSwitchingBlendModeColor();
            }
            case OUTER_BLADE -> {
                return (appearance.getOuterColor() != color) || isSwitchingBlendModeColor();
            }
            default -> {
                return appearance.getGripColor() != color;
            }
        }
    }

    private boolean isSwitchingBlendModeColor() {
        return LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE.getOuterColor() == color;
    }

    @Override
    public ItemStack assemble(Container container, HolderLookup.Provider provider) {
        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack itemstack = baseStack.copy();
        return getColoringResult(itemstack);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        if (sample != null) {
            return sample;
        }

        ItemStack output = super.getResultItem(provider);

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getColoringResult(output.copy());
        return sample;
    }

    private ItemStack getColoringResult(ItemStack input) {
        var appearance = LaserBladeAppearance.of(input);

        switch (part) {
            case INNER_BLADE -> {
                if (isSwitchingBlendModeColor()) {
                    appearance.switchInnerSubColor();
                } else {
                    appearance.setInnerColor(color);
                }
            }
            case OUTER_BLADE -> {
                if (isSwitchingBlendModeColor()) {
                    appearance.switchOuterSubColor();
                } else {
                    appearance.setOuterColor(color);
                }
            }
            default -> {
                appearance.setGripColor(color);
            }
        }

        appearance.setTo(input);
        return input;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COLOR;
    }

    public static class Serializer implements RecipeSerializer<LBColorRecipe> {
        private static final MapCodec<LBColorRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(lBColorRecipe -> lBColorRecipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(lBColorRecipe -> lBColorRecipe.base),
                        Ingredient.CODEC.fieldOf("addition").forGetter(lBColorRecipe -> lBColorRecipe.addition),
                        Codec.pair(Codec.STRING.fieldOf("part").codec(), Codec.INT.fieldOf("color").codec())
                                .fieldOf("result").forGetter(lBColorRecipe -> new Pair<>(lBColorRecipe.part.getPartName(), lBColorRecipe.color))
                ).apply(instance, (template, base, addition, result) ->
                        new LBColorRecipe(template, base, addition, LaserBladeColorPart.byPartName(result.getFirst()), result.getSecond()))
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, LBColorRecipe> STREAM_CODEC = StreamCodec.of(
                LBColorRecipe.Serializer::toNetwork, LBColorRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<LBColorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LBColorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static LBColorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            var colorPart = LaserBladeColorPart.byIndex(ByteBufCodecs.INT.decode(buffer));
            int color = ByteBufCodecs.INT.decode(buffer);
            return new LBColorRecipe(template, base, addition, colorPart, color);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, LBColorRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ByteBufCodecs.INT.encode(buffer, recipe.part.getIndex());
            ByteBufCodecs.INT.encode(buffer, recipe.color);
        }
    }
}
