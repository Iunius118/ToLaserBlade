package com.github.iunius118.tolaserblade.sounds;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.platform.Services;
import com.github.iunius118.tolaserblade.registry.ModObjectRegistry;
import com.github.iunius118.tolaserblade.registry.ModRegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    private static final ModObjectRegistry<SoundEvent, SoundEvent> REGISTRY =
            Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    public static final ModRegistryObject<SoundEvent> ITEM_LASER_BLADE_SWING =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_SWING);
    public static final ModRegistryObject<SoundEvent> ITEM_LASER_BLADE_HIT =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_HIT);
    public static final ModRegistryObject<SoundEvent> ITEM_LASER_BLADE_BLOCK =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_BLOCK);
    public static final ModRegistryObject<SoundEvent> ITEM_LASER_BLADE_BREAK =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_BREAK);

    private static ModRegistryObject<SoundEvent> register(ResourceKey<SoundEvent> key) {
        var soundEvent = SoundEvent.createVariableRangeEvent(key.identifier());
        return REGISTRY.register(key, () -> soundEvent);
    }

    public static void register() {
        REGISTRY.register();
    }
}
