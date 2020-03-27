package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.item.crafting.LaserBladeCrafting;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemEventHandler {
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();

        // Redundant Null Check for Forge
        if (itemStack != null && itemStack.getItem() == ModItems.LASER_BLADE) {
            // Avoid duplication of Laser Blade when player interact with Item Frame
            event.setCanceled(true);
            PlayerEntity player = event.getPlayer();
            ItemStack itemStack1 = itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy();

            if (event.getTarget().processInitialInteract(event.getPlayer(), event.getHand())) {
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

            // Redundant Null Check for Forge
            if (original != null && original.getItem() == ModItems.LASER_BLADE) {
                ItemStack brokenLaserBlade = new ItemStack(ModItems.LB_BROKEN);
                brokenLaserBlade.setTag(original.getOrCreateTag().copy());

                // Add Broken Laser Blade to Player's inventory
                if (!player.addItemStackToInventory(brokenLaserBlade)) {
                    // ...or drop it
                    ItemEntity itemEntity = new ItemEntity(player.world, player.getPosX(), player.getPosY() + 0.5, player.getPosZ(), brokenLaserBlade);
                    player.world.addEntity(itemEntity);
                }
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

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        ItemStack stackOut = event.getCrafting();
        Item item = stackOut.getItem();

        if (item instanceof LaserBladeItemBase) {
            (new LaserBladeCrafting(event, (LaserBladeItemBase)item)).getResult();
        }
    }

    @SubscribeEvent
    public void onAnvilRepair(AnvilRepairEvent event) {
        ItemStack left = event.getItemInput();

        if (left.getItem() instanceof LaserBladeItemBase) {
            event.setBreakChance(0.075F);
        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();

        // Redundant Null Check for Forge
        if (left != null && left.getItem() instanceof LaserBladeItemBase) {
            LaserBladeUpgrade.onAnvilUpdate(event, (LaserBladeItemBase)left.getItem());
        }
    }
}
