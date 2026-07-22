package com.github.iunius118.tolaserblade.sounds;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.platform.Services;
import com.github.iunius118.tolaserblade.registry.ModObjectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    private static final ModObjectRegistry<SoundEvent, SoundEvent> REGISTRY =
            Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    public static final Holder<SoundEvent> ITEM_LASER_BLADE_SWING =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_SWING.getPath());
    public static final Holder<SoundEvent> ITEM_LASER_BLADE_HIT =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_HIT.getPath());
    public static final Holder<SoundEvent> ITEM_LASER_BLADE_BLOCK =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_BLOCK.getPath());
    public static final Holder<SoundEvent> ITEM_LASER_BLADE_BREAK =
            register(Constants.SoundEvents.ITEM_LASER_BLADE_BREAK.getPath());

    private static Holder<SoundEvent> register(String name) {
        var soundEvent = SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return REGISTRY.register(name, () -> soundEvent);
    }

    public static void register() {
        REGISTRY.register();
    }
}
