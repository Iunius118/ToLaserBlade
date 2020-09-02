package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.function.Function;

public class EnchantmentUpgrade implements Upgrade {
    private final Function<UpgradeResult, UpgradeResult> function;

    public EnchantmentUpgrade(Enchantment enchantmentIn) {
        function = createFunction(enchantmentIn);
    }

    public static EnchantmentUpgrade of(Enchantment enchantmentIn) {
        return new EnchantmentUpgrade(enchantmentIn);
    }

    @Override
    public Function<UpgradeResult, UpgradeResult> getFunction() {
        return function;
    }

    private Function<UpgradeResult, UpgradeResult> createFunction(final Enchantment enchantment) {
        return upgradeResult -> {
            final ItemStack itemStack = upgradeResult.getItemStack();
            int cost = upgradeResult.getCost();
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack);

            if (level < enchantment.getMaxLevel()) {
                Map<Enchantment, Integer> oldEnchantments = EnchantmentHelper.getEnchantments(itemStack);
                Map<Enchantment, Integer> newEnchantments = Maps.newLinkedHashMap();
                // Remove not compatible enchantments
                oldEnchantments.forEach((e, lvl) -> {if (e.isCompatibleWith(enchantment) || e.equals(enchantment)) newEnchantments.put(e, lvl);});
                newEnchantments.put(enchantment, ++level);
                EnchantmentHelper.setEnchantments(newEnchantments, itemStack);
                Enchantment.Rarity rarity = enchantment.getRarity();
                int rate = (!(rarity == Enchantment.Rarity.RARE || rarity == Enchantment.Rarity.VERY_RARE)) ? 1
                        : (rarity == Enchantment.Rarity.RARE) ? 2 : 4;  // Half rate (same as enchanted book)
                cost += Math.max(level * rate, 1);
            }

            return UpgradeResult.of(itemStack, cost);
        };
    }
}
