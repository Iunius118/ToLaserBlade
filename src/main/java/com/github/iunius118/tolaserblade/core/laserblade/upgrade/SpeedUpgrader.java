package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import net.minecraft.world.item.ItemStack;

public class SpeedUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        return attack.canUpgradeSpeed();
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        int cost = baseCost;

        if (attack.canUpgradeSpeed()) {
            attack.changeSpeedSafely(attack.speed + 0.4F);
            laserBlade.write(base);
            cost += getCost(attack.speed);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(float newSpeed) {
        return Math.max((int) (newSpeed / 0.4F), 1);
    }
}
