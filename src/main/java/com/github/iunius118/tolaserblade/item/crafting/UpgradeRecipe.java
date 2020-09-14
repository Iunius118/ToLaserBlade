package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeResult;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UpgradeRecipe extends SmithingRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private final ResourceLocation recipeId;
    private Upgrade upgrade;

    public UpgradeRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        super(recipeId, base, addition, ItemStack.EMPTY);
        this.recipeId = recipeId;
        this.base = base;
        this.addition = addition;
        this.upgradeId = upgradeId;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (base.test(inv.getStackInSlot(0)) && addition.test(inv.getStackInSlot(1))) {
            ItemStack baseStack = inv.getStackInSlot(0);
            ItemStack additionalStack = inv.getStackInSlot(1);
            Upgrade upgrade = getUpgrade();
            return upgrade.test(baseStack, additionalStack);
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack baseStack = inv.getStackInSlot(0);
        ItemStack itemstack = baseStack.copy();
        Upgrade upgrade = getUpgrade();
        UpgradeResult result = upgrade.apply(itemstack, 0);
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
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.SMITHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<UpgradeRecipe> {
        @Override
        public UpgradeRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
            Ingredient addition = Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition"));
            ResourceLocation upgradeId = new ResourceLocation(JSONUtils.getJsonObject(json, "result").get("type").getAsString());
            return new UpgradeRecipe(recipeId, base, addition, upgradeId);
        }

        @Nullable
        @Override
        public UpgradeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient base = Ingredient.read(buffer);
            Ingredient addition = Ingredient.read(buffer);
            ResourceLocation upgradeId = buffer.readResourceLocation();
            return new UpgradeRecipe(recipeId, base, addition, upgradeId);
        }

        @Override
        public void write(PacketBuffer buffer, UpgradeRecipe recipe) {
            recipe.base.write(buffer);
            recipe.addition.write(buffer);
            buffer.writeResourceLocation(recipe.upgradeId);
        }
    }
}
