package com.github.iunius118.tolaserblade.config;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ToLaserBlade.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TLBClientConfig {
    private static final ForgeConfigSpec.Builder BUILDER;
    private static final ForgeConfigSpec.BooleanValue SHOW_UPDATE_MESSAGE;
    private static final ForgeConfigSpec.BooleanValue USE_ORIGINAL_MODEL_TYPE;
    private static final ForgeConfigSpec.BooleanValue RENDER_MULTIPLE_MODELS;
    private static final ForgeConfigSpec.IntValue DEFAULT_MODEL;
    private static final ForgeConfigSpec.BooleanValue USE_SHIELD_SOUND_FOR_LASER_BLADE;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER = new ForgeConfigSpec.Builder()
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
                        Need to reload resource packs after changing this.
                        Default: 0""")
                .translation("tolaserblade.configgui.client.defaultModel")
                .defineInRange("defaultModel", 0, 0, Integer.MAX_VALUE);
        USE_SHIELD_SOUND_FOR_LASER_BLADE = BUILDER
                .comment("""
                        Play the sound event for shields when a laser blade blocks an attack.
                        This setting is valid when playing in a world where enableBlockingWithLaserBlade in tolaserblade-server.toml is true.
                        Set to false to play the blocking sound events of this mod.""")
                .translation("tolaserblade.configgui.client.useShieldSoundForLaserBlade")
                .define("useShieldSoundForLaserBlade", true);

        SPEC = BUILDER.pop().build();
    }

    public static boolean showUpdateMessage = SHOW_UPDATE_MESSAGE.getDefault();
    public static boolean useOriginalModelType = USE_ORIGINAL_MODEL_TYPE.getDefault();
    public static boolean renderMultipleModels = RENDER_MULTIPLE_MODELS.getDefault();
    public static int defaultModel = DEFAULT_MODEL.getDefault();
    public static boolean useShieldSoundForLaserBlade = USE_SHIELD_SOUND_FOR_LASER_BLADE.getDefault();

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        showUpdateMessage = SHOW_UPDATE_MESSAGE.get();
        useOriginalModelType = USE_ORIGINAL_MODEL_TYPE.get();
        renderMultipleModels = RENDER_MULTIPLE_MODELS.get();
        defaultModel = DEFAULT_MODEL.get();
        useShieldSoundForLaserBlade = USE_SHIELD_SOUND_FOR_LASER_BLADE.get();
    }
}
