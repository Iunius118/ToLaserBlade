package com.github.iunius118.tolaserblade.data.pack;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import net.minecraft.DetectedVersion;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.Set;

public class RepulsiveForceProvider {
    private final static String PACK_PATH = "repulsive_force";
    private final static Identifier PACK_ID = ToLaserBlade.id(PACK_PATH);
    public final static String PACK_TITLE = "TLB Repulsive Force Pack";
    public final static String PACK_DESCRIPTION = "ToLaserBlade - Add Repulsive Force enchantment";


    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var packGenerator = dataGenerator.getBuiltinDatapack(true, PACK_PATH);

        var packMetadataSection = new PackMetadataSection(Component.literal(PACK_DESCRIPTION),
                DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());
        packGenerator.addProvider(o ->
                new PackMetadataGenerator(packOutput).add(PackMetadataSection.SERVER_TYPE, packMetadataSection));

        var lookupProvider = event.getLookupProvider();
        var builder = new RegistrySetBuilder().add(Registries.ENCHANTMENT, ModEnchantments::bootstrapOptional);
        var builtinEntriesProvider =
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of(ToLaserBlade.MOD_ID));
        packGenerator.addProvider(o -> builtinEntriesProvider);
    }

    public static void addPackFinders(final AddPackFindersEvent event) {
        event.addPackFinders(PACK_ID, PackType.SERVER_DATA, Component.literal(PACK_TITLE), PackSource.BUILT_IN, false,
                Pack.Position.TOP);
    }
}
