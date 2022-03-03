package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeResult;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

public class LBUpgradeRecipe extends UpgradeRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private final ResourceLocation recipeId;
    private Upgrade upgrade;
    private ItemStack sample;

    public LBUpgradeRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        super(recipeId, base, addition, getResultItemStack(base));
        this.recipeId = recipeId;
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
        if (base.test(container.getItem(0)) && addition.test(container.getItem(1))) {
            ItemStack baseStack = container.getItem(0);
            ItemStack additionalStack = container.getItem(1);
            Upgrade upgrade = getUpgrade();
            return upgrade.canApply(baseStack, additionalStack);
        }

        return false;
    }

    @Override
    public ItemStack assemble(Container container) {
        ItemStack baseStack = container.getItem(0);
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

        upgrade = UpgradeManager.get(upgradeId);
        return upgrade;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return super.canCraftInDimensions(i, j);
    }

    @Override
    public ItemStack getResultItem() {
        if (sample != null) return sample;

        ItemStack output = super.getResultItem();
        sample = output.copy();

        if (sample.isEmpty()) {
            return sample;
        }

        ResourceLocation efficiencyRemover = ModItemTags.EFFICIENCY_REMOVER.location();

        if (upgradeId.equals(efficiencyRemover)) {
            // Set hint of removing Efficiency to item-stack's display name
            TranslatableComponent textComponent = LaserBladeTextKey.KEY_TOOLTIP_REMOVE.translate(new TranslatableComponent("enchantment.minecraft.efficiency"));
            TextComponent info = new TextComponent(textComponent.getString());
            CompoundTag nbt = sample.getOrCreateTagElement("display");
            nbt.putString("Name", Component.Serializer.toJson(info));
        } else {
            // Apply upgrade to sample item
            sample = getUpgradingResult(output);
        }

        return sample;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.UPGRADE;
    }

    @Override
    public RecipeType<?> getType() {
        // Treat as RecipeType.SMITHING to use on smithing table
        return super.getType();
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<LBUpgradeRecipe> {
        @Override
        public LBUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            ResourceLocation upgradeId = new ResourceLocation(GsonHelper.getAsJsonObject(json, "result").get("type").getAsString());
            return new LBUpgradeRecipe(recipeId, base, addition, upgradeId);
        }

        @Nullable
        @Override
        public LBUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            ResourceLocation upgradeId = buffer.readResourceLocation();
            return new LBUpgradeRecipe(recipeId, base, addition, upgradeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf  buffer, LBUpgradeRecipe recipe) {
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeResourceLocation(recipe.upgradeId);
        }
    }
}
