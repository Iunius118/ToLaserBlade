package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.Map;
import java.util.function.ToIntFunction;

public class UpgradeFunctions {
    // ToIntFunction returns upgrading level cost
    public static ToIntFunction<ItemStack> getUpdateEnchantmentFunction(Enchantment enchantment) {
        return (stack) -> {
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
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

                return Math.max(level * rate, 1);
            }

            return 0;
        };
    }

    public static ToIntFunction<ItemStack> getRemoveEfficiencyFunction() {
        return (stack) -> {
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);

            if (level > 0) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                enchantments.remove(Enchantments.EFFICIENCY);
                EnchantmentHelper.setEnchantments(enchantments, stack);

                return 1;
            }

            return 0;
        };
    }

    public static ToIntFunction<ItemStack> getUpdateDamageFunction() {
        return (stack) -> {
            float attack = ModItems.LASER_BLADE.getLaserBladeATK(stack);
            float maxUpgradeCount = (float)ToLaserBladeConfig.COMMON.maxAttackDamageUpgradeCountInServer.get();

            if (attack >= LaserBladeItemBase.MOD_ATK_MIN && attack < maxUpgradeCount) {
                float attack2 = MathHelper.clamp(attack + 1.0F, LaserBladeItemBase.MOD_ATK_MIN, maxUpgradeCount);
                ModItems.LASER_BLADE.setLaserBladeATK(stack, attack2);
                return Math.max((int)attack2, 1);
            }

            return 0;
        };
    }

    public static ToIntFunction<ItemStack> getUpdateSpeedFunction() {
        return (stack) -> {
            float speed = ModItems.LASER_BLADE.getLaserBladeSPD(stack);

            if (speed >= LaserBladeItemBase.MOD_SPD_MIN && speed < LaserBladeItemBase.MOD_SPD_MAX) {
                float speed2 = MathHelper.clamp(speed + 0.4F, LaserBladeItemBase.MOD_SPD_MIN, LaserBladeItemBase.MOD_SPD_MAX);
                ModItems.LASER_BLADE.setLaserBladeSPD(stack, speed2);
                return Math.max((int)(speed2 / 0.4F), 1);
            }

            return 0;
        };
    }

    public static ToIntFunction<ItemStack> getRepairFunction() {
        return (stack) -> {
            if (stack.getDamage() > 0 || stack.getItem() == ModItems.LB_BROKEN) {
                stack.setDamage(0);
                return 1;
            }

            return 0;
        };
    }
}
