package com.github.iunius118.tolaserblade.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class DXLaserBladeItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public DXLaserBladeItem() {
        super(new DXLaserBladeItemTier(), 3, -1.2F, (new Item.Properties()).tab(ModMainItemGroup.ITEM_GROUP));

        IItemTier tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamageBonus();
        attackSpeed = -1.2F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getCommandSenderWorld();

        if (!world.isClientSide && attacker instanceof PlayerEntity) {
            DXLaserBladeItemUtil.playSwingSound(world, attacker);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        World world = entity.getCommandSenderWorld();

        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;

            if (!player.swinging) {
                DXLaserBladeItemUtil.playSwingSound(world, entity);
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        return DXLaserBladeItemUtil.useOn(context, getTier());
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }
}
