package com.github.iunius118.tolaserblade.laserblade.upgrade;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.function.Function;

public class RemoveEfficiencyUpgrade implements Upgrade {
    @Override
    public Function<UpgradeResult, UpgradeResult> getFunction() {
        return upgradeResult -> {
            final ItemStack itemStack = upgradeResult.getItemStack();
            int cost = upgradeResult.getCost();
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);

            if (level > 0) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
                enchantments.remove(Enchantments.EFFICIENCY);
                EnchantmentHelper.setEnchantments(enchantments, itemStack);
                cost += 1;
            }

            return UpgradeResult.of(itemStack, cost);
        };
    }
}
