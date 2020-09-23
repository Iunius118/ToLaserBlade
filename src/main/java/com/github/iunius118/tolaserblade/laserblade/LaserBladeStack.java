package com.github.iunius118.tolaserblade.laserblade;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public enum LaserBladeStack {
    ORIGINAL(() -> new ItemStack(ModItems.LASER_BLADE)),
    ICON(LaserBladeStack::getIconStack),
    LIGHT_ELEMENT_1(() -> getLightElementStack(1)),
    LIGHT_ELEMENT_2(() -> getLightElementStack(2)),
    GIFT(LaserBladeStack::getGiftStack),
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

    LaserBladeStack(Supplier<ItemStack> supplier) {
        this.supplier = supplier;
    }

    public ItemStack getCopy() {
        return supplier.get();
    }

    private static ItemStack getIconStack() {
        ItemStack stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBladeVisual visual = LaserBlade.visualOf(stack);
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        gripColor.color = Color.LIGHT_GRAY.getGripColor();
        visual.write(stack.getOrCreateTag());
        return stack;
    }

    private static ItemStack getLightElementStack(int lightElementLevel) {
        ItemStack laserBlade = new ItemStack(ModItems.LASER_BLADE);
        laserBlade.addEnchantment(ModEnchantments.LIGHT_ELEMENT, lightElementLevel);
        laserBlade.addEnchantment(Enchantments.EFFICIENCY, 1);
        return laserBlade;
    }

    private static ItemStack getGiftStack() {
        ItemStack stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBlade laserBlade = LaserBlade.of(stack);

        LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        attack.damage = LaserBladePerformance.AttackPerformance.MOD_ATK_GIFT;

        stack.addEnchantment(ModEnchantments.LIGHT_ELEMENT, 5);
        stack.addEnchantment(Enchantments.EFFICIENCY, 1);

        LaserBladeVisual visual = laserBlade.getVisual();
        LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        outerColor.color = Color.LIME.getBladeColor();
        gripColor.color = Color.BROWN.getGripColor();

        laserBlade.write(stack);
        return stack;
    }

    private static ItemStack getUpgradedStack(boolean isFireproof, boolean isDamaged) {
        ItemStack stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBlade laserBlade = LaserBlade.of(stack);

        LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        attack.damage = LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS;
        attack.speed = LaserBladePerformance.AttackPerformance.MOD_SPD_MAX;

        stack.addEnchantment(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.addEnchantment(Enchantments.EFFICIENCY, Enchantments.EFFICIENCY.getMaxLevel());
        stack.addEnchantment(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());

        LaserBladeVisual visual = laserBlade.getVisual();
        LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
        LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        innerColor.color = Color.LIGHT_BLUE.getBladeColor();
        outerColor.color = Color.BLUE.getBladeColor();
        gripColor.color = Color.GRAY.getGripColor();

        laserBlade.write(stack);
        if (isDamaged) stack.setDamage(LaserBladeItemBase.MAX_USES - 1);
        return stack;
    }

    private static ItemStack getFullModStack(boolean isDisassembled, boolean isFireproof) {
        ItemStack stack = new ItemStack(
                isDisassembled ? (isFireproof ? ModItems.LB_DISASSEMBLED_FP : ModItems.LB_DISASSEMBLED) :
                        (isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE)
        );
        LaserBlade laserBlade = LaserBlade.of(stack);

        LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        attack.damage = LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS;
        attack.speed = LaserBladePerformance.AttackPerformance.MOD_SPD_MAX;

        stack.addEnchantment(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.addEnchantment(Enchantments.EFFICIENCY, Enchantments.EFFICIENCY.getMaxLevel());
        stack.addEnchantment(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());
        stack.addEnchantment(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
        stack.addEnchantment(Enchantments.SWEEPING, Enchantments.SWEEPING.getMaxLevel());
        stack.addEnchantment(Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
        stack.addEnchantment(Enchantments.LOOTING, Enchantments.LOOTING.getMaxLevel());

        LaserBladeVisual visual = laserBlade.getVisual();
        LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
        LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        innerColor.color = Color.WHITE.getBladeColor();
        innerColor.isSubtractColor = true;
        outerColor.color = Color.CYAN.getBladeColor();
        outerColor.isSubtractColor = true;
        gripColor.color = Color.GRAY.getGripColor();

        laserBlade.write(stack);
        return stack;
    }
}
