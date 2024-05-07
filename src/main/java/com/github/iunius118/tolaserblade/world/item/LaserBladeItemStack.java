package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.Supplier;

public enum LaserBladeItemStack {
    ORIGINAL(() -> new ItemStack(ModItems.LASER_BLADE)),
    ICON(LaserBladeItemStack::getIconStack),
    MODEL_TYPE_526(() -> getModelChangedStack(526, false)),
    LIGHT_ELEMENT_1(() -> getLightElementStack(1)),
    LIGHT_ELEMENT_2(() -> getLightElementStack(2)),
    GIFT(LaserBladeItemStack::getGiftStack),
    UPGRADED(() -> getUpgradedStack(false, false)),
    DAMAGED(() -> getUpgradedStack(false, true)),
    FULL_MOD(() -> getFullModStack(false, false)),
    FP(() -> new ItemStack(ModItems.LASER_BLADE_FP)),
    UPGRADED_FP(() -> getUpgradedStack(true, false)),
    DAMAGED_FP(() -> getUpgradedStack(true, true)),
    FULL_MOD_FP(() -> getFullModStack(false, true)),
    DISASSEMBLED_FULL_MOD(() -> getFullModStack(true, false)),
    DISASSEMBLED_FULL_MOD_FP(() -> getFullModStack(true, true));

    private final Supplier<ItemStack> supplier;

    LaserBladeItemStack(Supplier<ItemStack> supplier) {
        this.supplier = supplier;
    }

    public ItemStack getCopy() {
        return supplier.get();
    }

    private static ItemStack getIconStack() {
        var stack = new ItemStack(ModItems.LASER_BLADE);
        new LaserBladeAppearance().setGripColor(LaserBladeColor.LIGHT_GRAY.getGripColor()).writeTo(stack);
        return stack;
    }

    public static ItemStack getModelChangedStack(int type, boolean isFireproof) {
        var stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBladeAppearance.of().setType(type).writeTo(stack);
        return stack;
    }

    private static ItemStack getLightElementStack(int lightElementLevel) {
        var stack = new ItemStack(ModItems.LASER_BLADE);
        stack.enchant(ModEnchantments.LIGHT_ELEMENT, lightElementLevel);
        stack.enchant(Enchantments.EFFICIENCY, 1);
        return stack;
    }

    private static ItemStack getGiftStack() {
        var stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBlade.setAttack(stack, LaserBlade.MOD_ATK_GIFT);
        LaserBladeAppearance.of()
                .setOuterColor(LaserBladeColor.LIME.getBladeColor())
                .setGripColor(LaserBladeColor.BROWN.getGripColor())
                .writeTo(stack);
        stack.enchant(ModEnchantments.LIGHT_ELEMENT, 5);
        stack.enchant(Enchantments.EFFICIENCY, 1);
        return stack;
    }

    private static ItemStack getUpgradedStack(boolean isFireproof, boolean isDamaged) {
        var stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBlade.setAttack(stack, LaserBlade.MOD_ATK_CRITICAL_BONUS);
        LaserBlade.setSpeed(stack, LaserBlade.MOD_SPD_MAX);
        LaserBladeAppearance.of()
                .setOuterColor(LaserBladeColor.BLUE.getBladeColor())
                .setInnerColor(LaserBladeColor.LIGHT_BLUE.getBladeColor())
                .setGripColor(LaserBladeColor.GRAY.getGripColor())
                .writeTo(stack);
        stack.enchant(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.enchant(Enchantments.EFFICIENCY, Enchantments.EFFICIENCY.getMaxLevel());
        stack.enchant(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());

        if (isDamaged) {
            int maxUses = isFireproof ? ModItemTiers.LASER_BLADE_FP.getUses() : ModItemTiers.LASER_BLADE.getUses();
            stack.setDamageValue(maxUses - 1);
        }

        return stack;
    }

    private static ItemStack getFullModStack(boolean isDisassembled, boolean isFireproof) {
        var stack = new ItemStack(
                isDisassembled ? (isFireproof ? ModItems.LB_DISASSEMBLED_FP : ModItems.LB_DISASSEMBLED) :
                        (isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE)
        );
        LaserBlade.setAttack(stack, LaserBlade.MOD_ATK_CRITICAL_BONUS);
        LaserBlade.setSpeed(stack, LaserBlade.MOD_SPD_MAX);
        LaserBladeAppearance.of()
                .setOuterColor(LaserBladeColor.CYAN.getBladeColor()).setOuterSubColor(true)
                .setInnerColor(LaserBladeColor.WHITE.getBladeColor()).setInnerSubColor(true)
                .setGripColor(LaserBladeColor.GRAY.getGripColor())
                .writeTo(stack);
        stack.enchant(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.enchant(Enchantments.EFFICIENCY, Enchantments.EFFICIENCY.getMaxLevel());
        stack.enchant(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());
        stack.enchant(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
        stack.enchant(Enchantments.SWEEPING_EDGE, Enchantments.SWEEPING_EDGE.getMaxLevel());
        stack.enchant(Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
        stack.enchant(Enchantments.LOOTING, Enchantments.LOOTING.getMaxLevel());
        return stack;
    }
}
