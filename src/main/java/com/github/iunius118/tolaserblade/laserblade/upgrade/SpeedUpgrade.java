package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class SpeedUpgrade implements Upgrade {
    @Override
    public Function<UpgradeResult, UpgradeResult> getFunction() {
        return upgradeResult -> {
            final ItemStack itemStack = upgradeResult.getItemStack();
            int cost = upgradeResult.getCost();
            final LaserBlade laserBlade = LaserBlade.of(itemStack);
            final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();

            float oldSpeed = attack.speed;
            attack.changeSpeedSafely(oldSpeed + 0.4F);

            if (oldSpeed < attack.speed) {
                cost += Math.max((int)(attack.speed / 0.4F), 1);
                laserBlade.write(itemStack);
            }

            return UpgradeResult.of(itemStack, cost);
        };
    }
}
