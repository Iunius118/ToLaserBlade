package com.github.iunius118.tolaserblade.config;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TLBClientConfig {
    private static final ModConfigSpec.Builder BUILDER;
    private static final ModConfigSpec.BooleanValue SHOW_UPDATE_MESSAGE;
    private static final ModConfigSpec.BooleanValue USE_ORIGINAL_MODEL_TYPE;
    private static final ModConfigSpec.BooleanValue RENDER_MULTIPLE_MODELS;
    private static final ModConfigSpec.IntValue DEFAULT_MODEL;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER = new ModConfigSpec.Builder()
                .comment("ToLaserBlade's client side settings.").push("client");

        SHOW_UPDATE_MESSAGE = BUILDER
                .comment("Enable to show update message.")
                .translation("tolaserblade.configgui.client.showUpdateMessage")
                .define("showUpdateMessage", true);
        USE_ORIGINAL_MODEL_TYPE = BUILDER
                .comment("""
                        Using mod model loader to load Laser Blade models.
                        Set to false to use the model that loaded by vanilla model loader.
                        Need to reload resource packs after changing this.""")
                .translation("tolaserblade.configgui.client.useOriginalModelType")
                .define("useOriginalModelType", true);
        RENDER_MULTIPLE_MODELS = BUILDER
                .comment("""
                        Enable to render Laser Blades using multiple models. Set to false to use the model of defaultModelType.
                        This setting is valid when useOriginalModelLoader is true.
                        Need to reload resource packs after changing this.""")
                .translation("tolaserblade.configgui.client.renderMultipleModels")
                .define("renderMultipleModels", true);
        DEFAULT_MODEL = BUILDER
                .comment("""
                        A non-negative integer representing the model number of laser blades to use when renderMultipleModels is false.
                        This setting is valid when useOriginalModelType is true.
                        Need to reload resource packs after changing this.""")
                .translation("tolaserblade.configgui.client.defaultModel")
                .defineInRange("defaultModel", 0, 0, Integer.MAX_VALUE);

        SPEC = BUILDER.pop().build();
    }

    public static boolean showUpdateMessage = SHOW_UPDATE_MESSAGE.getDefault();
    public static boolean useOriginalModelType = USE_ORIGINAL_MODEL_TYPE.getDefault();
    public static boolean renderMultipleModels = RENDER_MULTIPLE_MODELS.getDefault();
    public static int defaultModel = DEFAULT_MODEL.getDefault();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        showUpdateMessage = SHOW_UPDATE_MESSAGE.get();
        useOriginalModelType = USE_ORIGINAL_MODEL_TYPE.get();
        renderMultipleModels = RENDER_MULTIPLE_MODELS.get();
        defaultModel = DEFAULT_MODEL.get();
    }
}
