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
import net.minecraft.world.Container;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class LBUpgradeRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private Upgrade upgrade;
    private ItemStack sample;

    public LBUpgradeRecipe(Ingredient template, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        super(template, base, addition, getResultItemStack(base));
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.upgradeId = upgradeId;
    }

    private static ItemStack getResultItemStack(Ingredient base) {
        ItemStack[] matchingStacks = base.getItems();

        if (matchingStacks.length > 0 && matchingStacks[0] != null) {
            return matchingStacks[0].copy();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (!super.matches(container, level)) {
            return false;
        }

        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack additionalStack = container.getItem(SmithingMenu.ADDITIONAL_SLOT);
        Upgrade upgrade = getUpgrade();
        return upgrade.canApply(baseStack, additionalStack);
    }

    @Override
    public ItemStack assemble(Container container, HolderLookup.Provider provider) {
        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack itemstack = baseStack.copy();
        return getUpgradingResult(itemstack);
    }

    private ItemStack getUpgradingResult(ItemStack input) {
        Upgrade upgrade = getUpgrade();
        UpgradeResult result = upgrade.apply(input, 0);
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
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        if (sample != null) {
            return sample;
        }

        ItemStack output = super.getResultItem(provider);
        sample = output.copy();

        if (sample.isEmpty()) {
            return sample;
        }

        ResourceLocation efficiencyRemover = UpgradeID.EFFICIENCY_REMOVER.getID();

        if (upgradeId.equals(efficiencyRemover)) {
            // Set hint of removing Efficiency to item-stack's display name
            MutableComponent componentContents = LaserBladeTextKey.KEY_TOOLTIP_REMOVE.translate(Component.translatable("enchantment.minecraft.efficiency"));
            MutableComponent info = Component.literal(componentContents.getString());
            sample.set(DataComponents.CUSTOM_NAME, info);
        } else {
            // Apply upgrade to sample item
            sample = getUpgradingResult(output);
        }

        return sample;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.UPGRADE;
    }

    public static class Serializer implements RecipeSerializer<LBUpgradeRecipe> {
        private static final MapCodec<LBUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.base),
                        Ingredient.CODEC.fieldOf("addition").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.addition),
                        ResourceLocation.CODEC.fieldOf("type").codec().fieldOf("result").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.upgradeId)
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
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ResourceLocation upgradeId = ResourceLocation.STREAM_CODEC.decode(buffer);
            return new LBUpgradeRecipe(template, base, addition, upgradeId);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, LBUpgradeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.upgradeId);
        }
    }
}
