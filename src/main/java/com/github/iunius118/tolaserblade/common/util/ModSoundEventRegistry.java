package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEventRegistry {
    private static final DeferredRegister<SoundEvent> soundEventRegister = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ToLaserBlade.MOD_ID);

    public static final RegistryObject<SoundEvent> ITEM_DX_LASER_BLADE_SWING = registerSoundEvent("item.dx_laser_blade.swing");
    public static final RegistryObject<SoundEvent> ITEM_LASER_BLADE_SWING = registerSoundEvent("item.laser_blade.swing");
    public static final RegistryObject<SoundEvent> ITEM_LASER_BLADE_FP_SWING = registerSoundEvent("item.laser_blade_fp.swing");
    public static final RegistryObject<SoundEvent> ITEM_DX_LASER_BLADE_HIT = registerSoundEvent("item.dx_laser_blade.hit");
    public static final RegistryObject<SoundEvent> ITEM_LASER_BLADE_HIT = registerSoundEvent("item.laser_blade.hit");
    public static final RegistryObject<SoundEvent> ITEM_LASER_BLADE_FP_HIT = registerSoundEvent("item.laser_blade_fp.hit");
    public static final RegistryObject<SoundEvent> ITEM_LASER_BLADE_BLOCK = registerSoundEvent("item.laser_blade.block");
    public static final RegistryObject<SoundEvent> ITEM_LASER_BLADE_FP_BLOCK = registerSoundEvent("item.laser_blade_fp.block");
    public static final RegistryObject<SoundEvent> ITEM_LASER_TRAP_ACTIVATE = registerSoundEvent("item.laser_trap.activate");
    public static final RegistryObject<SoundEvent> ITEM_LB_BRAND_NEW_USE = registerSoundEvent("item.lb_brand_new.use");
    public static final RegistryObject<SoundEvent> ITEM_LB_BRAND_NEW_FP_USE = registerSoundEvent("item.lb_brand_new_fp.use");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        var soundEvent = SoundEvent.createVariableRangeEvent(ToLaserBlade.makeId(name));
        return soundEventRegister.register(name, () -> soundEvent);
    }

    public static void register(BusGroup modBusGroup) {
        ToLaserBlade.LOGGER.debug("Register mod sound events");
        soundEventRegister.register(modBusGroup);
    }
}
