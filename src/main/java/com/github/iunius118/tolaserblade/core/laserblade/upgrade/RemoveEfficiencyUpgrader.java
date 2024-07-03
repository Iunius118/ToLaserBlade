package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class RemoveEfficiencyUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider) {
        var optionalHolder = provider.lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.EFFICIENCY);

        if (optionalHolder.isEmpty()) {
            return false;
        }

        Holder.Reference<Enchantment> efficiency = optionalHolder.get();
        int level = EnchantmentHelper.getItemEnchantmentLevel(efficiency, base);
        return level > 0;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider) {
        var optionalHolder = provider.lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.EFFICIENCY);

        if (optionalHolder.isEmpty()) {
            return UpgradeResult.of(base, baseCost);
        }

        Holder.Reference<Enchantment> efficiency = optionalHolder.get();
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(efficiency, base);

        if (level > 0) {
            EnchantmentHelper.updateEnchantments(base, m -> m.removeIf(e -> e.is(Enchantments.EFFICIENCY)));
            cost += 1;
        }

        return UpgradeResult.of(base, cost);
    }
}
