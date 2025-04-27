package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundEventRegistry {
    private static final DeferredRegister<SoundEvent> soundEventRegister = net.neoforged.neoforge.registries.DeferredRegister.create(Registries.SOUND_EVENT, ToLaserBlade.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_DX_LASER_BLADE_SWING = registerSoundEvent("item.dx_laser_blade.swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_BLADE_SWING = registerSoundEvent("item.laser_blade.swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_BLADE_FP_SWING = registerSoundEvent("item.laser_blade_fp.swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_DX_LASER_BLADE_HIT = registerSoundEvent("item.dx_laser_blade.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_BLADE_HIT = registerSoundEvent("item.laser_blade.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_BLADE_FP_HIT = registerSoundEvent("item.laser_blade_fp.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_BLADE_BLOCK = registerSoundEvent("item.laser_blade.block");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_BLADE_FP_BLOCK = registerSoundEvent("item.laser_blade_fp.block");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LASER_TRAP_ACTIVATE = registerSoundEvent("item.laser_trap.activate");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LB_BRAND_NEW_USE = registerSoundEvent("item.lb_brand_new.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LB_BRAND_NEW_FP_USE = registerSoundEvent("item.lb_brand_new_fp.use");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        var soundEvent = SoundEvent.createVariableRangeEvent(ToLaserBlade.makeId(name));
        return soundEventRegister.register(name, () -> soundEvent);
    }

    public static void register(IEventBus modEventBus) {
        ToLaserBlade.LOGGER.debug("Register mod sound events");
        soundEventRegister.register(modEventBus);
    }
}
