package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public record LaserBlade(LaserBladePerformance performance, LaserBladeVisual visual) {
    public static LaserBlade of(ItemStack stack) {
        var item = stack.getItem();
        var compoundTag = stack.getOrCreateTag();
        var performance = LaserBladePerformance.of(compoundTag, item.isFireResistant());
        var visual = LaserBladeVisual.of(compoundTag);
        return new LaserBlade(performance, visual);
    }

    public static LaserBladePerformance performanceOf(ItemStack stack) {
        var item = stack.getItem();
        var compoundTag = stack.getOrCreateTag();
        return LaserBladePerformance.of(compoundTag, item.isFireResistant());
    }


    public static LaserBladeVisual visualOf(ItemStack stack) {
        var compoundTag = stack.getOrCreateTag();
        return LaserBladeVisual.of(compoundTag);
    }

    public LaserBladePerformance.AttackPerformance getAttackPerformance() {
        return performance.getAttackPerformance();
    }

    public boolean isFireResistant() {
        return performance.isFireResistant();
    }

    public LaserBladeVisual getVisual() {
        return visual;
    }

    public void write(ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag();
        performance.write(compound);
        visual.write(compound);
    }
}
