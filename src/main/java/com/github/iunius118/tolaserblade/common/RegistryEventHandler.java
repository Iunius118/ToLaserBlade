package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.github.iunius118.tolaserblade.item.*;
import com.github.iunius118.tolaserblade.item.crafting.ColorRecipe;
import com.github.iunius118.tolaserblade.item.crafting.ModelChangeRecipe;
import com.github.iunius118.tolaserblade.item.crafting.UpgradeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class RegistryEventHandler {
    // Register recipe Serializers
    @SubscribeEvent
    public static void onRecipeSerializerRegistry(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
                new UpgradeRecipe.Serializer().setRegistryName("tolaserblade:upgrade"),
                new ColorRecipe.Serializer().setRegistryName("tolaserblade:color"),
                new ModelChangeRecipe.Serializer().setRegistryName("tolaserblade:model_change")
        );
    }

    // Register items
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new DXLaserBladeItem().setRegistryName("dx_laser_blade"),
                new LaserBladeItem(false).setRegistryName("laser_blade"),
                new LaserBladeItem(true).setRegistryName("laser_blade_fp"), // Fireproof
                new LBBrandNewItem(LBBrandNewItem.Type.NONE, false).setRegistryName("lb_brand_new"),
                new LBBrandNewItem(LBBrandNewItem.Type.LIGHT_ELEMENT_1, false).setRegistryName("lb_brand_new_1"),
                new LBBrandNewItem(LBBrandNewItem.Type.LIGHT_ELEMENT_2, false).setRegistryName("lb_brand_new_2"),
                new LBBrandNewItem(LBBrandNewItem.Type.FP, true).setRegistryName("lb_brand_new_fp"),
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

    // Register Entity Types
    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> event) {
        EntityType<LaserTrapEntity> laserTrap = EntityType.Builder
                .<LaserTrapEntity>of(LaserTrapEntity::new, EntityClassification.MISC)
                .sized(1.0F, 1.0F).fireImmune()
                .setTrackingRange(64).setUpdateInterval(4).setShouldReceiveVelocityUpdates(false)
                .build(LaserTrapEntity.ID.toString());

        ToLaserBlade.LOGGER.info("[ToLaserBlade] This warning of data fixer is not an issue for modded entities");

        event.getRegistry().registerAll(
                laserTrap.setRegistryName(LaserTrapEntity.ID)
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
