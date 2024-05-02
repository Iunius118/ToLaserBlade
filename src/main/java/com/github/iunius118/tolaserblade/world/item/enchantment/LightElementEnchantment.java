package com.github.iunius118.tolaserblade.world.item.enchantment;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

public class LightElementEnchantment extends DamageEnchantment {
    public static final ResourceLocation ID = ToLaserBlade.makeId("light_element");
    private static final int MAX_LEVEL = 10;

    public LightElementEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public float getDamageBonus(int level, MobType mobType) {
        if (mobType == MobType.UNDEAD || mobType == MobType.ILLAGER) {
            return (float)level * 2.4F;
        }

        return (float)level * 0.4F;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        // Only Laser Blade items
        return super.canApplyAtEnchantingTable(stack) && stack.getItem() instanceof LaserBladeItemBase;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {

    }
}
