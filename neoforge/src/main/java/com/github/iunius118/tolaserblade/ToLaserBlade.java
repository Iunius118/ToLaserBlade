package com.github.iunius118.tolaserblade;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@Mod(Constants.MOD_ID)
public class ToLaserBlade {
    public static IEventBus modEventBus;

    public ToLaserBlade(IEventBus modEventBus, ModContainer modContainer) {
        ToLaserBlade.modEventBus = modEventBus;

        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        // Register mod event listeners
        modEventBus.addListener(this::addPackFinders);
    }

    private void addPackFinders(final AddPackFindersEvent event) {
        event.addPackFinders(Constants.DataPacks.REPULSIVE_FORCE.id().withPrefix("resourcepacks/"),
                PackType.SERVER_DATA, Component.translatable(Constants.DataPacks.REPULSIVE_FORCE.nameKey()),
                PackSource.BUILT_IN, false, Pack.Position.TOP);
    }
}
