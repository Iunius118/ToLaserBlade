package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public class DamageUpgrade extends Upgrade {
    public DamageUpgrade(Supplier<Ingredient> ingredientSupplierIn, String shortNameIn) {
        super(ingredientSupplierIn, shortNameIn);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        float maxUpgradeCount = (float)ToLaserBladeConfig.SERVER.maxAttackDamageUpgradeCount.get();
        return attack.damage < maxUpgradeCount;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        float maxUpgradeCount = (float)ToLaserBladeConfig.SERVER.maxAttackDamageUpgradeCount.get();

        if (attack.damage < maxUpgradeCount) {
            attack.changeDamageSafely(Math.min(attack.damage + 1.0F, maxUpgradeCount));
            laserBlade.write(base);
            cost += getCost(attack.damage);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(float newDamage) {
        return Math.max((int)newDamage, 1);
    }
}
