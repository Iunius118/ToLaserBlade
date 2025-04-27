package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.config.TLBClientConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class CommonEventHandler {
    /*
     * World Events
     */

    public static boolean hasShownUpdate = false;

    @SubscribeEvent
    public static void onEntityJoiningInWorld(final EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide && event.getEntity() instanceof Player) {
            // In client
            if (TLBClientConfig.showUpdateMessage && !hasShownUpdate) {
                ClientModEventHandler.checkUpdate();
                hasShownUpdate = true;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayLevelSoundAtPosition(PlayLevelSoundEvent.AtPosition event) {
        if (event.getLevel().isClientSide()
                && LaserBladeBlocking.isBlockingSound(event.getSound())) {
            // Play sound effect when the player blocks an attack with laser blade
            LaserBladeItemUtil.playBlockSound(event);
        }
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(final ItemTooltipEvent event) {
        final var itemStack = event.getItemStack();

        if (itemStack.getItem() instanceof LaserBladeItemBase item) {
            item.appendTooltip(itemStack, event.getContext(), event.getFlags(), event.getToolTip());
        }
    }
}
