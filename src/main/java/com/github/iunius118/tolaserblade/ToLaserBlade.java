package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.client.ClientEventHandler;
import com.github.iunius118.tolaserblade.client.renderer.entity.LaserTrapEntityRenderer;
import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.github.iunius118.tolaserblade.entity.ModEntities;
import com.github.iunius118.tolaserblade.item.*;
import com.github.iunius118.tolaserblade.item.crafting.ColorRecipe;
import com.github.iunius118.tolaserblade.item.crafting.UpgradeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(ToLaserBlade.MOD_ID)
@EventBusSubscriber
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean hasShownUpdate = false;

    public ToLaserBlade() {
        // Register lifecycle event listeners
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::initClient);
        modEventBus.register(ToLaserBladeConfig.class);

        // Register config handlers
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ToLaserBladeConfig.serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ToLaserBladeConfig.clientSpec);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            modEventBus.register(new ClientEventHandler());
        }
    }

    private void initClient(final FMLClientSetupEvent event) {
        // Register laser trap entity renderer
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LASER_TRAP, LaserTrapEntityRenderer::new);
    }

    /*
    * Registry Events
    */

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        // Register recipe Serializers
        @SubscribeEvent
        public static void onRecipeSerializerRegistry(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            event.getRegistry().registerAll(
                    new UpgradeRecipe.Serializer().setRegistryName("tolaserblade:upgrade"),
                    new ColorRecipe.Serializer().setRegistryName("tolaserblade:color")
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
                    .<LaserTrapEntity>create(LaserTrapEntity::new, EntityClassification.MISC)
                    .size(1.0F, 1.0F).immuneToFire()
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
                    new SoundEvent(new ResourceLocation(MOD_ID, "item.dx_laser_blade.swing")).setRegistryName("item_dx_laser_blade_swing"),
                    new SoundEvent(new ResourceLocation(MOD_ID, "item.laser_blade.swing")).setRegistryName("item_laser_blade_swing"),
                    new SoundEvent(new ResourceLocation(MOD_ID, "item.laser_blade_fp.swing")).setRegistryName("item_laser_blade_fp_swing")
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

    /*
     * Remapping Items
     */

    @SubscribeEvent
    public static void remapItems(RegistryEvent.MissingMappings<Item> mappings) {
        final Map<ResourceLocation, Item> remappingItemMap = new HashMap<>();
        // Replace item ID "tolaserblade:tolaserblade.laser_blade" (-1.11.2) with "tolaserblade:laser_blade" (1.12-)
        remappingItemMap.put(new ResourceLocation(MOD_ID, "tolaserblade.laser_blade"), ModItems.LASER_BLADE);

        // Replace item ID "tolaserblade:lasar_blade" (-1.14.4) with "tolaserblade:dx_laser_blade" (1.15-)
        remappingItemMap.put(new ResourceLocation(MOD_ID, "lasar_blade"), ModItems.DX_LASER_BLADE);

        // Replace item ID "tolaserblade:laser_blade_core" (-1.14.4) with "tolaserblade:lb_broken" (1.15-)
        remappingItemMap.put(new ResourceLocation(MOD_ID, "laser_blade_core"), ModItems.LB_BROKEN);

        // Replace item IDs
        mappings.getAllMappings().stream()
                .filter(mapping -> mapping.key.getNamespace().equals(MOD_ID) && remappingItemMap.containsKey(mapping.key))
                .forEach(mapping -> mapping.remap(remappingItemMap.get(mapping.key)));
    }

    /*
     * World Events
     */

    @SubscribeEvent
    public void onEntityJoiningInWorld(final EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote && event.getEntity() instanceof PlayerEntity) {
            if (ToLaserBladeConfig.CLIENT.showUpdateMessage.get() && !hasShownUpdate) {
                ClientEventHandler.checkUpdate();
                hasShownUpdate = true;
            }
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        /*
        // For debug
        String str = event.getSource().getDamageType() + " caused " + event.getAmount() + " point damage to " + event.getEntityLiving().getName().getString() + "!";
        if (FMLLoader.getDist().isClient()) {
            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(str));
        } else {
            LOGGER.info(str);
        }
        // */
    }
}
