package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryEventHandler {
    public static void registerGameObjects(IEventBus modEventBus) {
        registerRecipeSerializers(modEventBus);
        registerItems(modEventBus);
        registerEnchantments(modEventBus);
        registerParticleTypes(modEventBus);
        registerSoundEvents(modEventBus);
        registerCreativeModeTabs(modEventBus);
        registerDataComponentTypes(modEventBus);
    }

    private static void registerRecipeSerializers(IEventBus modEventBus) {
        var recipeSerializerRegister = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ToLaserBlade.MOD_ID);

        recipeSerializerRegister.register("upgrade", () -> ModRecipeSerializers.UPGRADE);
        recipeSerializerRegister.register("color", () -> ModRecipeSerializers.COLOR);
        recipeSerializerRegister.register("model_change", () -> ModRecipeSerializers.MODEL_CHANGE);

        recipeSerializerRegister.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, ToLaserBlade.MOD_ID);

        itemRegister.register("dx_laser_blade", () -> ModItems.DX_LASER_BLADE);
        itemRegister.register("laser_blade", () -> ModItems.LASER_BLADE);
        itemRegister.register("laser_blade_fp", () -> ModItems.LASER_BLADE_FP);
        itemRegister.register("lb_brand_new", () -> ModItems.LB_BRAND_NEW);
        itemRegister.register("lb_brand_new_1", () -> ModItems.LB_BRAND_NEW_1);
        itemRegister.register("lb_brand_new_2", () -> ModItems.LB_BRAND_NEW_2);
        itemRegister.register("lb_brand_new_fp", () -> ModItems.LB_BRAND_NEW_FP);
        itemRegister.register("lb_broken", () -> ModItems.LB_BROKEN);
        itemRegister.register("lb_broken_fp", () -> ModItems.LB_BROKEN_FP);
        itemRegister.register("lb_blueprint", () -> ModItems.LB_BLUEPRINT);
        itemRegister.register("lb_disassembled", () -> ModItems.LB_DISASSEMBLED);
        itemRegister.register("lb_disassembled_fp", () -> ModItems.LB_DISASSEMBLED_FP);
        itemRegister.register("lb_battery", () -> ModItems.LB_BATTERY);
        itemRegister.register("lb_medium", () -> ModItems.LB_MEDIUM);
        itemRegister.register("lb_emitter", () -> ModItems.LB_EMITTER);
        itemRegister.register("lb_casing", () -> ModItems.LB_CASING);
        itemRegister.register("lb_casing_fp", () -> ModItems.LB_CASING_FP);

        itemRegister.register(modEventBus);
    }

    private static void registerEnchantments(IEventBus modEventBus) {
        var enchantmentRegister = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ToLaserBlade.MOD_ID);

        enchantmentRegister.register(LightElementEnchantment.ID.getPath(), () -> ModEnchantments.LIGHT_ELEMENT);

        enchantmentRegister.register(modEventBus);
    }

    private static void registerParticleTypes(IEventBus modEventBus) {
        var particleTypeRegister = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ToLaserBlade.MOD_ID);

        particleTypeRegister.register("laser_trap_x", () -> ModParticleTypes.LASER_TRAP_X);
        particleTypeRegister.register("laser_trap_y", () -> ModParticleTypes.LASER_TRAP_Y);
        particleTypeRegister.register("laser_trap_z", () -> ModParticleTypes.LASER_TRAP_Z);

        particleTypeRegister.register(modEventBus);
    }

    private static void registerSoundEvents(IEventBus modEventBus) {
        var soundEventRegister = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ToLaserBlade.MOD_ID);

        soundEventRegister.register("item_dx_laser_blade_swing", () -> ModSoundEvents.ITEM_DX_LASER_BLADE_SWING);
        soundEventRegister.register("item_laser_blade_swing", () -> ModSoundEvents.ITEM_LASER_BLADE_SWING);
        soundEventRegister.register("item_laser_blade_fp_swing", () -> ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);

        soundEventRegister.register(modEventBus);
    }

    private static void registerCreativeModeTabs(IEventBus modEventBus) {
        var creativeModeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToLaserBlade.MOD_ID);

        creativeModeTabRegister.register("general", () -> ModCreativeModeTabs.TAB_LASER_BLADE);

        creativeModeTabRegister.register(modEventBus);
    }

    private static void registerDataComponentTypes(IEventBus modEventBus) {
        var dataComponentTypeRegister = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ToLaserBlade.MOD_ID);

        dataComponentTypeRegister.register("lb_atk", () -> ModDataComponents.LASER_BLADE_ATTACK);
        dataComponentTypeRegister.register("lb_spd", () -> ModDataComponents.LASER_BLADE_SPEED);
        dataComponentTypeRegister.register("lb_mdl", () -> ModDataComponents.LASER_BLADE_MODEL);

        dataComponentTypeRegister.register(modEventBus);
    }
}
