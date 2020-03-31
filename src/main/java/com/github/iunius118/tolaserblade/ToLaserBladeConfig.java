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

        public final IntValue maxAttackDamageUpgradeCount;
        public Supplier<Integer> maxAttackDamageUpgradeCountInServer;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("ToLaserBlade's common settings.").push("common");

            isEnabledBlockingWithLaserBlade = builder
                    .comment("Enable blocking with Laser Blade.\n" +
                            "Default: false")
                    .translation("tolaserblade.configgui.common.enableBlockingWithLaserBlade")
                    .define("enableBlockingWithLaserBlade", false);

            laserBladeEfficiency = builder
                    .comment("An integer value (0-128) that is a factor of mining speed of Laser Blade.\n" +
                            "Default: 12")
                    .translation("tolaserblade.configgui.common.laserBladeEfficiencyInServer")
                    .defineInRange("laserBladeEfficiencyInServer", 12, 0, 128);

            maxAttackDamageUpgradeCount = builder
                    .comment("An integer value (0-39) that is maximum count of attack damage upgrade of Laser Blade.\n" +
                            "Note:\n" +
                            "  Making the advancement [It's Over 9] requires 3 or more.\n" +
                            "  Similarly, [Beyond the Limit] requires 8 or more.\n" +
                            "Default: 8")
                    .translation("tolaserblade.configgui.common.maxAttackDamageUpgradeCount")
                    .defineInRange("maxAttackDamageUpgradeCount", 8, 0, 39);

            builder.pop();
        }
    }

    public static class Client {
        public final BooleanValue isEnabledLaserBlade3DModel;
        public final IntValue laserBladeRenderingMode;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("ToLaserBlade's client side settings.").push("client");

            isEnabledLaserBlade3DModel = builder
                    .comment("Enable Laser Blade to use 3D model.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.client.enableLaserBlade3DModel")
                    .define("enableLaserBlade3DModel", true);

            laserBladeRenderingMode = builder
                    .comment("Select rendering mode of Laser Blade (0-2). This option is available when enableLaserBlade3DModel is true.\n" +
                            "  Mode 0: default. Mode 1: Not using additive blending. Mode 2: Not using additive blending and using another model.\n" +
                            "Default: 0")
                    .translation("tolaserblade.configgui.client.laserBladeRenderingMode")
                    .defineInRange("laserBladeRenderingMode", 0, 0, 2);

            builder.pop();
        }
    }

    static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();

        // Init server-side settings with local common settings
        COMMON.isEnabledBlockingWithLaserBladeInServer = COMMON.isEnabledBlockingWithLaserBlade::get;
        COMMON.laserBladeEfficiencyInServer = COMMON.laserBladeEfficiency::get;
        COMMON.maxAttackDamageUpgradeCountInServer = COMMON.maxAttackDamageUpgradeCount::get;
    }

    static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    // Container for server-side config to make a synchronizing packet
    public static class ServerConfig {
        public boolean isEnabledBlockingWithLaserBladeInServer;
        public int laserBladeEfficiencyInServer;
        public int maxAttackDamageUpgradeCountInServer;

        public ServerConfig() {
            isEnabledBlockingWithLaserBladeInServer = COMMON.isEnabledBlockingWithLaserBlade.get();
            laserBladeEfficiencyInServer = COMMON.laserBladeEfficiency.get();
            maxAttackDamageUpgradeCountInServer = COMMON.maxAttackDamageUpgradeCount.get();
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        if (configEvent.getConfig().getType() == ModConfig.Type.COMMON
                && ServerLifecycleHooks.getCurrentServer() != null) {
            // Send server-side settings to clients
            ServerConfig serverConfig = new ServerConfig();
            ToLaserBlade.NETWORK_HANDLER.getConfigChannel().send(PacketDistributor.ALL.noArg(), new ServerConfigMessage(serverConfig));
        }
    }
}
