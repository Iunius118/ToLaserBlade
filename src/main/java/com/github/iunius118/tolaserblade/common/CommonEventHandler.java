package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.config.TLBClientConfig;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

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
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        /*
        // For debug
        String str = event.getSource().getDamageType() + " caused " + event.getAmount() + " point damage to " + event.getEntityLiving().getName().getString() + "!";
        if (FMLLoader.getDist().isClient()) {
            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(str));
        } else {
            LOGGER.info(str);
        }
        // */
    }
}
