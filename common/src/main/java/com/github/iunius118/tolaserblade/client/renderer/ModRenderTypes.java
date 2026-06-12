package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.Constants;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class ModRenderTypes {
    private static final BiFunction<String, Identifier, RenderType> TRANSLUCENT = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.TRANSLUCENT)
                        .withTexture("Sampler0", identifier)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .useLightmap()
                        .useOverlay()
                        .sortOnUpload()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> UNLIT_TRANSLUCENT = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.UNLIT_TRANSLUCENT)
                        .withTexture("Sampler0", identifier)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .sortOnUpload()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> ADD = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.ADD)
                        .withTexture("Sampler0", identifier)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .sortOnUpload()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> SUB = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.SUB)
                        .withTexture("Sampler0", identifier)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .sortOnUpload()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );

    public static RenderType hilt(Identifier texture) {
        return TRANSLUCENT.apply(Constants.MOD_ID + ":lb_hilt", texture);
    }

    public static RenderType unlit(Identifier texture) {
        return UNLIT_TRANSLUCENT.apply(Constants.MOD_ID + ":lb_unlit", texture);
    }

    public static RenderType additive(Identifier texture) {
        return ADD.apply(Constants.MOD_ID + ":lb_add", texture);
    }

    public static RenderType subtractive(Identifier texture) {
        return SUB.apply(Constants.MOD_ID + ":lb_sub", texture);
    }

    private ModRenderTypes() {}
}
