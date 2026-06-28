package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.item.LaserBladeColor;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

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
    public static final RecipeSerializer<CraftingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ItemStackTemplate result;

    public CraftingRecipe(CommonInfo commonInfo, List<Ingredient> ingredients, ItemStackTemplate result) {
        super(commonInfo, ingredients);
        this.result = result;
    }

    @Override
    public ItemStack assemble(BlueprintRecipeInput input) {
        // Copy and merge components of base item stack
        var componentsPatch = input.base().getComponentsPatch();
        var resultStack = result.apply(componentsPatch);

        if (resultStack.has(DataComponents.DAMAGE)) {
            // Repair damage
            resultStack.set(DataComponents.DAMAGE, 0);
        }

        return resultStack;
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<BlueprintRecipeInput>> getType() {
        return ModRecipeTypes.CRAFTING;
    }

    public static void applyGiftCode(BlueprintRecipeInput input, ItemStack result, Level level) {
        if (!(input.base().is(ModItems.LB_CASING) && result.is(ModItems.LASER_BLADE))) {
            return;
        }

        var component = result.get(DataComponents.CUSTOM_NAME);

        if (component == null
                || result.has(DataComponents.CUSTOM_MODEL_DATA)
                || result.isEnchanted()) {
            return;
        }

        String name = component.getString();

        if (!(name.equals("GIFT") || name.equals("\u304a\u305f\u304b\u3089"))) {
            return;
        }

        var laserBlade = level.holderLookup(Registries.ENCHANTMENT).get(ModEnchantments.LASER_BLADE);
        var lightElement = level.holderLookup(Registries.ENCHANTMENT).get(ModEnchantments.LIGHT_ELEMENT);
        var efficient = level.holderLookup(Registries.ENCHANTMENT).get(Enchantments.EFFICIENCY);

        if (laserBlade.isEmpty() || lightElement.isEmpty() || efficient.isEmpty()) {
            return;
        }

        // Apply gift code to resul item
        result.enchant(laserBlade.get(), 2);
        result.enchant(lightElement.get(), 2);
        result.enchant(efficient.get(), 2);
        result.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of(), List.of(true, true), List.of(),
                List.of(LaserBladeColor.BROWN.gripColor(), LaserBladeColor.LIME.outerBladeColor())));
        result.remove(DataComponents.CUSTOM_NAME);
    }
}
