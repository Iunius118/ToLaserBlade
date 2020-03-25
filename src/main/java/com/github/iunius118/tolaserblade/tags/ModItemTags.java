package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.LaserBladeUpgrade.Type;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public class ModItemTags {
    private static List<Triple<Tag<Item>, Type, ToIntFunction<ItemStack>>> tags = new ArrayList<>();    // ToIntFunction returns upgrading cost

    public static final Tag<Item> ATTACK_SPEED_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/speed"), Type.BATTERY, getUpdateSpeedFunction());
    public static final Tag<Item> EFFICIENCY_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/efficiency"), Type.BATTERY, getUpdateEnchantmentFunction(Enchantments.EFFICIENCY));
    public static final Tag<Item> EFFICIENCY_REMOVER = addUpgradeTag(makeWrapperTag("upgrade/efficiency_remover"), Type.BATTERY, getRemoveEfficiencyFunction());

    public static final Tag<Item> ATTACK_DAMAGE_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/damage"), Type.MEDIUM, getUpdateDamageFunction());
    public static final Tag<Item> LIGHT_ELEMENT_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/light_element"), Type.MEDIUM, getUpdateEnchantmentFunction(ModEnchantments.LIGHT_ELEMENT));

    public static final Tag<Item> FIRE_ASPECT_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/fire_aspect"), Type.EMITTER, getUpdateEnchantmentFunction(Enchantments.FIRE_ASPECT));
    public static final Tag<Item> SWEEPING_EDGE_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/sweeping_edge"), Type.EMITTER, getUpdateEnchantmentFunction(Enchantments.SWEEPING));

    public static final Tag<Item> LOOTING_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/looting"), Type.CASING, getUpdateEnchantmentFunction(Enchantments.LOOTING));
    public static final Tag<Item> MENDING_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/mending"), Type.CASING, getUpdateEnchantmentFunction(Enchantments.MENDING));

    public static final Tag<Item> CASING_REPAIR = addUpgradeTag(makeWrapperTag("casing_repair"), Type.REPAIR, getRepairFunction());

    private static Tag<Item> makeWrapperTag(String id) {
        Tag<Item> tag = new ItemTags.Wrapper(new ResourceLocation(ToLaserBlade.MOD_ID, id));
        return tag;
    }

    private static Tag<Item> addUpgradeTag(Tag<Item> tag, Type part, ToIntFunction<ItemStack> function) {
        tags.add(Triple.of(tag, part, function));
        return tag;
    }

    private static ToIntFunction<ItemStack> getUpdateEnchantmentFunction(Enchantment enchantment) {
        return (stack) -> {
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
            if (level < enchantment.getMaxLevel()) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                enchantments.put(enchantment, ++level);
                EnchantmentHelper.setEnchantments(enchantments, stack);
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

    private static ToIntFunction<ItemStack> getRemoveEfficiencyFunction() {
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

    private static ToIntFunction<ItemStack> getUpdateDamageFunction() {
        return (stack) -> {
            float attack = ModItems.LASER_BLADE.getLaserBladeATK(stack);

            if (attack >= LaserBladeItemBase.MOD_ATK_MIN && attack < LaserBladeItemBase.MOD_ATK_CLASS_5) {
                float attack2 = MathHelper.clamp(attack + 1.0F, LaserBladeItemBase.MOD_ATK_MIN, LaserBladeItemBase.MOD_ATK_CLASS_5);
                ModItems.LASER_BLADE.setLaserBladeATK(stack, attack2);
                return Math.max((int)attack2, 1);
            }

            return 0;
        };
    }

    private static ToIntFunction<ItemStack> getUpdateSpeedFunction() {
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

    private static ToIntFunction<ItemStack> getRepairFunction() {
        return (stack) -> {
            if (stack.getDamage() > 0 || stack.getItem() == ModItems.LB_BROKEN) {
                stack.setDamage(0);
                return 1;
            }

            return 0;
        };
    }

    public static List<Triple<Tag<Item>, Type, ToIntFunction<ItemStack>>> getTags() {
        return ImmutableList.copyOf(tags);
    }
}
