package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;

import java.util.Optional;
import java.util.Set;

public class TLBEnchantmentProvider {
    private final static String PACK_PATH = "enchantment_fix";
    private final static ResourceLocation PACK_ID = ToLaserBlade.makeId(PACK_PATH);

    private TLBEnchantmentProvider() {}

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var lookupProvider = event.getLookupProvider();
        var builtinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, TLBDataGenerator.getEntriesBuilder(), Set.of(ToLaserBlade.MOD_ID));
        final boolean includesServer = event.includeServer();
        var packGenerator = dataGenerator.getBuiltinDatapack(includesServer, PACK_PATH);

        packGenerator.addProvider((o) -> PackMetadataGenerator.forFeaturePack(packOutput, Component.literal("ToLB - temporary enchantment correction (mandatory)")));
        packGenerator.addProvider((o) -> builtinEntriesProvider);
    }

    public static void addPackFinders(final AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }

        var knownPack = new KnownPack(ToLaserBlade.MOD_ID, PACK_PATH, "1.0");
        var packInfo = new PackLocationInfo(PACK_ID.toString(), Component.literal(PACK_PATH), PackSource.DEFAULT, Optional.of(knownPack));
        var resourcePath = ModList.get().getModFileById(ToLaserBlade.MOD_ID).getFile().findResource(PACK_PATH);
        var packConfig = new PackSelectionConfig(true, Pack.Position.BOTTOM, false);
        var pack = Pack.readMetaAndCreate(packInfo, new PathPackResources.PathResourcesSupplier(resourcePath), PackType.SERVER_DATA, packConfig);

        if (pack != null) {
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }
}
