package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.laserblade.Color;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public enum LaserBladeItemStack {
    ORIGINAL(() -> new ItemStack(ModItems.LASER_BLADE)),
    ICON(LaserBladeItemStack::getIconStack),
    MODEL_TYPE_526(LaserBladeItemStack::getModelType526Stack),
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
        LaserBladeVisual visual = LaserBlade.visualOf(stack);
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        gripColor.color = Color.LIGHT_GRAY.getGripColor();
        visual.write(stack.getOrCreateTag());
        return stack;
    }

    private static ItemStack getModelType526Stack() {
        ItemStack stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBladeVisual visual = LaserBlade.visualOf(stack);
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        gripColor.color = Color.GRAY.getGripColor();
        visual.setModelType(526);
        visual.write(stack.getOrCreateTag());
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
        LaserBlade laserBlade = LaserBlade.of(stack);

        LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        attack.damage = LaserBladePerformance.AttackPerformance.MOD_ATK_GIFT;

        stack.enchant(ModEnchantments.LIGHT_ELEMENT, 5);
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, 1);

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

        stack.enchant(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, Enchantments.BLOCK_EFFICIENCY.getMaxLevel());
        stack.enchant(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());

        LaserBladeVisual visual = laserBlade.getVisual();
        LaserBladeVisual.PartColor innerColor = visual.getInnerColor();
        LaserBladeVisual.PartColor outerColor = visual.getOuterColor();
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        innerColor.color = Color.LIGHT_BLUE.getBladeColor();
        outerColor.color = Color.BLUE.getBladeColor();
        gripColor.color = Color.GRAY.getGripColor();

        laserBlade.write(stack);
        if (isDamaged) stack.setDamageValue(LaserBladeItemBase.MAX_USES - 1);
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

        stack.enchant(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, Enchantments.BLOCK_EFFICIENCY.getMaxLevel());
        stack.enchant(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());
        stack.enchant(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
        stack.enchant(Enchantments.SWEEPING_EDGE, Enchantments.SWEEPING_EDGE.getMaxLevel());
        stack.enchant(Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
        stack.enchant(Enchantments.MOB_LOOTING, Enchantments.MOB_LOOTING.getMaxLevel());

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
