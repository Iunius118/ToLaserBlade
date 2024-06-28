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
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class LBModelChangeRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private ItemStack sample;

    public LBModelChangeRecipe(Ingredient template, Ingredient base, Ingredient addition, int type) {
        super(template, base, addition, getResultItemStack(base));
        this.template = template;
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
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        if (sample != null) {
            return sample;
        }

        ItemStack output = super.getResultItem(provider);

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getResult(output.copy());
        return sample;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MODEL_CHANGE;
    }

    public static class Serializer implements RecipeSerializer<LBModelChangeRecipe> {
        private static final MapCodec<LBModelChangeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.base),
                        Ingredient.CODEC.fieldOf("addition").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.addition),
                        Codec.INT.fieldOf("model_type").codec().fieldOf("result").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.type)
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
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int type = ByteBufCodecs.INT.decode(buffer);
            return new LBModelChangeRecipe(template, base, addition, type);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, LBModelChangeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ByteBufCodecs.INT.encode(buffer, recipe.type);
        }
    }
}
