package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Optional;
import java.util.function.Function;

public enum LaserBladeItemStack {
    ORIGINAL(p -> new ItemStack(ModItems.LASER_BLADE)),
    ICON(LaserBladeItemStack::getIconStack),
    MODEL_TYPE_526(p -> getModelChangedStack(526, false, p)),
    LIGHT_ELEMENT_1(p -> getLightElementStack(1, p)),
    LIGHT_ELEMENT_2(p -> getLightElementStack(2, p)),
    GIFT(LaserBladeItemStack::getGiftStack),
    UPGRADED(p -> getUpgradedStack(false, false, p)),
    DAMAGED(p -> getUpgradedStack(false, true, p)),
    FULL_MOD(p -> getFullModStack(false, false, p)),
    FP(p -> new ItemStack(ModItems.LASER_BLADE_FP)),
    UPGRADED_FP(p -> getUpgradedStack(true, false, p)),
    DAMAGED_FP(p -> getUpgradedStack(true, true, p)),
    FULL_MOD_FP(p -> getFullModStack(false, true, p)),
    DISASSEMBLED_FULL_MOD(p -> getFullModStack(true, false, p)),
    DISASSEMBLED_FULL_MOD_FP(p -> getFullModStack(true, true, p));

    private final Function<HolderLookup.Provider, ItemStack> getter;

    LaserBladeItemStack(Function<HolderLookup.Provider, ItemStack> getter) {
        this.getter = getter;
    }

    public ItemStack getCopy(HolderLookup.Provider lookupProvider) {
        return getter.apply(lookupProvider);
    }

    private static ItemStack getIconStack(HolderLookup.Provider lookupProvider) {
        var stack = new ItemStack(ModItems.LASER_BLADE);
        new LaserBladeAppearance().setGripColor(LaserBladeColor.LIGHT_GRAY.getGripColor()).setTo(stack);
        return stack;
    }

    public static ItemStack getModelChangedStack(int type, boolean isFireproof, HolderLookup.Provider lookupProvider) {
        var stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBladeAppearance.of().setType(type).setTo(stack);
        return stack;
    }

    private static ItemStack getLightElementStack(int lightElementLevel, HolderLookup.Provider lookupProvider) {
        var stack = new ItemStack(ModItems.LASER_BLADE);
        enchant(stack, ModEnchantments.LIGHT_ELEMENT, lightElementLevel, lookupProvider);
        enchant(stack, Enchantments.EFFICIENCY, 1, lookupProvider);
        return stack;
    }

    private static ItemStack getGiftStack(HolderLookup.Provider lookupProvider) {
        var stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBlade.setAttack(stack, LaserBlade.MOD_ATK_GIFT);
        LaserBlade.updateItemAttributeModifiers(stack);
        LaserBladeAppearance.of()
                .setOuterColor(LaserBladeColor.LIME.getBladeColor())
                .setGripColor(LaserBladeColor.BROWN.getGripColor())
                .setTo(stack);
        enchant(stack, ModEnchantments.LIGHT_ELEMENT, 5, lookupProvider);
        enchant(stack, Enchantments.EFFICIENCY, 1, lookupProvider);
        return stack;
    }

    private static ItemStack getUpgradedStack(boolean isFireproof, boolean isDamaged, HolderLookup.Provider lookupProvider) {
        var stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBlade.setAttack(stack, LaserBlade.MOD_ATK_CRITICAL_BONUS);
        LaserBlade.setSpeed(stack, LaserBlade.MOD_SPD_MAX);
        LaserBlade.updateItemAttributeModifiers(stack);
        LaserBladeAppearance.of()
                .setOuterColor(LaserBladeColor.BLUE.getBladeColor())
                .setInnerColor(LaserBladeColor.LIGHT_BLUE.getBladeColor())
                .setGripColor(LaserBladeColor.GRAY.getGripColor())
                .setTo(stack);
        enchantMaxLevel(stack, ModEnchantments.LIGHT_ELEMENT, lookupProvider);
        enchantMaxLevel(stack, Enchantments.EFFICIENCY, lookupProvider);
        enchantMaxLevel(stack, Enchantments.MENDING, lookupProvider);

        if (isDamaged) {
            int maxUses = isFireproof ? ModItemTiers.LASER_BLADE_FP.getUses() : ModItemTiers.LASER_BLADE.getUses();
            stack.setDamageValue(maxUses - 1);
        }

        return stack;
    }

    private static ItemStack getFullModStack(boolean isDisassembled, boolean isFireproof, HolderLookup.Provider lookupProvider) {
        var stack = new ItemStack(
                isDisassembled ? (isFireproof ? ModItems.LB_DISASSEMBLED_FP : ModItems.LB_DISASSEMBLED) :
                        (isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE)
        );
        LaserBlade.setAttack(stack, LaserBlade.MOD_ATK_CRITICAL_BONUS);
        LaserBlade.setSpeed(stack, LaserBlade.MOD_SPD_MAX);
        LaserBlade.updateItemAttributeModifiers(stack);
        LaserBladeAppearance.of()
                .setOuterColor(LaserBladeColor.CYAN.getBladeColor()).setOuterSubColor(true)
                .setInnerColor(LaserBladeColor.WHITE.getBladeColor()).setInnerSubColor(true)
                .setGripColor(LaserBladeColor.GRAY.getGripColor())
                .setTo(stack);
        enchantMaxLevel(stack, ModEnchantments.LIGHT_ELEMENT, lookupProvider);
        enchantMaxLevel(stack, Enchantments.EFFICIENCY, lookupProvider);
        enchantMaxLevel(stack, Enchantments.MENDING, lookupProvider);
        enchantMaxLevel(stack, Enchantments.FIRE_ASPECT, lookupProvider);
        enchantMaxLevel(stack, Enchantments.SWEEPING_EDGE, lookupProvider);
        enchantMaxLevel(stack, Enchantments.SILK_TOUCH, lookupProvider);
        enchantMaxLevel(stack, Enchantments.LOOTING, lookupProvider);
        return stack;
    }

    private static void enchant(ItemStack stack, ResourceKey<Enchantment> enchantmentKey, int level, HolderLookup.Provider lookupProvider) {
        Optional<Holder.Reference<Enchantment>> enchantment = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT).get(enchantmentKey);
        enchantment.ifPresent(e -> stack.enchant(e, level));
    }

    private static void enchantMaxLevel(ItemStack stack, ResourceKey<Enchantment> enchantmentKey, HolderLookup.Provider lookupProvider) {
        Optional<Holder.Reference<Enchantment>> enchantment = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT).get(enchantmentKey);
        enchantment.ifPresent(e -> stack.enchant(e, e.value().getMaxLevel()));
    }
}
