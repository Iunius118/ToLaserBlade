package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeResult;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UpgradeRecipe extends SmithingRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private final ResourceLocation recipeId;
    private Upgrade upgrade;
    private ItemStack sample;

    public UpgradeRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
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
    public boolean matches(IInventory inv, World worldIn) {
        if (base.test(inv.getItem(0)) && addition.test(inv.getItem(1))) {
            ItemStack baseStack = inv.getItem(0);
            ItemStack additionalStack = inv.getItem(1);
            Upgrade upgrade = getUpgrade();
            return upgrade.test(baseStack, additionalStack);
        }

        return false;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        ItemStack baseStack = inv.getItem(0);
        ItemStack itemstack = baseStack.copy();
        return getUpgradingResult(itemstack);
    }

    private ItemStack getUpgradingResult(ItemStack input) {
        Upgrade upgrade = getUpgrade();
        UpgradeResult result = upgrade.apply(input, 0);
        return result.getItemStack();
    }

    private Upgrade getUpgrade() {
        if (upgrade != null) {
            return upgrade;
        }

        upgrade = UpgradeManager.get(upgradeId);
        return upgrade;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        if (sample != null) return sample;

        ItemStack output = super.getResultItem();
        sample = output.copy();

        if (sample.isEmpty()) {
            return sample;
        }

        ResourceLocation efficiencyRemover = ModItemTags.EFFICIENCY_REMOVER.getName();

        if (upgradeId.equals(efficiencyRemover)) {
            // Set hint of removing Efficiency to item-stack's display name
            TranslationTextComponent textComponent = new TranslationTextComponent(LaserBladeItemBase.KEY_TOOLTIP_REMOVE, new TranslationTextComponent("enchantment.minecraft.efficiency"));
            StringTextComponent info = new StringTextComponent(textComponent.getString());
            CompoundNBT nbt = sample.getOrCreateTagElement("display");
            nbt.putString("Name", ITextComponent.Serializer.toJson(info));
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
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.UPGRADE;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.SMITHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<UpgradeRecipe> {
        @Override
        public UpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "addition"));
            ResourceLocation upgradeId = new ResourceLocation(JSONUtils.getAsJsonObject(json, "result").get("type").getAsString());
            return new UpgradeRecipe(recipeId, base, addition, upgradeId);
        }

        @Nullable
        @Override
        public UpgradeRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            ResourceLocation upgradeId = buffer.readResourceLocation();
            return new UpgradeRecipe(recipeId, base, addition, upgradeId);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, UpgradeRecipe recipe) {
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeResourceLocation(recipe.upgradeId);
        }
    }
}
