package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class DamageUpgrade implements Upgrade {
    @Override
    public Function<UpgradeResult, UpgradeResult> getFunction() {
        return upgradeResult -> {
            final ItemStack itemStack = upgradeResult.getItemStack();
            int cost = upgradeResult.getCost();
            final LaserBlade laserBlade = LaserBlade.of(itemStack);
            final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
            float maxUpgradeCount = (float)ToLaserBladeConfig.COMMON.maxAttackDamageUpgradeCountInServer.get();

            if (attack.damage < maxUpgradeCount) {
                attack.changeDamageSafely(Math.min(attack.damage + 1.0F, maxUpgradeCount));
                cost += Math.max((int)attack.damage, 1);
                laserBlade.write(itemStack);
            }

            return UpgradeResult.of(itemStack, cost);
        };
    }
}
