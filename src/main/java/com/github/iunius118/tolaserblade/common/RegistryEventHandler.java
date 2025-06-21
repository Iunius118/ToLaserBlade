package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEventRegistry;
import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.github.iunius118.tolaserblade.core.component.predicates.ModDataComponentPredicates;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.github.iunius118.tolaserblade.world.item.ModItemRegistry;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryEventHandler {
    public static void registerGameObjects(BusGroup modBusGroup) {
        registerRecipeSerializers(modBusGroup);
        registerItems(modBusGroup);
        registerParticleTypes(modBusGroup);
        registerSoundEvents(modBusGroup);
        registerCreativeModeTabs(modBusGroup);
        registerDataComponentTypes(modBusGroup);
        registerDataComponentPredicateTypes(modBusGroup);
    }

    private static void registerRecipeSerializers(BusGroup modBusGroup) {
        var recipeSerializerRegister = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ToLaserBlade.MOD_ID);

        recipeSerializerRegister.register("upgrade", () -> ModRecipeSerializers.UPGRADE);
        recipeSerializerRegister.register("color", () -> ModRecipeSerializers.COLOR);
        recipeSerializerRegister.register("model_change", () -> ModRecipeSerializers.MODEL_CHANGE);

        recipeSerializerRegister.register(modBusGroup);
    }

    private static void registerItems(BusGroup modBusGroup) {
        ModItemRegistry.register(modBusGroup);
    }

    private static void registerParticleTypes(BusGroup modBusGroup) {
        var particleTypeRegister = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ToLaserBlade.MOD_ID);

        particleTypeRegister.register("laser_trap_x", () -> ModParticleTypes.LASER_TRAP_X);
        particleTypeRegister.register("laser_trap_y", () -> ModParticleTypes.LASER_TRAP_Y);
        particleTypeRegister.register("laser_trap_z", () -> ModParticleTypes.LASER_TRAP_Z);

        particleTypeRegister.register(modBusGroup);
    }

    private static void registerSoundEvents(BusGroup modBusGroup) {
        ModSoundEventRegistry.register(modBusGroup);
    }

    private static void registerCreativeModeTabs(BusGroup modBusGroup) {
        var creativeModeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToLaserBlade.MOD_ID);

        creativeModeTabRegister.register("general", () -> ModCreativeModeTabs.TAB_LASER_BLADE);

        creativeModeTabRegister.register(modBusGroup);
    }

    private static void registerDataComponentTypes(BusGroup modBusGroup) {
        var dataComponentTypeRegister = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ToLaserBlade.MOD_ID);

        dataComponentTypeRegister.register("lb_atk", () -> ModDataComponents.LASER_BLADE_ATTACK);
        dataComponentTypeRegister.register("lb_spd", () -> ModDataComponents.LASER_BLADE_SPEED);
        dataComponentTypeRegister.register("lb_mdl", () -> ModDataComponents.LASER_BLADE_MODEL);

        dataComponentTypeRegister.register(modBusGroup);
    }

    private static void registerDataComponentPredicateTypes(BusGroup modBusGroup) {
        var dataComponentPredicateTypeRegister = DeferredRegister.create(Registries.DATA_COMPONENT_PREDICATE_TYPE, ToLaserBlade.MOD_ID);

        dataComponentPredicateTypeRegister.register("lb_atk", () -> ModDataComponentPredicates.LASER_BLADE_ATTACK);
        dataComponentPredicateTypeRegister.register("lb_spd", () -> ModDataComponentPredicates.LASER_BLADE_SPEED);

        dataComponentPredicateTypeRegister.register(modBusGroup);
    }
}
