package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemEventHandler {
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();

        // Redundant Null Check for Forge
        if (itemStack != null && (itemStack.getItem() == ModItems.LASER_BLADE || itemStack.getItem() == ModItems.LASER_BLADE_FP)) {
            // Avoid duplication of Laser Blade when player interact with Item Frame
            event.setCanceled(true);
            Player player = event.getPlayer();
            ItemStack itemStack1 = itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy();

            if (event.getTarget().interact(event.getPlayer(), event.getHand()).consumesAction()) {
                if (player.getAbilities().instabuild && itemStack == event.getItemStack() && itemStack.getCount() < itemStack1.getCount()) {
                    itemStack.setCount(itemStack1.getCount());
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
                return;
            }

            event.setCancellationResult(InteractionResult.PASS);
        }
    }

    @SubscribeEvent
    public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        Player player = event.getPlayer();

        if (!player.getCommandSenderWorld().isClientSide) {
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
                brokenLaserBlade.setDamageValue(0);

                // Drop Broken Laser Blade
                ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY() + 0.5, player.getZ(), brokenLaserBlade);
                player.level.addFreshEntity(itemEntity);
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        ItemStack stack = event.getPlayer().getMainHandItem();

        if (stack.getItem() instanceof LBSwordItem lbSwordItem) {
            lbSwordItem.onCriticalHit(event);
        }
    }
}
