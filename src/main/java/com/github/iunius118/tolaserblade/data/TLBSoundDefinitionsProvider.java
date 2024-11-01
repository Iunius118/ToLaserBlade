package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class TLBSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public TLBSoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_SWING);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_SWING);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);
    }

    private void addSingleFileSound(SoundEvent soundEvent) {
        var soundDefinition = definition().with(getSound(soundEvent.location()));
        add(soundEvent, soundDefinition);
    }

    private SoundDefinition.Sound getSound(ResourceLocation location) {
        return sound(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath().replace(".", "/")));
    }
}
