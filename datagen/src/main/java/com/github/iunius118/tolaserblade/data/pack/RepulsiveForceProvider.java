package com.github.iunius118.tolaserblade.data.pack;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import net.minecraft.DetectedVersion;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public class RepulsiveForceProvider {
    // Add `resourcepacks/` prefix to pack path for fabric's resource loader
    private final static String PACK_PATH = "resourcepacks/" + Constants.DataPacks.REPULSIVE_FORCE.id().getPath();
    public final static String PACK_DESCRIPTION = Constants.DataPacks.REPULSIVE_FORCE.descriptionKey();

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var packGenerator = dataGenerator.getBuiltinDatapack(true, PACK_PATH);

        var packMetadataSection = new PackMetadataSection(Component.translatable(PACK_DESCRIPTION),
                DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());
        packGenerator.addProvider(o ->
                new PackMetadataGenerator(packOutput).add(PackMetadataSection.SERVER_TYPE, packMetadataSection));

        var lookupProvider = event.getLookupProvider();
        var builder = new RegistrySetBuilder().add(Registries.ENCHANTMENT, ModEnchantments::bootstrapOptional);
        var builtinEntriesProvider =
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of(Constants.MOD_ID));
        packGenerator.addProvider(o -> builtinEntriesProvider);
    }
}
