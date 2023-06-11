package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
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
    }

    private static void registerRecipeSerializers(IEventBus modEventBus) {
        var recipeSerializerDeferredRegister = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ToLaserBlade.MOD_ID);

        recipeSerializerDeferredRegister.register("upgrade", () -> ModRecipeSerializers.UPGRADE);
        recipeSerializerDeferredRegister.register("color", () -> ModRecipeSerializers.COLOR);
        recipeSerializerDeferredRegister.register("model_change", () -> ModRecipeSerializers.MODEL_CHANGE);

        recipeSerializerDeferredRegister.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var itemDeferredRegister = DeferredRegister.create(ForgeRegistries.ITEMS, ToLaserBlade.MOD_ID);

        itemDeferredRegister.register("dx_laser_blade", () -> ModItems.DX_LASER_BLADE);
        itemDeferredRegister.register("laser_blade", () -> ModItems.LASER_BLADE);
        itemDeferredRegister.register("laser_blade_fp", () -> ModItems.LASER_BLADE_FP);
        itemDeferredRegister.register("lb_brand_new", () -> ModItems.LB_BRAND_NEW);
        itemDeferredRegister.register("lb_brand_new_1", () -> ModItems.LB_BRAND_NEW_1);
        itemDeferredRegister.register("lb_brand_new_2", () -> ModItems.LB_BRAND_NEW_2);
        itemDeferredRegister.register("lb_brand_new_fp", () -> ModItems.LB_BRAND_NEW_FP);
        itemDeferredRegister.register("lb_broken", () -> ModItems.LB_BROKEN);
        itemDeferredRegister.register("lb_broken_fp", () -> ModItems.LB_BROKEN_FP);
        itemDeferredRegister.register("lb_blueprint", () -> ModItems.LB_BLUEPRINT);
        itemDeferredRegister.register("lb_disassembled", () -> ModItems.LB_DISASSEMBLED);
        itemDeferredRegister.register("lb_disassembled_fp", () -> ModItems.LB_DISASSEMBLED_FP);
        itemDeferredRegister.register("lb_battery", () -> ModItems.LB_BATTERY);
        itemDeferredRegister.register("lb_medium", () -> ModItems.LB_MEDIUM);
        itemDeferredRegister.register("lb_emitter", () -> ModItems.LB_EMITTER);
        itemDeferredRegister.register("lb_casing", () -> ModItems.LB_CASING);
        itemDeferredRegister.register("lb_casing_fp", () -> ModItems.LB_CASING_FP);

        itemDeferredRegister.register(modEventBus);
    }

    private static void registerEnchantments(IEventBus modEventBus) {
        var enchantmentDeferredRegister = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ToLaserBlade.MOD_ID);

        enchantmentDeferredRegister.register(LightElementEnchantment.ID.getPath(), () -> ModEnchantments.LIGHT_ELEMENT);

        enchantmentDeferredRegister.register(modEventBus);
    }

    private static void registerParticleTypes(IEventBus modEventBus) {
        var particleTypeDeferredRegister = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ToLaserBlade.MOD_ID);

        particleTypeDeferredRegister.register("laser_trap_x", () -> ModParticleTypes.LASER_TRAP_X);
        particleTypeDeferredRegister.register("laser_trap_y", () -> ModParticleTypes.LASER_TRAP_Y);
        particleTypeDeferredRegister.register("laser_trap_z", () -> ModParticleTypes.LASER_TRAP_Z);

        particleTypeDeferredRegister.register(modEventBus);
    }

    private static void registerSoundEvents(IEventBus modEventBus) {
        var soundEventDeferredRegister = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ToLaserBlade.MOD_ID);

        soundEventDeferredRegister.register("item_dx_laser_blade_swing", () -> ModSoundEvents.ITEM_DX_LASER_BLADE_SWING);
        soundEventDeferredRegister.register("item_laser_blade_swing", () -> ModSoundEvents.ITEM_LASER_BLADE_SWING);
        soundEventDeferredRegister.register("item_laser_blade_fp_swing", () -> ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);

        soundEventDeferredRegister.register(modEventBus);
    }

    private static void registerCreativeModeTabs(IEventBus modEventBus) {
        var creativeModeTabDeferredRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToLaserBlade.MOD_ID);

        creativeModeTabDeferredRegister.register("general", () -> ModCreativeModeTabs.TAB_LASER_BLADE);

        creativeModeTabDeferredRegister.register(modEventBus);
    }
}
