package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.network.ServerConfigMessage;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;


public class ToLaserBladeConfig {
    public static class Common {
        public final BooleanValue isEnabledBlockingWithLaserBlade;
        public Supplier<Boolean> isEnabledBlockingWithLaserBladeInServer;

        public final IntValue laserBladeEfficiency;
        public Supplier<Integer> laserBladeEfficiencyInServer;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("ToLaserBlade's common settings.").push("common");

            isEnabledBlockingWithLaserBlade = builder
                    .comment("Enable blocking with Laser Blade.")
                    .translation("tolaserblade.configgui.enableBlockingWithLaserBlade")
                    .define("enableBlockingWithLaserBlade", false);

            laserBladeEfficiency = builder
                    .comment("An integer value (0-128) that is a factor of mining speed of Laser Blade.")
                    .translation("tolaserblade.configgui.laserBladeEfficiencyInServer")
                    .defineInRange("laserBladeEfficiencyInServer", 12, 0, 128);

            builder.pop();
        }
    }

    public static class Client {
        public final BooleanValue isEnabledLaserBlade3DModel;
        public final IntValue laserBladeRenderingMode;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("ToLaserBlade's client side settings.").push("client");

            isEnabledLaserBlade3DModel = builder
                    .comment("Enable Laser Blade to use 3D model.")
                    .translation("tolaserblade.configgui.enableLaserBlade3DModel")
                    .define("enableLaserBlade3DModel", true);

            laserBladeRenderingMode = builder
                    .comment("Select rendering mode of Laser Blade (0: Default, 1: Using only alpha blending). This option is available when enableLaserBlade3DModel is true.")
                    .translation("tolaserblade.configgui.laserBladeRenderingMode")
                    .defineInRange("laserBladeRenderingMode", 0, 0, 1);

            builder.pop();
        }
    }

    static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();

        COMMON.isEnabledBlockingWithLaserBladeInServer = COMMON.isEnabledBlockingWithLaserBlade::get;
        COMMON.laserBladeEfficiencyInServer = COMMON.laserBladeEfficiency::get;
    }

    static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class ServerConfig {
        public boolean isEnabledBlockingWithLaserBladeInServer;
        public int laserBladeEfficiencyInServer;
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        if (configEvent.getConfig().getType() == ModConfig.Type.COMMON
                && ServerLifecycleHooks.getCurrentServer() != null) {
            // Send server-side settings to clients
            ServerConfig serverConfig = new ServerConfig();
            serverConfig.isEnabledBlockingWithLaserBladeInServer = COMMON.isEnabledBlockingWithLaserBlade.get();
            serverConfig.laserBladeEfficiencyInServer = COMMON.laserBladeEfficiency.get();

            ToLaserBlade.NETWORK_HANDLER.getConfigChannel().send(PacketDistributor.ALL.noArg(), new ServerConfigMessage(serverConfig));
        }
    }
}
