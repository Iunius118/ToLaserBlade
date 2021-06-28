package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Map;
import java.util.function.Supplier;

public class EnchantmentUpgrade extends Upgrade {
    private final Enchantment enchantment;

    public EnchantmentUpgrade(Supplier<Ingredient> ingredientSupplierIn, Enchantment enchantmentIn, String shortNameIn) {
        super(ingredientSupplierIn, shortNameIn);
        enchantment = enchantmentIn;
    }

    public static EnchantmentUpgrade of(Supplier<Ingredient> ingredientSupplierIn, Enchantment enchantmentIn, String shortNameIn) {
        return new EnchantmentUpgrade(ingredientSupplierIn, enchantmentIn, shortNameIn);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, base);
        return level < enchantment.getMaxLevel();
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, base);

        if (level < enchantment.getMaxLevel()) {
            Map<Enchantment, Integer> oldEnchantments = EnchantmentHelper.getEnchantments(base);
            Map<Enchantment, Integer> newEnchantments = Maps.newLinkedHashMap();
            // Remove not compatible enchantments
            oldEnchantments.forEach((e, lvl) -> {if (isCompatibleWith(e, enchantment)) newEnchantments.put(e, lvl);});
            newEnchantments.put(enchantment, ++level);
            EnchantmentHelper.setEnchantments(newEnchantments, base);
            cost += getCost(level);
        }

        return UpgradeResult.of(base, cost);
    }

    private boolean isCompatibleWith(Enchantment e1, Enchantment e2) {
        return e1.isCompatibleWith(e2) || e1.equals(e2) ||
                (e1 == Enchantments.SILK_TOUCH && e2 == Enchantments.MOB_LOOTING) || (e1 == Enchantments.MOB_LOOTING && e2 == Enchantments.SILK_TOUCH); // Allow Laser Blade to have Silk Touch and Looting together
    }

    private int getCost(int newLevel) {
        Enchantment.Rarity rarity = enchantment.getRarity();
        int rate = (!(rarity == Enchantment.Rarity.RARE || rarity == Enchantment.Rarity.VERY_RARE)) ? 1
                : (rarity == Enchantment.Rarity.RARE) ? 2 : 4;  // Half rate (same as enchanted book)
        return Math.max(rate * newLevel, 1);
    }
}
