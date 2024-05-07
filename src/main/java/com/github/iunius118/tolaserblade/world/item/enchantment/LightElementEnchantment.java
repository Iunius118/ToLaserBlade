package com.github.iunius118.tolaserblade.world.item.enchantment;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModEntityTypeTags;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nullable;
import java.util.Optional;

public class LightElementEnchantment extends DamageEnchantment {
    public static final ResourceLocation ID = ToLaserBlade.makeId("light_element");
    private static final int MAX_LEVEL = 10;
    private static final Enchantment.EnchantmentDefinition DEFINITION = Enchantment.definition(ItemTags.WEAPON_ENCHANTABLE, ItemTags.SWORD_ENCHANTABLE, 5, MAX_LEVEL, Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(25, 8), 2, EquipmentSlot.MAINHAND);

    private static final TagKey<EntityType<?>> TARGETS = ModEntityTypeTags.SENSITIVE_TO_LIGHT_ELEMENT;

    public LightElementEnchantment() {
        super(DEFINITION, Optional.ofNullable(TARGETS));
    }

    @Override
    public float getDamageBonus(int level, @Nullable EntityType<?> entityType) {
        if (entityType != null && entityType.is(TARGETS)) {
            return (float)level * 2.4F;
        }

        return (float)level * 0.4F;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof LaserBladeItemBase;
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
