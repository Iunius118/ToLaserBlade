package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.config.TLBClientConfig;
import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;
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
    public static void onPlayLevelSoundAtEntity(PlayLevelSoundEvent.AtEntity event) {
        var soundEventHolder = event.getSound();
        var level = event.getLevel();
        var entity = event.getEntity();

        if (soundEventHolder != null && soundEventHolder.value() == SoundEvents.SHIELD_BLOCK
                && level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            // The local player blocks an attack
            var itemStack = livingEntity.getItemInHand(livingEntity.getUsedItemHand());

            if (itemStack.getItem() instanceof LBSwordItem) {
                // Play sound effect when the player blocks an attack with laser blade
                LaserBladeItemUtil.playBlockSound(event, itemStack);
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
