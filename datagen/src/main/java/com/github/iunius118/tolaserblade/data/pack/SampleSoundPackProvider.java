package com.github.iunius118.tolaserblade.data.pack;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.sounds.ModSoundEvents;
import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class SampleSoundPackProvider {
    private final static String PACK_PATH = "resourcepacks/" + Constants.DataPacks.SAMPLE_SOUND_PACK.id().getPath();
    public final static String PACK_DESCRIPTION = Constants.DataPacks.SAMPLE_SOUND_PACK.descriptionKey();

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var packGenerator = dataGenerator.getBuiltinDatapack(true, PACK_PATH);

        var packMetadataSection = new PackMetadataSection(Component.translatable(PACK_DESCRIPTION),
                DetectedVersion.BUILT_IN.packVersion(PackType.CLIENT_RESOURCES).minorRange());
        packGenerator.addProvider(o ->
                new PackMetadataGenerator(packOutput).add(PackMetadataSection.CLIENT_TYPE, packMetadataSection));

        packGenerator.addProvider(o -> new SampleSoundDefinitionsProvider(packOutput));
    }

    private static class SampleSoundDefinitionsProvider extends SoundDefinitionsProvider {
        public SampleSoundDefinitionsProvider(PackOutput packOutput) {
            super(packOutput, Constants.MOD_ID);
        }

        @Override
        public void registerSounds() {
            final float[] pitch1 = new float[]{1.0F, 1.025F, 1.05F, 1.075F, 1.1F};
            final float[] pitch2 = new float[]{1.0F, 1.1F, 1.2F, 1.3F, 1.4F};

            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_SWING.value(), true, pitch1);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_HIT.value(), true, pitch2);
            addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_BLOCK.value(), true, pitch1);
        }

        private void addSingleFileSound(SoundEvent soundEvent) {
            var soundDefinition = definition().with(getSound(soundEvent.location()));
            add(soundEvent, soundDefinition);
        }

        private void addSingleFileSound(SoundEvent soundEvent, boolean replace) {
            var soundDefinition = definition().with(getSound(soundEvent.location()));

            if (replace) {
                soundDefinition.replace(true);
            }

            add(soundEvent, soundDefinition);
        }

        private void addSingleFileSound(SoundEvent soundEvent, boolean replace, float[] pitchArray) {
            var soundDefinition = definition();

            for (float pitch : pitchArray) {
                soundDefinition.with(getSound(soundEvent.location()).pitch(pitch));
            }

            if (replace) {
                soundDefinition.replace(true);
            }

            add(soundEvent, soundDefinition);
        }

        private SoundDefinition.Sound getSound(Identifier location) {
            return sound(Identifier.fromNamespaceAndPath(location.getNamespace(), location.getPath().replace(".", "/")));
        }
    }
}
