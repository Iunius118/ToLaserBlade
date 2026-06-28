package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.item.component.BlendModes;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import com.github.iunius118.tolaserblade.tag.ModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ColoringRecipe extends BlueprintRecipe {
    public static final int PARTS_MAX = BlueprintRecipeInput.SIZE - 1;
    public static final MapCodec<ColoringRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.fieldOf("base").forGetter(o -> o.ingredients.get(0)),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(o -> o.ingredients.get(1)),
                            PartColor.CODEC.listOf(PARTS_MAX, PARTS_MAX).fieldOf("colors").forGetter(o -> o.colors)
                    ).apply(i, ColoringRecipe::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, ColoringRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC, o -> o.commonInfo,
                    Ingredient.CONTENTS_STREAM_CODEC, o -> o.ingredients.get(0),
                    Ingredient.CONTENTS_STREAM_CODEC, o -> o.ingredients.get(1),
                    PartColor.STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.colors,
                    ColoringRecipe::new
            );
    public static final RecipeSerializer<ColoringRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final List<PartColor> colors;

    public ColoringRecipe(Recipe.CommonInfo commonInfo, Ingredient base, Ingredient ingredient,
                          List<PartColor> colors) {
        super(commonInfo, List.of(base, ingredient));
        this.colors = colors;
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

        // Chack input slot #0
        ItemStack baseStack = input.base();

        if (!base.test(baseStack)) {
            // Slot 0 did not match
            return false;
        }

        boolean found = false;
        int emptySlots = 0;

        // Chack input slots #1+
        for (int i = 0; i < PARTS_MAX; i++) {
            if (input.getItem(i + 1).isEmpty()) {
                emptySlots++;
            } else if (!found && ingredient.test(input.getItem(i + 1))) {
                var data = baseStack.getOrDefault(DataComponents.CUSTOM_MODEL_DATA, CustomModelData.EMPTY);
                var blendModes = baseStack.getOrDefault(ModDataComponents.BLEND_MODES, BlendModes.DEFAULT);
                boolean baseFlag = Objects.requireNonNullElse(data.getBoolean(i), false);
                Integer baseColor = data.getColor(i);
                var partColor = colors.get(i);

                if (!baseFlag || baseColor == null || baseColor != partColor.color()
                        || (i > 0 && canChangeMode(partColor, blendModes.getMode(i - 1)))) {
                    found = true;
                }
            }
        }

        return found && emptySlots == PARTS_MAX - 1;
    }

    private boolean canChangeMode(PartColor partColor, boolean blendMode) {
        if (blendMode) {
            return partColor.blendMode() == PartColor.BlendMode.ADDITIVE;
        } else {
            return partColor.blendMode() == PartColor.BlendMode.SUBTRACTIVE;
        }
    }

    @Override
    public ItemStack assemble(BlueprintRecipeInput input) {
        ItemStack base = input.base();

        for (int i = 0; i < PARTS_MAX; i++) {
            if (!input.getItem(i + 1).isEmpty()) {
                return recolor(base, i);
            }
        }

        return base;
    }

    private ItemStack recolor(ItemStack base, int index) {
        var oldData = base.getOrDefault(DataComponents.CUSTOM_MODEL_DATA, CustomModelData.EMPTY);
        var oldBlendModes = base.getOrDefault(ModDataComponents.BLEND_MODES, BlendModes.DEFAULT);
        List<Boolean> newFlags = new ArrayList<>(PARTS_MAX);
        List<Integer> newColors = new ArrayList<>(PARTS_MAX);
        List<Boolean> newBlendModes = new ArrayList<>(PARTS_MAX - 1);

        for (int i = 0; i < PARTS_MAX; i++) {
            var partColor = colors.get(i);
            newFlags.add(i == index || Objects.requireNonNullElse(oldData.getBoolean(i), false));
            newColors.add((i == index) ? partColor.color() : Objects.requireNonNullElse(oldData.getColor(i), -1));

            if (i > 0) {
                if (i == index && partColor.blendMode() != PartColor.BlendMode.UNCHANGED) {
                    newBlendModes.add(partColor.blendMode() == PartColor.BlendMode.SUBTRACTIVE);
                } else {
                    newBlendModes.add(oldBlendModes.getMode(i - 1));
                }
            }
        }

        ItemStack result = base.copy();
        result.set(DataComponents.CUSTOM_MODEL_DATA,
                new CustomModelData(oldData.floats(), newFlags, oldData.strings(), newColors));
        return result;
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<BlueprintRecipeInput>> getType() {
        return ModRecipeTypes.COLORING;
    }

    public record PartColor(int color, BlendMode blendMode) {
        private static final Codec<PartColor> PART_COLOR_CODEC =
                RecordCodecBuilder.create(
                        i -> i.group(
                                ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("color").forGetter(PartColor::color),
                                Codec.STRING.optionalFieldOf("blend_mode", BlendMode.UNCHANGED.id())
                                        .forGetter(o -> o.blendMode().id())
                        ).apply(i, PartColor::new)
                );
        public static final Codec<PartColor> CODEC =
                Codec.withAlternative(PART_COLOR_CODEC, ExtraCodecs.ARGB_COLOR_CODEC,
                        n -> new PartColor(n, BlendMode.UNCHANGED));
        public static final StreamCodec<RegistryFriendlyByteBuf, PartColor> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.INT, PartColor::color,
                        ByteBufCodecs.INT, o -> o.blendMode().ordinal(),
                        PartColor::new
                );

        public PartColor(int color, String id) {
            this(color, BlendMode.of(id));
        }

        public PartColor(int color, int index) {
            this(color, BlendMode.of(index));
        }

        public enum BlendMode {
            UNCHANGED("unchanged"),
            ADDITIVE("additive"),
            SUBTRACTIVE("subtractive"),
            ;

            private String id;

            BlendMode(String id) {
                this.id = id;
            }

            public String id() {
                return id;
            }

            public static BlendMode of(int index) {
                if (index >= 0 && index < BlendMode.values().length) {
                    return BlendMode.values()[index];
                }

                return UNCHANGED;
            }

            public static BlendMode of(String id) {
                for (var blendMode : BlendMode.values()) {
                    if (blendMode.id().equals(id)) {
                        return blendMode;
                    }
                }

                return UNCHANGED;
            }
        }
    }
}
