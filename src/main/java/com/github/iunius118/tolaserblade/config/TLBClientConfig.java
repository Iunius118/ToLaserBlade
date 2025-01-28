package com.github.iunius118.tolaserblade.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class TLBClientConfig {
    private static final ForgeConfigSpec.Builder BUILDER;
    private static final ForgeConfigSpec.BooleanValue SHOW_UPDATE_MESSAGE;
    private static final ForgeConfigSpec.BooleanValue USE_FIXED_VERTEX_BUFFER;
    private static final ForgeConfigSpec.BooleanValue USE_ORIGINAL_MODEL_TYPE;
    private static final ForgeConfigSpec.BooleanValue RENDER_MULTIPLE_MODELS;
    private static final ForgeConfigSpec.IntValue DEFAULT_MODEL;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER = new ForgeConfigSpec.Builder()
                .comment("ToLaserBlade's client side settings.").push("client");

        SHOW_UPDATE_MESSAGE = BUILDER
                .comment("Enable to show update message.")
                .translation("tolaserblade.configgui.client.showUpdateMessage")
                .define("showUpdateMessage", true);
        USE_FIXED_VERTEX_BUFFER = BUILDER
                .comment("""
                        Add mod's vertex buffers to Minecraft's fixed buffer list.
                        Need to restart client after changing this.""")
                .translation("tolaserblade.configgui.client.useFixedVertexBuffer")
                .define("useFixedVertexBuffer", false);
        USE_ORIGINAL_MODEL_TYPE = BUILDER
                .comment("""
                        Using mod model loader to load Laser Blade models.
                        Set to false to use the model that loaded by vanilla model loader.
                        Need to reload resource packs after changing this.""")
                .translation("tolaserblade.configgui.client.useOriginalModelType")
                .define("useInternalModel", true);
        RENDER_MULTIPLE_MODELS = BUILDER
                .comment("""
                        Enable to render Laser Blades using multiple models.
                        This setting is valid when useOriginalModelLoader is true.
                        Set to false to use the model of defaultModelType.
                        Need to reload resource packs after changing this.""")
                .translation("tolaserblade.configgui.client.renderMultipleModels")
                .define("renderMultipleModels", true);
        DEFAULT_MODEL = BUILDER
                .comment("""
                        Select model number of laser blade to use when renderMultipleModels is false.
                        This setting is valid when useOriginalModelLoader is true.
                        Need to reload resource packs after changing this.
                        Default: 0""")
                .translation("tolaserblade.configgui.client.defaultModel")
                .defineInRange("defaultModel", 0, 0, Integer.MAX_VALUE);

        SPEC = BUILDER.pop().build();
    }

    public static boolean showUpdateMessage = SHOW_UPDATE_MESSAGE.getDefault();
    public static boolean useFixedVertexBuffer = USE_FIXED_VERTEX_BUFFER.getDefault();
    public static boolean useOriginalModelType = USE_ORIGINAL_MODEL_TYPE.getDefault();
    public static boolean renderMultipleModels = RENDER_MULTIPLE_MODELS.getDefault();
    public static int defaultModel = DEFAULT_MODEL.getDefault();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        showUpdateMessage = SHOW_UPDATE_MESSAGE.get();
        useFixedVertexBuffer = USE_FIXED_VERTEX_BUFFER.get();
        useOriginalModelType = USE_ORIGINAL_MODEL_TYPE.get();
        renderMultipleModels = RENDER_MULTIPLE_MODELS.get();
        defaultModel = DEFAULT_MODEL.get();
    }
}
