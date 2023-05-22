package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import net.minecraft.world.item.ItemStack;

public class DamageUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        float damage = LaserBlade.of(base).getDamage();
        return LaserBlade.canUpgradeDamage(damage);
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        final float damage = LaserBlade.of(base).getDamage();
        int cost = baseCost;

        if (LaserBlade.canUpgradeDamage(damage)) {
            float newDamage = damage + 1.0F;
            LaserBlade.Writer.of(base).writeDamage(newDamage);
            cost += getCost(newDamage);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(float newDamage) {
        return Math.max((int)newDamage, 1);
    }
}
