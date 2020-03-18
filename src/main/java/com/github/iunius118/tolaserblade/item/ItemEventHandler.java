package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemEventHandler {
    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event) {
        ItemStack stack = event.getPlayer().getHeldItemMainhand();

        if (stack.getItem() instanceof LaserBladeItem) {
            ((LaserBladeItem) stack.getItem()).onCriticalHit(event);
        }
    }

}
