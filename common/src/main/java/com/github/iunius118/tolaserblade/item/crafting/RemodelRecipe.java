package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import com.github.iunius118.tolaserblade.item.crafting.display.BlueprintRecipeDisplay;
import com.github.iunius118.tolaserblade.item.crafting.display.ModSlotDisplay;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;

public class RemodelRecipe extends BlueprintRecipe {
    public static final MapCodec<RemodelRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(o -> o.ingredients),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("model").forGetter(o -> o.modelType)
                    ).apply(i, RemodelRecipe::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, RemodelRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC, o -> o.commonInfo,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.ingredients,
                    ByteBufCodecs.INT, o -> o.modelType,
                    RemodelRecipe::new
            );
    public static final RecipeSerializer<RemodelRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final int modelType;

    public RemodelRecipe(Recipe.CommonInfo commonInfo, List<Ingredient> ingredients, int modelType) {
        super(commonInfo, ingredients);
        this.modelType = modelType;
    }

    @Override
    public boolean shouldConsumeIngredient() {
        return false;
    }

    @Override
    public boolean matches(BlueprintRecipeInput input, Level level) {
        if (super.matches(input, level)) {
            return input.base().getOrDefault(ModDataComponents.MODEL, 0) != modelType;
        }

        return false;
    }

    @Override
    public ItemStack assemble(BlueprintRecipeInput input) {
        ItemStack result = input.base().copy();
        result.set(ModDataComponents.MODEL, modelType);
        return result;
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<BlueprintRecipeDisplay> getRecipeDisplay() {
        return List.of(
                BlueprintRecipeDisplay.fromIngredients(ingredients,
                        new ModSlotDisplay.RemodelSlotDemo(ingredients.getFirst().display(), modelType))
        );
    }
}
