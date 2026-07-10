package com.github.iunius118.tolaserblade.item.crafting;

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
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class RepairRecipe extends BlueprintRecipe {
    public static final MapCodec<RepairRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(o -> o.ingredients),
                            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("rate", 0.25f).forGetter(o -> o.rate)
                    ).apply(i, RepairRecipe::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, RepairRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC, o -> o.commonInfo,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.ingredients,
                    ByteBufCodecs.FLOAT, o -> o.rate,
                    RepairRecipe::new
            );
    public static final RecipeSerializer<RepairRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final float rate;

    public RepairRecipe(CommonInfo commonInfo, List<Ingredient> ingredients, float rate) {
        super(commonInfo, ingredients);
        this.rate = rate;
    }

    @Override
    public boolean matches(BlueprintRecipeInput input, Level level) {
        if (!super.matches(input, level)) {
            return false;
        }

        ItemStack base = input.base();
        return base.isDamaged();
    }

    @Override
    public ItemStack assemble(BlueprintRecipeInput input) {
        ItemStack base = input.base();
        int newDamage = Math.max(base.getDamageValue() - (int) (base.getMaxDamage() * rate), 0);
        ItemStack result = base.copy();
        result.setDamageValue(newDamage);
        return result;
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<BlueprintRecipeDisplay> getRecipeDisplay() {
        List<SlotDisplay> inputSlots = new ArrayList<>();

        for (int i = 0; i < BlueprintRecipeInput.SIZE; i++) {
            if (i == 0) {
                inputSlots.add(new ModSlotDisplay.RepairSlotDemo(ingredients.getFirst().display(), 0));
            } else if (i < ingredients.size()) {
                inputSlots.add(ingredients.get(i).display());
            } else {
                inputSlots.add(SlotDisplay.Empty.INSTANCE);
            }
        }

        return List.of(
                BlueprintRecipeDisplay.of(inputSlots,
                        new ModSlotDisplay.RepairSlotDemo(ingredients.getFirst().display(), rate))
        );
    }
}
