package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class CommonEventHandler {
    /*
     * Remapping Items
     */

    @SubscribeEvent
    public static void remapItems(RegistryEvent.MissingMappings<Item> mappings) {
        final Map<ResourceLocation, Item> remappingItemMap = new HashMap<>();
        // Replace item ID "tolaserblade:tolaserblade.laser_blade" (-1.11.2) with "tolaserblade:laser_blade" (1.12-)
        remappingItemMap.put(new ResourceLocation(ToLaserBlade.MOD_ID, "tolaserblade.laser_blade"), ModItems.LASER_BLADE);

        // Replace item ID "tolaserblade:lasar_blade" (-1.14.4) with "tolaserblade:dx_laser_blade" (1.15-)
        remappingItemMap.put(new ResourceLocation(ToLaserBlade.MOD_ID, "lasar_blade"), ModItems.DX_LASER_BLADE);

        // Replace item ID "tolaserblade:laser_blade_core" (-1.14.4) with "tolaserblade:lb_broken" (1.15-)
        remappingItemMap.put(new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_core"), ModItems.LB_BROKEN);

        // Replace item IDs
        mappings.getAllMappings().stream()
                .filter(mapping -> mapping.key.getNamespace().equals(ToLaserBlade.MOD_ID) && remappingItemMap.containsKey(mapping.key))
                .forEach(mapping -> mapping.remap(remappingItemMap.get(mapping.key)));
    }

    /*
     * World Events
     */

    public static boolean hasShownUpdate = false;

    @SubscribeEvent
    public static void onEntityJoiningInWorld(final EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide && event.getEntity() instanceof PlayerEntity) {
            // In client
            if (ToLaserBladeConfig.CLIENT.showUpdateMessage.get() && !hasShownUpdate) {
                ClientModEventHandler.checkUpdate();
                hasShownUpdate = true;
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
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