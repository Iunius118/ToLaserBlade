package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeID;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class LBUpgradeRecipe extends LBSmithingRecipe {
    private final ResourceLocation upgradeId;
    private Upgrade upgrade;

    public LBUpgradeRecipe(Optional<Ingredient> template, Optional<Ingredient> base, Optional<Ingredient> addition, ResourceLocation upgradeId) {
        super(template, base, addition);
        this.upgradeId = upgradeId;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, Level level) {
        if (!super.matches(smithingRecipeInput, level)) {
            return false;
        }

        ItemStack baseStack = smithingRecipeInput.base();
        ItemStack additionalStack = smithingRecipeInput.addition();
        HolderLookup.Provider provider = level.registryAccess();
        Upgrade upgrade = getUpgrade();
        return upgrade.canApply(baseStack, additionalStack, provider);
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.Provider provider) {
        ItemStack baseStack = smithingRecipeInput.base();
        ItemStack itemstack = baseStack.copy();
        return getUpgradingResult(itemstack, provider);
    }

    private ItemStack getUpgradingResult(ItemStack input, HolderLookup.Provider provider) {
        Upgrade upgrade = getUpgrade();
        UpgradeResult result = upgrade.apply(input, 0, provider);
        return result.itemStack();
    }

    private Upgrade getUpgrade() {
        if (upgrade != null) {
            return upgrade;
        }

        upgrade = UpgradeManager.getUpgrade(upgradeId);
        return upgrade;
    }

    @Override
    protected ItemStack getDisplayResult(ItemStack result) {
        ResourceLocation efficiencyRemover = UpgradeID.EFFICIENCY_REMOVER.getID();
        ItemStack copied = result.copy();

        if (upgradeId.equals(efficiencyRemover)) {
            // Set hint of removing Efficiency to item-stack's display name
            MutableComponent componentContents = LaserBladeTextKey.KEY_TOOLTIP_REMOVE.translate(Component.translatable("enchantment.minecraft.efficiency"));
            MutableComponent info = Component.literal(componentContents.getString());
            copied.set(DataComponents.CUSTOM_NAME, info);
            return copied;
        } else {
            // For other upgrades, return result without applying upgrade temporarily
            return copied;
        }
    }

    @Override
    public RecipeSerializer<LBUpgradeRecipe> getSerializer() {
        return ModRecipeSerializers.UPGRADE;
    }

    public static class Serializer implements RecipeSerializer<LBUpgradeRecipe> {
        private static final MapCodec<LBUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Ingredient.CODEC.optionalFieldOf("template").forGetter(recipe -> recipe.template),
                        Ingredient.CODEC.optionalFieldOf("base").forGetter(recipe -> recipe.base),
                        Ingredient.CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
                        ResourceLocation.CODEC.fieldOf("type").codec().fieldOf("result").forGetter(recipe -> recipe.upgradeId)
                ).apply(instance, LBUpgradeRecipe::new)
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, LBUpgradeRecipe> STREAM_CODEC = StreamCodec.of(
                LBUpgradeRecipe.Serializer::toNetwork, LBUpgradeRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<LBUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LBUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static LBUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Optional<Ingredient> template = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> base = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            Optional<Ingredient> addition = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buffer);
            ResourceLocation upgradeId = ResourceLocation.STREAM_CODEC.decode(buffer);
            return new LBUpgradeRecipe(template, base, addition, upgradeId);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, LBUpgradeRecipe recipe) {
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.upgradeId);
        }
    }
}
