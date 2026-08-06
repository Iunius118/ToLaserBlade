package com.github.iunius118.tolaserblade;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Optional;

@Mod(Constants.MOD_ID)
public class ToLaserBlade {
    public static BusGroup modBusGroup;

    public ToLaserBlade(FMLJavaModLoadingContext context) {
        ToLaserBlade.modBusGroup = context.getModBusGroup();

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register event listeners
        AddPackFindersEvent.BUS.addListener(this::addPackFinders);

        // Init client-side
        if (FMLLoader.getDist().isClient()) {
            ToLaserBladeClient.initClient(context);
        }
    }

    private void addPackFinders(final AddPackFindersEvent event) {
        switch (event.getPackType()) {
            case SERVER_DATA -> {
                // Repulsive Force Pack (Server Data Pack)
                event.addRepositorySource(s -> s.accept(
                        createPack(Constants.DataPacks.REPULSIVE_FORCE.id().withPrefix("resourcepacks/"),
                                PackType.SERVER_DATA,
                                Component.translatable(Constants.DataPacks.REPULSIVE_FORCE.nameKey()),
                                PackSource.BUILT_IN, false, Pack.Position.TOP
                        )
                ));
            }
            case CLIENT_RESOURCES -> {
                // Sample Sound Pack (Client Resource Pack)
                event.addRepositorySource(s -> s.accept(
                        createPack(
                                Constants.DataPacks.SAMPLE_SOUND_PACK.id().withPrefix("resourcepacks/"),
                                PackType.CLIENT_RESOURCES,
                                Component.translatable(Constants.DataPacks.SAMPLE_SOUND_PACK.nameKey()),
                                PackSource.BUILT_IN, false, Pack.Position.TOP
                        )
                ));
            }
        }
    }

    private Pack createPack(Identifier location, PackType packType, Component name, PackSource packSource,
                            boolean alwaysActive, Pack.Position packPosition) {
        var knownPack = new KnownPack(location.getNamespace(), location.getPath(), "1.0");
        var packInfo = new PackLocationInfo(location.toString(), name, packSource, Optional.of(knownPack));
        var resourcePath = ModList.getModFileById(location.getNamespace()).getFile().findResource(location.getPath());
        var pathResourcesSupplier = new PathPackResources.PathResourcesSupplier(resourcePath);
        var packConfig = new PackSelectionConfig(alwaysActive, packPosition, false);
        return Pack.readMetaAndCreate(packInfo, pathResourcesSupplier, packType, packConfig);
    }
}
