package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import net.minecraft.world.item.ItemStack;

public class AttackUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        float attack = LaserBlade.getAttack(base);
        return LaserBlade.canUpgradeAttack(attack);
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        float attack = LaserBlade.getAttack(base);
        int cost = baseCost;

        if (LaserBlade.canUpgradeAttack(attack)) {
            attack += 1.0F;
            LaserBlade.setAttack(base, attack);
            cost += getCost(attack);
        }

        return UpgradeResult.of(base, cost);
    }

    private int getCost(float newDamage) {
        return Math.max((int)newDamage, 1);
    }
}
