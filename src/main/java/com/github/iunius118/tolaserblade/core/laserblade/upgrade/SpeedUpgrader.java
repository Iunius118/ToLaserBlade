package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import net.minecraft.world.item.ItemStack;

public class SpeedUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        float speed = LaserBlade.getSpeed(base);
        return LaserBlade.canUpgradeSpeed(speed);
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        float speed = LaserBlade.getSpeed(base);
        int cost = baseCost;

        if (LaserBlade.canUpgradeSpeed(speed)) {
            speed += 0.4F;
            LaserBlade.setSpeed(base, speed);
            cost += getCost(speed);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(float newSpeed) {
        return Math.max((int) (newSpeed / 0.4F), 1);
    }
}
