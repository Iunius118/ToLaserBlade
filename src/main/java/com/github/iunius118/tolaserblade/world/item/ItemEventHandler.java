package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

public class ItemEventHandler {
    @SubscribeEvent
    public static boolean onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();

        // Redundant Null Check for Forge
        if (itemStack != null && (itemStack.getItem() == ModItems.LASER_BLADE || itemStack.getItem() == ModItems.LASER_BLADE_FP)) {
            // Avoid duplication of Laser Blade when player interact with Item Frame
            Player player = event.getEntity();
            ItemStack itemStack1 = itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy();

            if (event.getTarget().interact(event.getEntity(), event.getHand()).consumesAction()) {
                if (player.getAbilities().instabuild && itemStack == event.getItemStack() && itemStack.getCount() < itemStack1.getCount()) {
                    itemStack.setCount(itemStack1.getCount());
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
                // Canceled
                return true;
            }

            event.setCancellationResult(InteractionResult.PASS);
            // Canceled
            return true;
        }

        return false;
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        ItemStack stack = event.getEntity().getMainHandItem();

        if (stack.getItem() instanceof LBSwordItem lbSwordItem) {
            lbSwordItem.onCriticalHit(event);
        }
    }

    @SubscribeEvent
    public static void onMineBlock(PlayerEvent.BreakSpeed event) {
        LaserBladeItemUtil.changeDestroySpeed(event);
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        var player = event.getEntity();
        var level = player.level();

        if (level.isClientSide() || player.isSpectator()) {
            return;
        }

        Entity target = event.getTarget();
        var itemStack = player.getMainHandItem();
        var item = itemStack.getItem();

        // Play sound effect when a player attacks an entity with laser blade
        if (item instanceof LBSwordItem) {
            // Laser Blade (normal/FP)
            LaserBladeItemUtil.playHitSound(level, target, itemStack);
        } else if (item instanceof DXLaserBladeItem) {
            // DX Laser B1ade
            DXLaserBladeItemUtil.playHitSound(level, target, itemStack);
        }
    }
}
