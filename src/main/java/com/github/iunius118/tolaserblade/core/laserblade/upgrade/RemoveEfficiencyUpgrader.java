package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class RemoveEfficiencyUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.EFFICIENCY, base);
        return level > 0;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.EFFICIENCY, base);

        if (level > 0) {
            EnchantmentHelper.updateEnchantments(base, m -> {
                m.removeIf(e -> e.value().equals(Enchantments.EFFICIENCY));
            });
            cost += 1;
        }

        return UpgradeResult.of(base, cost);
    }
}
