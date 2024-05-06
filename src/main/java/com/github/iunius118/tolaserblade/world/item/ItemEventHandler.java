package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
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
            Player player = event.getEntity();
            ItemStack itemStack1 = itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy();

            if (event.getTarget().interact(event.getEntity(), event.getHand()).consumesAction()) {
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
    public static void onCriticalHit(CriticalHitEvent event) {
        ItemStack stack = event.getEntity().getMainHandItem();

        if (stack.getItem() instanceof LBSwordItem lbSwordItem) {
            lbSwordItem.onCriticalHit(event);
        }
    }
}
