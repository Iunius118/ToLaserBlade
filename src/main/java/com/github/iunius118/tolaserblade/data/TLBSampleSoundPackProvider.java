package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;

public class TLBSampleSoundPackProvider {
    public final static String PACK_PATH = "sample_sound_pack";
    public final static ResourceLocation PACK_ID = ToLaserBlade.makeId(PACK_PATH);
    public final static String PACK_TITLE = "TLB Sample Sound Pack";
    public final static String PACK_DESCRIPTION = "ToLaserBlade - sample sound pack for laser blade";

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var existingFileHelper = event.getExistingFileHelper();
        final boolean includesClient = event.includeClient();
        var packGenerator = dataGenerator.getBuiltinDatapack(includesClient, PACK_PATH);

        var packMetadataSection = new PackMetadataSection(Component.literal(PACK_DESCRIPTION), DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES));
        packGenerator.addProvider(o -> new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, packMetadataSection));
        packGenerator.addProvider(o -> new TLBSampleSoundPackProvider.SampleSoundDefinitionsProvider(packOutput, existingFileHelper));
    }

    public static void addPackFinders(final AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) {
            return;
        }

        var resourcePath = ModList.get().getModFileById(ToLaserBlade.MOD_ID).getFile().findResource(PACK_PATH);
        var pack = Pack.readMetaAndCreate(PACK_ID.toString(), Component.literal(PACK_TITLE), false,
                (path) -> new PathPackResources(path, resourcePath, false), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);

        if (pack != null) {
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    private static class SampleSoundDefinitionsProvider extends SoundDefinitionsProvider {
        public SampleSoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
            super(packOutput, ToLaserBlade.MOD_ID, existingFileHelper);
        }

        @Override
        public void registerSounds() {
            final float[] pitch1 = new float[]{1.0F, 1.025F, 1.05F, 1.075F, 1.1F};
            final float[] pitch2 = new float[]{1.0F, 1.1F, 1.2F, 1.3F, 1.4F};

            addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, true, pitch1);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_SWING, true, pitch1);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_SWING, true, pitch1);
            addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_HIT, true, pitch2);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_HIT, true, pitch2);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_HIT, true, pitch2);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_BLOCK, true, pitch1);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK, true, pitch1);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_TRAP_ACTIVATE, true, pitch2);
            addSingleFileSound(ModSoundEvents.ITEM_LB_BRAND_NEW_USE, true, pitch2);
            addSingleFileSound(ModSoundEvents.ITEM_LB_BRAND_NEW_FP_USE, true, pitch2);
        }

        private void addSingleFileSound(SoundEvent soundEvent) {
            var soundDefinition = definition().with(getSound(soundEvent.getLocation()));
            add(soundEvent, soundDefinition);
        }

        private void addSingleFileSound(SoundEvent soundEvent, boolean replace) {
            var soundDefinition = definition().with(getSound(soundEvent.getLocation()));

            if (replace) {
                soundDefinition.replace(true);
            }

            add(soundEvent, soundDefinition);
        }

        private void addSingleFileSound(SoundEvent soundEvent, boolean replace, float[] pitchArray) {
            var soundDefinition = definition();

            for (float pitch : pitchArray) {
                soundDefinition.with(getSound(soundEvent.getLocation()).pitch(pitch));
            }

            if (replace) {
                soundDefinition.replace(true);
            }

            add(soundEvent, soundDefinition);
        }

        private SoundDefinition.Sound getSound(ResourceLocation location) {
            return sound(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath().replace(".", "/")));
        }
    }
}
