package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.HashMap;
import java.util.Map;

public class CommonEventHandler {
    /*
     * Remapping Items
     */

    @SubscribeEvent
    public static void remapItems(MissingMappingsEvent event) {
        if (event.getKey() != ForgeRegistries.Keys.ITEMS)
            return;

        final Map<ResourceLocation, Item> remappingItemMap = new HashMap<>();
        // Replace item ID "tolaserblade:tolaserblade.laser_blade" (-1.11.2) with "tolaserblade:laser_blade" (1.12-)
        remappingItemMap.put(new ResourceLocation(ToLaserBlade.MOD_ID, "tolaserblade.laser_blade"), ModItems.LASER_BLADE);

        // Replace item ID "tolaserblade:lasar_blade" (-1.14.4) with "tolaserblade:dx_laser_blade" (1.15-)
        remappingItemMap.put(new ResourceLocation(ToLaserBlade.MOD_ID, "lasar_blade"), ModItems.DX_LASER_BLADE);

        // Replace item ID "tolaserblade:laser_blade_core" (-1.14.4) with "tolaserblade:lb_broken" (1.15-)
        remappingItemMap.put(new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_core"), ModItems.LB_BROKEN);

        // Replace item IDs
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).stream()
                .filter(mapping -> mapping.getKey().getNamespace().equals(ToLaserBlade.MOD_ID) && remappingItemMap.containsKey(mapping.getKey()))
                .forEach(mapping -> mapping.remap(remappingItemMap.get(mapping.getKey())));
    }

    /*
     * World Events
     */

    public static boolean hasShownUpdate = false;

    @SubscribeEvent
    public static void onEntityJoiningInWorld(final EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide && event.getEntity() instanceof Player) {
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
