package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.client.ClientEventHandler;
import com.github.iunius118.tolaserblade.item.ItemEventHandler;
import com.github.iunius118.tolaserblade.network.NetworkHandler;
import com.github.iunius118.tolaserblade.network.ServerConfigMessage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.Logger;

@Mod(ToLaserBlade.MOD_ID)
@EventBusSubscriber
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static Logger LOGGER;

    public static final String NAME_ITEM_LASER_BLADE = "laser_blade";
    public static final ResourceLocation RL_OBJ_ITEM_LASER_BLADE = new ResourceLocation(MOD_ID, "item/laser_blade.obj");
    public static final ResourceLocation RL_OBJ_ITEM_LASER_BLADE_1 = new ResourceLocation(MOD_ID, "item/laser_blade_1.obj");
    public static final ResourceLocation RL_TEXTURE_ITEM_LASER_BLADE = new ResourceLocation(MOD_ID, "items/laser_blade");

    public static boolean hasShownUpdate = false;

    // Init network channels
    public static final NetworkHandler NETWORK_HANDLER = new NetworkHandler();

    @ObjectHolder(MOD_ID)
    public static class ToLaserBladeItems {
        // Laser B1ade
        public static final Item LASAR_BLADE = null;

        // Laser Blade
        public static final Item LASER_BLADE = null;
        // Laser Blade parts
        public static final Item LASER_BLADE_CORE = null;
        public static final Item LB_OSCILLATOR = null;
        public static final Item LB_BATTERY = null;
        public static final Item LB_MEDIUM = null;
        public static final Item LB_LENS = null;
        public static final Item LB_CASING = null;
        // Workbench
        public static final Item LB_WORKBENCH = null;
    }

    @ObjectHolder(MOD_ID)
    public static class ToLaserBladeBlocks {
        public static final Block LB_WORKBENCH = null;
    }

    public ToLaserBlade() {
        // Register lifecycle event listeners
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(ToLaserBladeConfig.class);

        // Register config handlers
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ToLaserBladeConfig.commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ToLaserBladeConfig.clientSpec);

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            modEventBus.register(new ClientEventHandler());
        }

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {

        }

        @SubscribeEvent
        public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {

        }
    }

    @SubscribeEvent
    public static void remapItems(RegistryEvent.MissingMappings<Item> mappings) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : mappings.getAllMappings()) {
            if (!mapping.key.getNamespace().equals(MOD_ID)) {
                continue;
            }

            String name = mapping.key.getPath();
            if (name.equals("tolaserblade.laser_blade")) {
                // Replace item ID "tolaserblade:tolaserblade.laser_blade" (-1.11.2) with "tolaserblade:laser_blade" (1.12-)
                mapping.remap(ToLaserBladeItems.LASER_BLADE);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ToLaserBladeConfig.ServerConfig serverConfig = new ToLaserBladeConfig.ServerConfig();
        serverConfig.isEnabledBlockingWithLaserBladeInServer = ToLaserBladeConfig.COMMON.isEnabledBlockingWithLaserBlade.get();
        serverConfig.laserBladeEfficiencyInServer = ToLaserBladeConfig.COMMON.laserBladeEfficiency.get();

        NETWORK_HANDLER.getConfigChannel().sendTo(
                new ServerConfigMessage(serverConfig),
                ((ServerPlayerEntity) event.getPlayer()).connection.getNetworkManager(),
                NetworkDirection.PLAY_TO_CLIENT);
    }

    @SubscribeEvent
    public void onEntityJoiningInWorld(final EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote && event.getEntity() instanceof PlayerEntity) {
            if (!hasShownUpdate) {
                ClientEventHandler.checkUpdate();
                hasShownUpdate = true;
            }
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        /*
        // For debug
        String str = event.getSource().getDamageType() + " caused " + event.getAmount() + " point damage to " + event.getEntityLiving().getName().getFormattedText() + "!";
        if (FMLLoader.getDist().isClient()) {
            Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.SYSTEM, new StringTextComponent(str));
        } else {
            LOGGER.info(str);
        }
        // */
    }
}
