package com.github.iunius118.tolaserblade.item.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.Map;
import java.util.function.Function;

public class UpgradeFunctions {
    public static Function<ItemStack, UpgradeResult> getUpgradeEnchantmentFunction(Enchantment enchantment) {
        return (stack) -> {
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
            int cost = 0;

            if (level < enchantment.getMaxLevel()) {
                Map<Enchantment, Integer> oldEnchantments = EnchantmentHelper.getEnchantments(stack);
                Map<Enchantment, Integer> newEnchantments = Maps.newLinkedHashMap();
                // Remove not compatible enchantments
                oldEnchantments.forEach((e, lvl) -> {if (e.isCompatibleWith(enchantment) || e.equals(enchantment)) newEnchantments.put(e, lvl);});
                newEnchantments.put(enchantment, ++level);
                EnchantmentHelper.setEnchantments(newEnchantments, stack);
                int rate = 1;

                switch(enchantment.getRarity()) {
                    // Half rate (same as enchanted book)
                    case COMMON:
                    case UNCOMMON:
                        break;
                    case RARE:
                        rate = 2;
                        break;
                    case VERY_RARE:
                        rate = 4;
                }

                cost = Math.max(level * rate, 1);
            }

            return UpgradeResult.of(stack, cost);
        };
    }

    public static Function<ItemStack, UpgradeResult> getRemoveEfficiencyFunction() {
        return (stack) -> {
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            int cost = 0;

            if (level > 0) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                enchantments.remove(Enchantments.EFFICIENCY);
                EnchantmentHelper.setEnchantments(enchantments, stack);

                cost = 1;
            }

            return UpgradeResult.of(stack, cost);
        };
    }

    public static Function<ItemStack, UpgradeResult> getUpgradeDamageFunction() {
        return (stack) -> {
            float attack = ModItems.LASER_BLADE.getLaserBladeATK(stack);
            float maxUpgradeCount = (float)ToLaserBladeConfig.COMMON.maxAttackDamageUpgradeCountInServer.get();
            int cost = 0;

            if (attack >= LaserBladeItemBase.MOD_ATK_MIN && attack < maxUpgradeCount) {
                float attack2 = MathHelper.clamp(attack + 1.0F, LaserBladeItemBase.MOD_ATK_MIN, maxUpgradeCount);
                ModItems.LASER_BLADE.setLaserBladeATK(stack, attack2);
                cost = Math.max((int)attack2, 1);
            }

            return UpgradeResult.of(stack, cost);
        };
    }

    public static Function<ItemStack, UpgradeResult> getUpgradeSpeedFunction() {
        return (stack) -> {
            float speed = ModItems.LASER_BLADE.getLaserBladeSPD(stack);
            int cost = 0;

            if (speed >= LaserBladeItemBase.MOD_SPD_MIN && speed < LaserBladeItemBase.MOD_SPD_MAX) {
                float speed2 = MathHelper.clamp(speed + 0.4F, LaserBladeItemBase.MOD_SPD_MIN, LaserBladeItemBase.MOD_SPD_MAX);
                ModItems.LASER_BLADE.setLaserBladeSPD(stack, speed2);
                cost = Math.max((int)(speed2 / 0.4F), 1);
            }

            return UpgradeResult.of(stack, cost);
        };
    }

    public static Function<ItemStack, UpgradeResult> getUpgradeFireproofFunction() {
        return (stack) -> {
            ItemStack itemFireproof = null;
            ItemStack output = stack;
            int cost = 0;

            if (stack.getItem() == ModItems.LASER_BLADE) {
                // Make Laser Blade fireproof with Netherite
                itemFireproof = new ItemStack(ModItems.LASER_BLADE_FP);

            } else if (stack.getItem() == ModItems.LB_CASING) {
                // Make Laser Blade Casing fireproof with Netherite
                itemFireproof = new ItemStack(ModItems.LB_CASING_FP);
            }

            if (itemFireproof != null) {
                itemFireproof.setTag(stack.getOrCreateTag().copy());
                itemFireproof.setDamage(0);
                output = itemFireproof;
                cost = 1;
            }

            return UpgradeResult.of(output, cost);
        };
    }

    public static Function<ItemStack, UpgradeResult> getRepairFunction() {
        return (stack) -> {
            ItemStack itemRepaired = null;
            ItemStack output = stack;
            int cost = 0;

            if (stack.getItem() == ModItems.LB_BROKEN) {
                // Repair Broken Laser Blade
                itemRepaired = new ItemStack(ModItems.LASER_BLADE);

            } else if (stack.getItem() == ModItems.LB_BROKEN_FP) {
                // Repair Broken Netherite Laser Blade
                itemRepaired = new ItemStack(ModItems.LASER_BLADE_FP);

            } else if (stack.getDamage() > 0) {
                // Repair damaged Laser Blade (excluding broken one)
                output.setDamage(0);
                cost = 1;
            }

            if (itemRepaired != null) {
                itemRepaired.setTag(stack.getOrCreateTag().copy());
                itemRepaired.setDamage(0);
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemRepaired);

                if (enchantments.containsKey(Enchantments.SHARPNESS)) {
                    // SHARPNESS -> ATK (for [-1.14.4] Laser Blade Core)
                    float atkFromSharpness = Math.max(enchantments.get(Enchantments.SHARPNESS) - 1, 0);
                    float atk = ModItems.LASER_BLADE.getLaserBladeATK(itemRepaired);
                    ModItems.LASER_BLADE.setLaserBladeATK(itemRepaired, Math.max(atkFromSharpness, atk));
                    enchantments.remove(Enchantments.SHARPNESS);
                    EnchantmentHelper.setEnchantments(enchantments, itemRepaired);
                }

                output = itemRepaired;
                cost = 1;
            }

            return UpgradeResult.of(output, cost);
        };
    }
}
