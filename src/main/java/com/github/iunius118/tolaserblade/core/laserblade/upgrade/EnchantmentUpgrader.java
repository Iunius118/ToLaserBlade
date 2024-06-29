package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class EnchantmentUpgrader implements Upgrader {
    private final ResourceKey<Enchantment> enchantment;

    public EnchantmentUpgrader(ResourceKey<Enchantment> enchantment) {
        this.enchantment = enchantment;
    }

    public static EnchantmentUpgrader of(ResourceKey<Enchantment> enchantment) {
        return new EnchantmentUpgrader(enchantment);
    }

    @Override
    public boolean canApply(ItemStack base, ItemStack addition, HolderLookup.Provider provider) {
        var optionalHolder = provider.lookupOrThrow(Registries.ENCHANTMENT).get(enchantment);

        if (optionalHolder.isEmpty()) {
            return false;
        }

        Holder.Reference<Enchantment> enchantmentHolder = optionalHolder.get();
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, base);
        return level < enchantmentHolder.value().getMaxLevel();
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost, HolderLookup.Provider provider) {
        var optionalHolder = provider.lookupOrThrow(Registries.ENCHANTMENT).get(enchantment);

        if (optionalHolder.isEmpty()) {
            return UpgradeResult.of(base, baseCost);
        }

        Holder.Reference<Enchantment> enchantmentHolder = optionalHolder.get();
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, base);

        if (level < enchantmentHolder.value().getMaxLevel()) {
            EnchantmentHelper.updateEnchantments(base, m -> {
                // Remove not compatible enchantments
                m.removeIf(e -> !isCompatibleWith(e, enchantmentHolder));
                // Add enchantment or increase enchantment's level
                m.set(enchantmentHolder, level + 1);
            });
            cost += getCost(enchantmentHolder, level);
        }

        return UpgradeResult.of(base, cost);
    }

    private boolean isCompatibleWith(Holder<Enchantment> e1, Holder<Enchantment> e2) {
        // Allow Laser Blade to have Silk Touch and Looting together
        return Enchantment.areCompatible(e1, e2)
                || (e1.is(Enchantments.SILK_TOUCH) && e2.is(Enchantments.LOOTING))
                || (e1.is(Enchantments.LOOTING) && e2.is(Enchantments.SILK_TOUCH));
    }

    private int getCost(Holder<Enchantment> enchantmentHolder, int newLevel) {
        int rate = Math.max(enchantmentHolder.value().getAnvilCost() / 2, 1);
        return Math.max(rate * newLevel, 1);
    }
}
