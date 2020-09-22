package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Map;

public class RemoveEfficiencyUpgrade extends Upgrade {
    public RemoveEfficiencyUpgrade(Ingredient ingredientIn, String shortNameIn) {
        super(ingredientIn, shortNameIn);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, base);
        return level > 0;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, base);

        if (level > 0) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(base);
            enchantments.remove(Enchantments.EFFICIENCY);
            EnchantmentHelper.setEnchantments(enchantments, base);
            cost += 1;
        }

        return UpgradeResult.of(base, cost);
    }
}
