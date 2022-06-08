package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public record LaserBladePerformance(AttackPerformance attackPerformance, boolean isFireResistant) {
    public static LaserBladePerformance of(CompoundTag compoundTag, boolean isFireResistant) {
        var attackPerformance = new AttackPerformance(compoundTag);
        return new LaserBladePerformance(attackPerformance, isFireResistant);
    }

    public AttackPerformance getAttackPerformance() {
        return attackPerformance;
    }

    public void write(CompoundTag compound) {
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

        public static final float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;

        public static final String KEY_ATK = "ATK";
        public static final String KEY_SPD = "SPD";

        public AttackPerformance(CompoundTag compound) {
            damage = Mth.clamp(compound.getFloat(KEY_ATK), MOD_ATK_MIN, MOD_ATK_MAX);
            speed = Mth.clamp(compound.getFloat(KEY_SPD), MOD_SPD_MIN, MOD_SPD_MAX);
        }

        public void changeDamageSafely(float damage) {
            this.damage = Mth.clamp(damage, MOD_ATK_MIN, MOD_ATK_MAX);
        }

        public void changeSpeedSafely(float speed) {
            this.speed = Mth.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX);
        }

        public boolean canUpgradeSpeed() {
            return this.speed < MOD_SPD_MAX;
        }

        public void write(CompoundTag compound) {
            compound.putFloat(KEY_ATK, Mth.clamp(damage, MOD_ATK_MIN, MOD_ATK_MAX));
            compound.putFloat(KEY_SPD, Mth.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX));
        }
    }
}
