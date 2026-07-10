package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.item.component.BlendModes;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import com.github.iunius118.tolaserblade.item.crafting.display.BlueprintRecipeDisplay;
import com.github.iunius118.tolaserblade.item.crafting.display.ModSlotDisplay;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BlendingRecipe extends BlueprintRecipe {
    public static final int PARTS_MAX = BlueprintRecipeInput.SIZE - 1;
    public static final MapCodec<BlendingRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.fieldOf("base").forGetter(o -> o.ingredients.get(0)),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(o -> o.ingredients.get(1))
                    ).apply(i, BlendingRecipe::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, BlendingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    CommonInfo.STREAM_CODEC, o -> o.commonInfo,
                    Ingredient.CONTENTS_STREAM_CODEC, o -> o.ingredients.get(0),
                    Ingredient.CONTENTS_STREAM_CODEC, o -> o.ingredients.get(1),
                    BlendingRecipe::new
            );
    public static final RecipeSerializer<BlendingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public BlendingRecipe(CommonInfo commonInfo, Ingredient base, Ingredient ingredient) {
        super(commonInfo, List.of(base, ingredient));
    }

    @Override
    public boolean shouldConsumeIngredient() {
        return false;
    }

    @Override
    public boolean matches(BlueprintRecipeInput input, Level level) {
        Ingredient base = ingredients.get(0);
        Ingredient ingredient = ingredients.get(1);

        if (base.isEmpty() || ingredient.isEmpty()) {
            // The recipe was invalid
            return false;
        }

        if (!base.test(input.base())) {
            // Slot 0 did not match
            return false;
        }

        if (!input.getItem(1).isEmpty()) {
            // Slot 1 must be empty
            return false;
        }

        boolean found = false;
        int emptySlots = 0;

        for (int i = 2; i < PARTS_MAX + 1; i++) {
            if (input.getItem(i).isEmpty()) {
                emptySlots++;
            } else if (!found && ingredient.test(input.getItem(i))) {
                found = true;
            }
        }

        return found && emptySlots == PARTS_MAX - 2;
    }

    @Override
    public ItemStack assemble(BlueprintRecipeInput input) {
        ItemStack base = input.base();

        for (int i = 2; i < PARTS_MAX + 1; i++) {
            if (!input.getItem(i).isEmpty()) {
                return switchMode(base, i - 2);
            }
        }

        return base;
    }

    private ItemStack switchMode(ItemStack base, int index) {
        var oldModes = base.getOrDefault(ModDataComponents.BLEND_MODES, BlendModes.DEFAULT);
        List<Boolean> newModes = new ArrayList<>(PARTS_MAX - 2);

        // Switch blend mode of the specified part
        for (int i = 0; i < PARTS_MAX - 1; i++) {
            // Switch: T != T -> F, T != F -> T. Leave: F != T -> T, F != F -> F
            newModes.add((i == index) != oldModes.getMode(i));
        }

        ItemStack result = base.copy();
        result.set(ModDataComponents.BLEND_MODES, new BlendModes(newModes));
        return result;
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<BlueprintRecipeDisplay> getRecipeDisplay() {
        return IntStream.range(0, PARTS_MAX - 1)
                .mapToObj(part -> {
                    SlotDisplay baseSlot = ingredients.getFirst().display();
                    List<SlotDisplay> inputSlots = new ArrayList<>();

                    for (int i = 0; i < BlueprintRecipeInput.SIZE; i++) {
                        if (i == 0) {
                            inputSlots.add(baseSlot);
                        } else if (i == part + 2) {
                            inputSlots.add(ingredients.get(1).display());
                        } else {
                            inputSlots.add(SlotDisplay.Empty.INSTANCE);
                        }
                    }

                    return BlueprintRecipeDisplay.of(
                            inputSlots,
                            new ModSlotDisplay.BlendingSlotDemo(baseSlot, part));
                })
                .toList();
    }
}
