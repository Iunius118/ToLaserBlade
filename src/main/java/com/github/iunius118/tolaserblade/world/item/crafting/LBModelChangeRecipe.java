package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
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

public class LBModelChangeRecipe extends LBSmithingRecipe {
    private final int type;

    public LBModelChangeRecipe(Optional<Ingredient> template, Optional<Ingredient> base, Optional<Ingredient> addition, int type) {
        super(template, base, addition);
        this.type = type;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, Level level) {
        if (!super.matches(smithingRecipeInput, level)) {
            return false;
        }

        ItemStack baseStack = smithingRecipeInput.base();
        int baseType = LaserBladeAppearance.of(baseStack).getType();
        return type >= 0 && baseType != type;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.Provider provider) {
        ItemStack baseStack = smithingRecipeInput.base();
        ItemStack itemstack = baseStack.copy();
        return getResult(itemstack);
    }

    private ItemStack getResult(ItemStack input) {
        LaserBladeAppearance.of(input).setType(type).setTo(input);
        return input;
    }

    @Override
    protected ItemStack getDisplayResult(ItemStack result) {
        return getResult(result.copy());
    }

    @Override
    public RecipeSerializer<LBModelChangeRecipe> getSerializer() {
        return ModRecipeSerializers.MODEL_CHANGE;
    }

    public static class Serializer implements RecipeSerializer<LBModelChangeRecipe> {
        private static final MapCodec<LBModelChangeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Ingredient.CODEC.optionalFieldOf("template").forGetter(recipe -> recipe.template),
                        Ingredient.CODEC.optionalFieldOf("base").forGetter(recipe -> recipe.base),
                        Ingredient.CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
                        Codec.INT.fieldOf("model_type").codec().fieldOf("result").forGetter(recipe -> recipe.type)
                ).apply(instance, LBModelChangeRecipe::new)
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, LBModelChangeRecipe> STREAM_CODEC = StreamCodec.of(
                LBModelChangeRecipe.Serializer::toNetwork, LBModelChangeRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<LBModelChangeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LBModelChangeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static LBModelChangeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Optional<Ingredient> template = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> base = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> addition = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            int type = ByteBufCodecs.INT.decode(buffer);
            return new LBModelChangeRecipe(template, base, addition, type);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, LBModelChangeRecipe recipe) {
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ByteBufCodecs.INT.encode(buffer, recipe.type);
        }
    }
}
