package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Map;

public class EnchantmentUpgrade extends Upgrade {
    private final Enchantment enchantment;

    public EnchantmentUpgrade(Ingredient ingredientIn, Enchantment enchantmentIn) {
        super(ingredientIn);
        enchantment = enchantmentIn;
    }

    public static EnchantmentUpgrade of(Ingredient ingredientIn, Enchantment enchantmentIn) {
        return new EnchantmentUpgrade(ingredientIn, enchantmentIn);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getEnchantmentLevel(enchantment, base);
        return level < enchantment.getMaxLevel();
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getEnchantmentLevel(enchantment, base);

        if (level < enchantment.getMaxLevel()) {
            Map<Enchantment, Integer> oldEnchantments = EnchantmentHelper.getEnchantments(base);
            Map<Enchantment, Integer> newEnchantments = Maps.newLinkedHashMap();
            // Remove not compatible enchantments
            oldEnchantments.forEach((e, lvl) -> {if (e.isCompatibleWith(enchantment) || e.equals(enchantment)) newEnchantments.put(e, lvl);});
            newEnchantments.put(enchantment, ++level);
            EnchantmentHelper.setEnchantments(newEnchantments, base);
            cost += getCost(level);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(int newLevel) {
        Enchantment.Rarity rarity = enchantment.getRarity();
        int rate = (!(rarity == Enchantment.Rarity.RARE || rarity == Enchantment.Rarity.VERY_RARE)) ? 1
                : (rarity == Enchantment.Rarity.RARE) ? 2 : 4;  // Half rate (same as enchanted book)
        return Math.max(rate * newLevel, 1);
    }
}
