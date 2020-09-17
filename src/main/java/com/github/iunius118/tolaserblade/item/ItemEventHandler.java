package com.github.iunius118.tolaserblade.item;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemEventHandler {
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();

        // Redundant Null Check for Forge
        if (itemStack != null && (itemStack.getItem() == ModItems.LASER_BLADE || itemStack.getItem() == ModItems.LASER_BLADE_FP)) {
            // Avoid duplication of Laser Blade when player interact with Item Frame
            event.setCanceled(true);
            PlayerEntity player = event.getPlayer();
            ItemStack itemStack1 = itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy();

            if (event.getTarget().processInitialInteract(event.getPlayer(), event.getHand()).isSuccessOrConsume()) {
                if (player.abilities.isCreativeMode && itemStack == event.getItemStack() && itemStack.getCount() < itemStack1.getCount()) {
                    itemStack.setCount(itemStack1.getCount());
                }

                event.setCancellationResult(ActionResultType.SUCCESS);
                return;
            }

            event.setCancellationResult(ActionResultType.PASS);
        }
    }

    @SubscribeEvent
    public void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        PlayerEntity player = event.getPlayer();

        if (!player.getEntityWorld().isRemote) {
            ItemStack original = event.getOriginal();
            ItemStack brokenLaserBlade = null;

            if (original == null) {
                // Redundant Null Check for Forge

            } else if (original.getItem() == ModItems.LASER_BLADE) {
                brokenLaserBlade = new ItemStack(ModItems.LB_BROKEN);

            } else if (original.getItem() == ModItems.LASER_BLADE_FP) {
                brokenLaserBlade = new ItemStack(ModItems.LB_BROKEN_FP);
            }

            if (brokenLaserBlade != null) {
                brokenLaserBlade.setTag(original.getOrCreateTag().copy());
                brokenLaserBlade.setDamage(0);

                // Drop Broken Laser Blade
                ItemEntity itemEntity = new ItemEntity(player.world, player.getPosX(), player.getPosY() + 0.5, player.getPosZ(), brokenLaserBlade);
                player.world.addEntity(itemEntity);
            }
        }
    }

    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event) {
        ItemStack stack = event.getPlayer().getHeldItemMainhand();

        if (stack.getItem() instanceof LaserBladeItem) {
            ((LaserBladeItem) stack.getItem()).onCriticalHit(event);
        }
    }
}
