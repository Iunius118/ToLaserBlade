package com.github.iunius118.tolaserblade.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ToLaserBladeConfig {
    public static class Server {
        public final ModConfigSpec.BooleanValue isEnabledBlockingWithLaserBlade;
        public final ModConfigSpec.IntValue laserBladeEfficiency;
        public final ModConfigSpec.IntValue maxAttackDamageUpgradeCount;
        public final ModConfigSpec.BooleanValue isEnabledLaserTrap;
        public final ModConfigSpec.BooleanValue canLaserTrapAttackPlayer;
        public final ModConfigSpec.BooleanValue canLaserTrapHeatUpFurnace;

        Server(ModConfigSpec.Builder builder) {
            builder.comment("ToLaserBlade's game server side settings.").push("server");

            isEnabledBlockingWithLaserBlade = builder
                    .comment("Enable blocking with Laser Blade.\n" +
                            "Default: false")
                    .translation("tolaserblade.configgui.server.enableBlockingWithLaserBlade")
                    .define("enableBlockingWithLaserBlade", false);

            laserBladeEfficiency = builder
                    .comment("An integer value (0-128) that is a factor of mining speed of Laser Blade.\n" +
                            "Default: 12")
                    .translation("tolaserblade.configgui.server.laserBladeEfficiency")
                    .defineInRange("laserBladeEfficiency", 12, 0, 128);

            maxAttackDamageUpgradeCount = builder
                    .comment("An integer value (0-39) that is maximum count of attack damage upgrade of Laser Blade.\n" +
                            "Note:\n" +
                            "  Making the advancement [It's Over 9] requires 3 or more.\n" +
                            "  Similarly, [Beyond the Limit] requires 8 or more.\n" +
                            "Default: 8")
                    .translation("tolaserblade.configgui.server.maxAttackDamageUpgradeCount")
                    .defineInRange("maxAttackDamageUpgradeCount", 8, 0, 39);

            isEnabledLaserTrap = builder
                    .comment("Enable to attack with Laser Blade in Dispenser when the dispenser is activated.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.server.enableLaserTrap")
                    .define("enableLaserTrap", true);

            canLaserTrapAttackPlayer = builder
                    .comment("A boolean value represents whether laser trap can attack player or not. This setting is valid when enableLaserTrap is true.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.server.canLaserTrapAttackPlayer")
                    .define("canLaserTrapAttackPlayer", true);

            canLaserTrapHeatUpFurnace = builder
                    .comment("A boolean value represents whether laser trap with fireproof Laser Blade can heat up furnace or not. This setting is valid when enableLaserTrap is true.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.server.canLaserTrapHeatUpFurnace")
                    .define("canLaserTrapHeatUpFurnace", true);

            builder.pop();
        }
    }

    public static class Client {
        public final ModConfigSpec.BooleanValue showUpdateMessage;
        public final ModConfigSpec.BooleanValue useFixedVertexBuffer;
        public final ModConfigSpec.BooleanValue useOriginalModelType;
        public final ModConfigSpec.BooleanValue renderMultipleModels;
        public final ModConfigSpec.IntValue defaultModel;

        Client(ModConfigSpec.Builder builder) {
            builder.comment("ToLaserBlade's client side settings.").push("client");

            showUpdateMessage = builder
                    .comment("Enable to show update message.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.client.showUpdateMessage")
                    .define("showUpdateMessage", true);

            useFixedVertexBuffer = builder
                    .comment("Add mod's vertex buffers to Minecraft's fixed buffer list.\n" +
                            "Need to restart client after changing this.\n" +
                            "Default: false")
                    .translation("tolaserblade.configgui.client.useFixedVertexBuffer")
                    .define("useFixedVertexBuffer", false);

            useOriginalModelType = builder
                    .comment("Using mod model loader to load Laser Blade models. Set to false to use the model that loaded by vanilla model loader.\n" +
                            "Need to reload resource packs after changing this.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.client.useOriginalModelType")
                    .define("useOriginalModelType", true);

            renderMultipleModels = builder
                    .comment("Enable to render Laser Blades using multiple models. This setting is valid when useOriginalModelLoader is true.\n" +
                            "Set to false to use the model of defaultModelType.\n" +
                            "Need to reload resource packs after changing this.\n" +
                            "Default: true")
                    .translation("tolaserblade.configgui.client.renderMultipleModels")
                    .define("renderMultipleModels", true);

            defaultModel = builder
                    .comment("Select model number of laser blade to use when renderMultipleModels is false. This setting is valid when useOriginalModelType is true.\n" +
                            "Need to reload resource packs after changing this.\n" +
                            "Default: 0")
                    .translation("tolaserblade.configgui.client.defaultModel")
                    .defineInRange("defaultModel", 0, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    public static final ModConfigSpec serverSpec;
    public static final Server SERVER;
    static {
        final Pair<Server, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static final ModConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
}
