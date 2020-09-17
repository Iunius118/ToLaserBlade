package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

public class LaserBladePerformance {
    private final AttackPerformance attackPerformance;
    private final boolean isFireproof;

    public LaserBladePerformance(CompoundNBT compound, boolean isFireproofIn) {
        attackPerformance = new AttackPerformance(compound);
        isFireproof = isFireproofIn;
    }

    public AttackPerformance getAttackPerformance() {
        return attackPerformance;
    }

    public boolean isFireproof() {
        return isFireproof;
    }

    public void write(CompoundNBT compound) {
        attackPerformance.write(compound);
    }

    /* Inner classes */

    public static class AttackPerformance {
        public float damage;
        public float speed;

        public static final float MOD_ATK_MIN = 0.0F;
        public static final float MOD_ATK_GIFT = 3.0F;
        public static final float MOD_ATK_CRITICAL_BONUS = 8.0F;
        public static final float MOD_ATK_MAX = 2040.0F;
        public static final float MOD_SPD_MIN = 0.0F;
        public static final float MOD_SPD_MAX = 1.2F;

        public static final String KEY_ATK = "ATK";
        public static final String KEY_SPD = "SPD";

        public AttackPerformance(CompoundNBT compound) {
            damage = MathHelper.clamp(compound.getFloat(KEY_ATK), MOD_ATK_MIN, MOD_ATK_MAX);
            speed = MathHelper.clamp(compound.getFloat(KEY_SPD), MOD_SPD_MIN, MOD_SPD_MAX);
        }

        public void changeDamageSafely(float damage) {
            this.damage = MathHelper.clamp(damage, MOD_ATK_MIN, MOD_ATK_MAX);
        }

        public void changeSpeedSafely(float speed) {
            this.speed = MathHelper.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX);
        }

        public boolean canUpgradeSpeed() {
            return this.speed < MOD_SPD_MAX;
        }

        public void write(CompoundNBT compound) {
            compound.putFloat(KEY_ATK, MathHelper.clamp(damage, MOD_ATK_MIN, MOD_ATK_MAX));
            compound.putFloat(KEY_SPD, MathHelper.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX));
        }
    }
}
