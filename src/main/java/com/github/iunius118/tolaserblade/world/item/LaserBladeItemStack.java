package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
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
        ItemStack stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBladeVisual.Writer.of(stack).writeGripColor(LaserBladeColor.LIGHT_GRAY.getGripColor());
        return stack;
    }

    public static ItemStack getModelChangedStack(int type, boolean isFireproof) {
        ItemStack stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBladeVisual.Writer.of(stack).writeModelType(type);
        return stack;
    }

    private static ItemStack getLightElementStack(int lightElementLevel) {
        ItemStack laserBlade = new ItemStack(ModItems.LASER_BLADE);
        laserBlade.enchant(ModEnchantments.LIGHT_ELEMENT, lightElementLevel);
        laserBlade.enchant(Enchantments.BLOCK_EFFICIENCY, 1);
        return laserBlade;
    }

    private static ItemStack getGiftStack() {
        ItemStack stack = new ItemStack(ModItems.LASER_BLADE);

        LaserBlade.Writer.of(stack).writeDamage(LaserBlade.MOD_ATK_GIFT);

        stack.enchant(ModEnchantments.LIGHT_ELEMENT, 5);
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, 1);

        LaserBladeVisual.Writer.of(stack)
                .writeOuterColor(LaserBladeColor.LIME.getBladeColor())
                .writeGripColor(LaserBladeColor.BROWN.getGripColor());

        return stack;
    }

    private static ItemStack getUpgradedStack(boolean isFireproof, boolean isDamaged) {
        ItemStack stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);

        LaserBlade.Writer.of(stack)
                .writeDamage(LaserBlade.MOD_ATK_CRITICAL_BONUS)
                .writeSpeed(LaserBlade.MOD_SPD_MAX);

        stack.enchant(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, Enchantments.BLOCK_EFFICIENCY.getMaxLevel());
        stack.enchant(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());

        LaserBladeVisual.Writer.of(stack)
                .writeInnerColor(LaserBladeColor.LIGHT_BLUE.getBladeColor())
                .writeOuterColor(LaserBladeColor.BLUE.getBladeColor())
                .writeGripColor(LaserBladeColor.GRAY.getGripColor());

        if (isDamaged) {
            int maxUses = isFireproof ? ModItemTiers.LASER_BLADE_FP.getUses() : ModItemTiers.LASER_BLADE.getUses();
            stack.setDamageValue(maxUses - 1);
        }

        return stack;
    }

    private static ItemStack getFullModStack(boolean isDisassembled, boolean isFireproof) {
        ItemStack stack = new ItemStack(
                isDisassembled ? (isFireproof ? ModItems.LB_DISASSEMBLED_FP : ModItems.LB_DISASSEMBLED) :
                        (isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE)
        );

        LaserBlade.Writer.of(stack)
                .writeDamage(LaserBlade.MOD_ATK_CRITICAL_BONUS)
                .writeSpeed(LaserBlade.MOD_SPD_MAX);

        stack.enchant(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, Enchantments.BLOCK_EFFICIENCY.getMaxLevel());
        stack.enchant(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());
        stack.enchant(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
        stack.enchant(Enchantments.SWEEPING_EDGE, Enchantments.SWEEPING_EDGE.getMaxLevel());
        stack.enchant(Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
        stack.enchant(Enchantments.MOB_LOOTING, Enchantments.MOB_LOOTING.getMaxLevel());

        LaserBladeVisual.Writer.of(stack)
                .writeInnerColor(LaserBladeColor.WHITE.getBladeColor()).writeIsInnerSubColor(true)
                .writeOuterColor(LaserBladeColor.CYAN.getBladeColor()).writeIsOuterSubColor(true)
                .writeGripColor(LaserBladeColor.GRAY.getGripColor());

        return stack;
    }
}
