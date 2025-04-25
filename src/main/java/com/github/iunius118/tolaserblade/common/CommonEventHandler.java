package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.config.TLBClientConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
        remappingItemMap.put(ToLaserBlade.makeId("tolaserblade.laser_blade"), ModItems.LASER_BLADE);

        // Replace item ID "tolaserblade:lasar_blade" (-1.14.4) with "tolaserblade:dx_laser_blade" (1.15-)
        remappingItemMap.put(ToLaserBlade.makeId("lasar_blade"), ModItems.DX_LASER_BLADE);

        // Replace item ID "tolaserblade:laser_blade_core" (-1.14.4) with "tolaserblade:lb_broken" (1.15-)
        remappingItemMap.put(ToLaserBlade.makeId("laser_blade_core"), ModItems.LB_BROKEN);

        // Replace item IDs
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).stream()
                .filter(mapping -> mapping.getKey().getNamespace().equals(ToLaserBlade.MOD_ID)
                        && remappingItemMap.containsKey(mapping.getKey()))
                .forEach(mapping -> mapping.remap(remappingItemMap.get(mapping.getKey())));
    }

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
            item.appendTooltip(itemStack, null, event.getFlags(), event.getToolTip());
        }
    }
}
