package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class RegistryEventHandler {
    @SubscribeEvent
    public static void registerRecipeSerializers(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS))
            return;

        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, new ResourceLocation(ToLaserBlade.MOD_ID, "upgrade"), ()-> ModRecipeSerializers.UPGRADE);
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, new ResourceLocation(ToLaserBlade.MOD_ID, "color"), ()-> ModRecipeSerializers.COLOR);
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, new ResourceLocation(ToLaserBlade.MOD_ID, "model_change"), ()-> ModRecipeSerializers.MODEL_CHANGE);

    }

    @SubscribeEvent
    public static void registerItems(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS))
            return;

        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "dx_laser_blade"), () -> ModItems.DX_LASER_BLADE);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade"), ()-> ModItems.LASER_BLADE);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_fp"), ()-> ModItems.LASER_BLADE_FP);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_brand_new"), ()-> ModItems.LB_BRAND_NEW);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_brand_new_1"), ()-> ModItems.LB_BRAND_NEW_1);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_brand_new_2"), ()-> ModItems.LB_BRAND_NEW_2);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_brand_new_fp"), ()-> ModItems.LB_BRAND_NEW_FP);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_broken"), ()-> ModItems.LB_BROKEN);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_broken_fp"), ()-> ModItems.LB_BROKEN_FP);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_blueprint"), ()-> ModItems.LB_BLUEPRINT);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_disassembled"), ()-> ModItems.LB_DISASSEMBLED);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_disassembled_fp"), ()-> ModItems.LB_DISASSEMBLED_FP);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_battery"), ()-> ModItems.LB_BATTERY);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_medium"), ()-> ModItems.LB_MEDIUM);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_emitter"), ()-> ModItems.LB_EMITTER);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_casing"), ()-> ModItems.LB_CASING);
        event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_casing_fp"), ()-> ModItems.LB_CASING_FP);
    }

    @SubscribeEvent
    public static void registerEnchantments(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.ENCHANTMENTS))
            return;

        event.register(ForgeRegistries.Keys.ENCHANTMENTS, LightElementEnchantment.ID, () -> ModEnchantments.LIGHT_ELEMENT);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.PARTICLE_TYPES))
            return;

        event.register(ForgeRegistries.Keys.PARTICLE_TYPES, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap_x"), ()->  ModParticleTypes.LASER_TRAP_X);
        event.register(ForgeRegistries.Keys.PARTICLE_TYPES, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap_y"), ()-> ModParticleTypes.LASER_TRAP_Y);
        event.register(ForgeRegistries.Keys.PARTICLE_TYPES, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap_z"), ()-> ModParticleTypes.LASER_TRAP_Z);
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.SOUND_EVENTS))
            return;

        event.register(ForgeRegistries.Keys.SOUND_EVENTS, new ResourceLocation(ToLaserBlade.MOD_ID, "item_dx_laser_blade_swing"), ()-> ModSoundEvents.ITEM_DX_LASER_BLADE_SWING);
        event.register(ForgeRegistries.Keys.SOUND_EVENTS, new ResourceLocation(ToLaserBlade.MOD_ID, "item_laser_blade_swing"), ()-> ModSoundEvents.ITEM_LASER_BLADE_SWING);
        event.register(ForgeRegistries.Keys.SOUND_EVENTS, new ResourceLocation(ToLaserBlade.MOD_ID, "item_laser_blade_fp_swing"), ()-> ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);
    }

    // Generate Data
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ForgeBlockTagsProvider blockTags = new ForgeBlockTagsProvider(gen, existingFileHelper);

        // Server
        // Recipes
        gen.addProvider(event.includeServer(), new TLBRecipeProvider(gen));
        // Item tags
        gen.addProvider(event.includeServer(), new TLBItemTagsProvider(gen, blockTags, existingFileHelper));
        // Advancements
        gen.addProvider(event.includeServer(), new TLBAdvancementProvider(gen));

        // Client
        // Item models
        gen.addProvider(event.includeClient(), new TLBItemModelProvider(gen, existingFileHelper));
        // Languages
        TLBLanguageProvider.addProviders(event.includeClient(), gen);
        // Sounds
        gen.addProvider(event.includeClient(), new TLBSoundProvider(gen));
    }
}
