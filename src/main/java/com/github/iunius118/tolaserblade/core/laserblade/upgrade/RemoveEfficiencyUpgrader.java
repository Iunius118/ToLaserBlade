package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class RemoveEfficiencyUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider) {
        var efficiency = provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY);
        int level = base.getEnchantmentLevel(efficiency);
        return level > 0;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider) {
        var efficiency = provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY);
        int cost = baseCost;
        int level = base.getEnchantmentLevel(efficiency);

        if (level > 0) {
            EnchantmentHelper.updateEnchantments(base,
                    m -> m.removeIf(e -> e.value().equals(Enchantments.EFFICIENCY)));
            cost += 1;
        }

        return UpgradeResult.of(base, cost);
    }
}
