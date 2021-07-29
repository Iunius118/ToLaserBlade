package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.world.item.*;
import com.github.iunius118.tolaserblade.world.item.crafting.LBColorRecipe;
import com.github.iunius118.tolaserblade.world.item.crafting.LBModelChangeRecipe;
import com.github.iunius118.tolaserblade.world.item.crafting.LBUpgradeRecipe;
import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class RegistryEventHandler {
    // Register recipe Serializers
    @SubscribeEvent
    public static void onRecipeSerializerRegistry(RegistryEvent.Register<RecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
                new LBUpgradeRecipe.Serializer().setRegistryName("tolaserblade:upgrade"),
                new LBColorRecipe.Serializer().setRegistryName("tolaserblade:color"),
                new LBModelChangeRecipe.Serializer().setRegistryName("tolaserblade:model_change")
        );
    }

    // Register items
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new DXLaserBladeItem().setRegistryName("dx_laser_blade"),
                new LBSwordItem(false).setRegistryName("laser_blade"),
                new LBSwordItem(true).setRegistryName("laser_blade_fp"), // Fireproof
                new LBBrandNewItem(LBBrandNewType.NONE, false).setRegistryName("lb_brand_new"),
                new LBBrandNewItem(LBBrandNewType.LIGHT_ELEMENT_1, false).setRegistryName("lb_brand_new_1"),
                new LBBrandNewItem(LBBrandNewType.LIGHT_ELEMENT_2, false).setRegistryName("lb_brand_new_2"),
                new LBBrandNewItem(LBBrandNewType.FP, true).setRegistryName("lb_brand_new_fp"),
                new LBBrokenItem(false).setRegistryName("lb_broken"),
                new LBBrokenItem(true).setRegistryName("lb_broken_fp"), // Fireproof
                new LBBlueprintItem().setRegistryName("lb_blueprint"),
                new LBDisassembledItem(false).setRegistryName("lb_disassembled"),
                new LBDisassembledItem(true).setRegistryName("lb_disassembled_fp"), // Fireproof
                new LBBatteryItem().setRegistryName("lb_battery"),
                new LBMediumItem().setRegistryName("lb_medium"),
                new LBEmitterItem().setRegistryName("lb_emitter"),
                new LBCasingItem(false).setRegistryName("lb_casing"),
                new LBCasingItem(true).setRegistryName("lb_casing_fp")  // Fireproof
        );
    }

    // Register Enchantments
    @SubscribeEvent
    public static void onEnchantmentRegistry(final RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(
                new LightElementEnchantment().setRegistryName(LightElementEnchantment.ID)
        );
    }

    // Register Particle Types
    @SubscribeEvent
    public static void onParticleTypeRegistry(RegistryEvent.Register<ParticleType<?>> event) {
        event.getRegistry().registerAll(
                new SimpleParticleType(true).setRegistryName("laser_trap_x"),
                new SimpleParticleType(true).setRegistryName("laser_trap_y"),
                new SimpleParticleType(true).setRegistryName("laser_trap_z")
        );
    }

    // Register Sound Events
    @SubscribeEvent
    public static void onSoundEventRegistry(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                new SoundEvent(new ResourceLocation(ToLaserBlade.MOD_ID, "item.dx_laser_blade.swing")).setRegistryName("item_dx_laser_blade_swing"),
                new SoundEvent(new ResourceLocation(ToLaserBlade.MOD_ID, "item.laser_blade.swing")).setRegistryName("item_laser_blade_swing"),
                new SoundEvent(new ResourceLocation(ToLaserBlade.MOD_ID, "item.laser_blade_fp.swing")).setRegistryName("item_laser_blade_fp_swing")
        );
    }

    // Generate Data
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ForgeBlockTagsProvider blockTags = new ForgeBlockTagsProvider(gen, existingFileHelper);

        if (event.includeServer()) {
            gen.addProvider(new TLBRecipeProvider(gen));    // Recipes
            gen.addProvider(new TLBItemTagsProvider(gen, blockTags, existingFileHelper));  // Item tags
            gen.addProvider(new TLBAdvancementProvider(gen));   // Advancements
        }

        if (event.includeClient()) {
            gen.addProvider(new TLBItemModelProvider(gen, existingFileHelper)); // Item models
            TLBLanguageProvider.addProviders(gen);  // Languages
            gen.addProvider(new TLBSoundProvider(gen)); // Sounds
        }
    }
}
