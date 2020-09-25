package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public class SpeedUpgrade extends Upgrade {
    public SpeedUpgrade(Supplier<Ingredient> ingredientSupplierIn, String shortNameIn) {
        super(ingredientSupplierIn, shortNameIn);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        return attack.canUpgradeSpeed();
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();

        if (attack.canUpgradeSpeed()) {
            attack.changeSpeedSafely(attack.speed + 0.4F);
            laserBlade.write(base);
            cost += getCost(attack.speed);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(float newSpeed) {
        return Math.max((int)(newSpeed / 0.4F), 1);
    }
}
