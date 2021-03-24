package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Map;
import java.util.function.Supplier;

public class RemoveEfficiencyUpgrade extends Upgrade {
    public RemoveEfficiencyUpgrade(Supplier<Ingredient> ingredientSupplierIn, String shortNameIn) {
        super(ingredientSupplierIn, shortNameIn);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, base);
        return level > 0;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, base);

        if (level > 0) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(base);
            enchantments.remove(Enchantments.BLOCK_EFFICIENCY);
            EnchantmentHelper.setEnchantments(enchantments, base);
            cost += 1;
        }

        return UpgradeResult.of(base, cost);
    }
}
