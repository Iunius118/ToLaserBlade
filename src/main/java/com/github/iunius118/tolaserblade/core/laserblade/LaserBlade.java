package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.api.core.laserblade.LaserBladeState;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class LaserBlade {
    public static final float MOD_ATK_MIN = 0.0F;
    public static final float MOD_ATK_MAX = 2040.0F;
    public static final float MOD_ATK_GIFT = 3.0F;
    public static final float MOD_ATK_CRITICAL_BONUS = 8.0F;
    public static final float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;
    public static final float MOD_SPD_MIN = 0.0F;
    public static final float MOD_SPD_MAX = 1.2F;
    public static final int TYPE_DEFAULT = 0;

    private LaserBlade() { }

    public static float getAttack(ItemStack itemStack) {
        float attack = Objects.requireNonNullElseGet(itemStack.get(ModDataComponents.LASER_BLADE_ATTACK),
                () -> LaserBladeDataMigrator.getAttack(itemStack)); // Attempt to get and migrate data from CUSTOM_DATA
        return Mth.clamp(attack, MOD_ATK_MIN, MOD_ATK_MAX);
    }

    public static void setAttack(ItemStack itemStack, float damage) {
        itemStack.set(ModDataComponents.LASER_BLADE_ATTACK, damage);
        // Update item attribute modifiers
        itemStack.getItem().verifyComponentsAfterLoad(itemStack);
    }

    public static float getSpeed(ItemStack itemStack) {
        float speed = Objects.requireNonNullElseGet(itemStack.get(ModDataComponents.LASER_BLADE_SPEED),
                () -> LaserBladeDataMigrator.getSpeed(itemStack));  // Attempt to get and migrate data from CUSTOM_DATA
        return Mth.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX);
    }

    public static void setSpeed(ItemStack itemStack, float speed) {
        itemStack.set(ModDataComponents.LASER_BLADE_SPEED, speed);
        // Update item attribute modifiers
        itemStack.getItem().verifyComponentsAfterLoad(itemStack);
    }

    public static boolean canUpgradeAttack(float attack) {
        return attack < getMaxAttackUpgradeCount();
    }

    private static float getMaxAttackUpgradeCount() {
        return (float) ToLaserBladeConfig.SERVER.maxAttackDamageUpgradeCount.get();
    }

    public static boolean canUpgradeSpeed(float speed) {
        return speed < MOD_SPD_MAX;
    }

    public static LaserBladeState getState(ItemStack itemStack) {
        float attack = getAttack(itemStack);
        float speed = getSpeed(itemStack);
        var appearance = LaserBladeAppearance.of(itemStack);
        int type = appearance.getType();
        LaserBladeStateImpl.PartImpl outer = new LaserBladeStateImpl.PartImpl(true, appearance.getOuterColor(), appearance.isOuterSubColor());
        LaserBladeStateImpl.PartImpl inner = new LaserBladeStateImpl.PartImpl(true, appearance.getInnerColor(), appearance.isInnerSubColor());
        LaserBladeStateImpl.PartImpl grip = new LaserBladeStateImpl.PartImpl(true, appearance.getGripColor(), false);
        return new LaserBladeStateImpl(attack, speed, type, outer, inner, grip);
    }

    public record LaserBladeStateImpl(float attack, float speed, int modelType, Part outer, Part inner, Part grip) implements LaserBladeState {
        public record PartImpl(boolean exists, int color, boolean isSubtractiveColor) implements Part { }
    }
}
