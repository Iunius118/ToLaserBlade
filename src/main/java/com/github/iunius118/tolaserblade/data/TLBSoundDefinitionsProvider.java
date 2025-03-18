package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class TLBSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public TLBSoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_SWING, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_SWING, true);
        addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_HIT, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_HIT, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_HIT, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_BLOCK, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK, true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_TRAP_ACTIVATE, true);
        addSingleFileSound(ModSoundEvents.ITEM_LB_BRAND_NEW_USE, true);
        addSingleFileSound(ModSoundEvents.ITEM_LB_BRAND_NEW_FP_USE, true);
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

    private SoundDefinition.Sound getSound(ResourceLocation location) {
        return sound(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath().replace(".", "/")));
    }
}
