package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class TLBSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public TLBSoundDefinitionsProvider(PackOutput packOutput) {
        super(packOutput, ToLaserBlade.MOD_ID);
    }

    @Override
    public void registerSounds() {
        addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_SWING);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_SWING);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);
        addSingleFileSound(ModSoundEvents.ITEM_DX_LASER_BLADE_HIT);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_HIT);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_HIT);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_BLOCK);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_TRAP_ACTIVATE);
        addSingleFileSound(ModSoundEvents.ITEM_LB_BRAND_NEW_USE);
        addSingleFileSound(ModSoundEvents.ITEM_LB_BRAND_NEW_FP_USE);
    }

    private void addSingleFileSound(SoundEvent soundEvent) {
        var soundDefinition = definition().with(getSound(soundEvent.location()));
        add(soundEvent, soundDefinition);
    }

    private SoundDefinition.Sound getSound(ResourceLocation location) {
        return sound(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath().replace(".", "/")));
    }
}
