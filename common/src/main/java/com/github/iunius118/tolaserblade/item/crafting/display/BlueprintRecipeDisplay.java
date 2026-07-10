package com.github.iunius118.tolaserblade.item.crafting.display;

import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.crafting.BlueprintRecipeInput;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.ArrayList;
import java.util.List;

public record BlueprintRecipeDisplay(List<SlotDisplay> ingredients, SlotDisplay result, SlotDisplay craftingStation)
        implements RecipeDisplay {
    public static final MapCodec<BlueprintRecipeDisplay> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(o -> o.ingredients),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(o -> o.result),
                            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(o -> o.craftingStation)
                    ).apply(i, BlueprintRecipeDisplay::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, BlueprintRecipeDisplay> STREAM_CODEC =
            StreamCodec.composite(
                    SlotDisplay.STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.ingredients,
                    SlotDisplay.STREAM_CODEC, o -> o.result,
                    SlotDisplay.STREAM_CODEC, o -> o.craftingStation,
                    BlueprintRecipeDisplay::new
            );
    public static final RecipeDisplay.Type<BlueprintRecipeDisplay> TYPE =
            new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public Type<BlueprintRecipeDisplay> type() {
        return TYPE;
    }

    public static BlueprintRecipeDisplay of(List<SlotDisplay> ingredients, SlotDisplay result) {
        return new BlueprintRecipeDisplay(ingredients, result, new SlotDisplay.ItemSlotDisplay(ModItems.BL_BLUEPRINT));
    }

    public static BlueprintRecipeDisplay fromIngredients(List<Ingredient> ingredients, SlotDisplay result) {
        int size = ingredients.size();
        List<SlotDisplay> slotDisplays = new ArrayList<>();

        for (int i = 0; i < BlueprintRecipeInput.SIZE; i++) {
            if (i < size) {
                slotDisplays.add(ingredients.get(i).display());
            } else {
                slotDisplays.add(SlotDisplay.Empty.INSTANCE);
            }
        }

        return BlueprintRecipeDisplay.of(slotDisplays, result);
    }
}
