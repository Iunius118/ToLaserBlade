package com.github.iunius118.tolaserblade.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class CraftingRecipe extends BlueprintRecipe {
    public static final MapCodec<CraftingRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(o -> o.ingredients),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result)
                    ).apply(i, CraftingRecipe::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, CraftingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    CommonInfo.STREAM_CODEC, o -> o.commonInfo,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.ingredients,
                    ItemStackTemplate.STREAM_CODEC, o -> o.result,
                    CraftingRecipe::new
            );
    public static final RecipeSerializer<CraftingRecipe> SERIALIZER
            = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ItemStackTemplate result;

    public CraftingRecipe(CommonInfo commonInfo, List<Ingredient> ingredients, ItemStackTemplate result) {
        super(commonInfo, ingredients);
        this.result = result;
    }

    @Override
    public ItemStack assemble(BlurprintRecipeInput input) {
        return result.create();
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<BlurprintRecipeInput>> getType() {
        return ModRecipeTypes.CRAFTING;
    }
}
