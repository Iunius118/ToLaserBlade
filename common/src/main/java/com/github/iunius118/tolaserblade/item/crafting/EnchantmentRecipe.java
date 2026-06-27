package com.github.iunius118.tolaserblade.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class EnchantmentRecipe extends BlueprintRecipe {
    public static final MapCodec<EnchantmentRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(
                    i -> i.group(
                            CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(o -> o.ingredients),
                            Enchantment.CODEC.fieldOf("enchantmant").forGetter(o -> o.enchantment)
                    ).apply(i, EnchantmentRecipe::new)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    CommonInfo.STREAM_CODEC, o -> o.commonInfo,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.ingredients,
                    Enchantment.STREAM_CODEC, o -> o.enchantment,
                    EnchantmentRecipe::new
            );
    public static final RecipeSerializer<EnchantmentRecipe> SERIALIZER
            = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Holder<Enchantment> enchantment;

    public EnchantmentRecipe(CommonInfo commonInfo, List<Ingredient> ingredients, Holder<Enchantment> enchantment) {
        super(commonInfo, ingredients);
        this.enchantment = enchantment;
    }

    @Override
    public boolean matches(BlurprintRecipeInput input, Level level) {
        if (super.matches(input, level) && enchantment.isBound()) {
            return input.base().getEnchantments().getLevel(enchantment) < enchantment.value().getMaxLevel();
        }

        return false;
    }

    @Override
    public ItemStack assemble(BlurprintRecipeInput input) {
        ItemStack result = input.base().copy();
        // Upgrade the item stack's enchantments with the upgrade's enchantments
        EnchantmentHelper.updateEnchantments(result, mutableEnchantments -> {
            final int oldLevel = mutableEnchantments.getLevel(enchantment);
            if (enchantment.isBound()) {
                // Remove incompatible enchantments
                mutableEnchantments.removeIf(e -> !isCompatibleWith(e, enchantment));
                // Add or Increase enchantment levels
                mutableEnchantments.set(enchantment, oldLevel + 1);
            }
        });
        return result;
    }

    private boolean isCompatibleWith(Holder<Enchantment> e1, Holder<Enchantment> e2) {
        // Allow Laser Blade to have Silk Touch and Looting together
        return Enchantment.areCompatible(e1, e2)
                || (e1.is(Enchantments.SILK_TOUCH) && e2.is(Enchantments.LOOTING))
                || (e1.is(Enchantments.LOOTING) && e2.is(Enchantments.SILK_TOUCH));
    }

    @Override
    public RecipeSerializer<? extends BlueprintRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<BlurprintRecipeInput>> getType() {
        return ModRecipeTypes.ENCHANTMENT;
    }
}
