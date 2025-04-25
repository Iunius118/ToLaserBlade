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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class LBColorRecipe extends LBSmithingRecipe {
    final LaserBladeColorPart part;
    final int color;

    public LBColorRecipe(Optional<Ingredient> template, Ingredient base, Optional<Ingredient> addition, LaserBladeColorPart part, int color) {
        super(template, base, addition);
        this.part = part;
        this.color = color;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, Level level) {
        if (!super.matches(smithingRecipeInput, level)) {
            return false;
        }

        ItemStack baseStack = smithingRecipeInput.base();
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
    public ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.Provider provider) {
        ItemStack baseStack = smithingRecipeInput.base();
        ItemStack itemstack = baseStack.copy();
        return getColoringResult(itemstack);
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
    protected ItemStack getDisplayResult(ItemStack result) {
        return getColoringResult(result.copy());
    }

    @Override
    public RecipeSerializer<LBColorRecipe> getSerializer() {
        return ModRecipeSerializers.COLOR;
    }

    public static class Serializer implements RecipeSerializer<LBColorRecipe> {
        private static final MapCodec<LBColorRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Ingredient.CODEC.optionalFieldOf("template").forGetter(recipe -> recipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                        Ingredient.CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
                        Codec.pair(Codec.STRING.fieldOf("part").codec(), Codec.INT.fieldOf("color").codec())
                                .fieldOf("result").forGetter(recipe -> new Pair<>(recipe.part.getPartName(), recipe.color))
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
            Optional<Ingredient> template = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> addition = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            var colorPart = LaserBladeColorPart.byIndex(ByteBufCodecs.INT.decode(buffer));
            int color = ByteBufCodecs.INT.decode(buffer);
            return new LBColorRecipe(template, base, addition, colorPart, color);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, LBColorRecipe recipe) {
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ByteBufCodecs.INT.encode(buffer, recipe.part.getIndex());
            ByteBufCodecs.INT.encode(buffer, recipe.color);
        }
    }
}
