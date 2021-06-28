package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class LaserBlade {
    private final LaserBladePerformance performance;
    private final LaserBladeVisual visual;

    public LaserBlade(ItemStack stack) {
        Item item = stack.getItem();
        CompoundNBT compound = stack.getOrCreateTag();
        performance = new LaserBladePerformance(compound, item.isFireResistant());
        visual = new LaserBladeVisual(compound);
    }

    public static LaserBlade of(ItemStack stack) {
        return new LaserBlade(stack);
    }

    public static LaserBladePerformance performanceOf(ItemStack stack) {
        Item item = stack.getItem();
        CompoundNBT compound = stack.getOrCreateTag();
        return new LaserBladePerformance(compound, item.isFireResistant());
    }

    public static LaserBladeVisual visualOf(ItemStack stack) {
        Item item = stack.getItem();
        CompoundNBT compound = stack.getOrCreateTag();
        return new LaserBladeVisual(compound);
    }

    public LaserBladePerformance.AttackPerformance getAttackPerformance() {
        return performance.getAttackPerformance();
    }

    public boolean isFireproof() {
        return performance.isFireproof();
    }

    public LaserBladeVisual getVisual() {
        return visual;
    }

    public void write(ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();
        performance.write(compound);
        visual.write(compound);
    }
}
