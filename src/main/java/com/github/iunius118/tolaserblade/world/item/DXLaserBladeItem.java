package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DXLaserBladeItem extends SwordItem {
    public DXLaserBladeItem() {
        super(ModItemTiers.DX_LASER_BLADE, new Item.Properties().attributes(SwordItem.createAttributes(ModItemTiers.DX_LASER_BLADE, 3, -1.2F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.getCommandSenderWorld();

        if (!level.isClientSide && attacker instanceof Player) {
            DXLaserBladeItemUtil.playSwingSound(level, attacker);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        Level level = entity.getCommandSenderWorld();

        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.swinging) {
                DXLaserBladeItemUtil.playSwingSound(level, entity);
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return DXLaserBladeItemUtil.useOn(context, getTier());
    }
}
