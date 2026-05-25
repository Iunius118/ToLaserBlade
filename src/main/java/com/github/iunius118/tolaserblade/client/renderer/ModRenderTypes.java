package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class ModRenderTypes {
    public static RenderType hilt(Identifier texture) {
        return TRANSLUCENT.apply(ToLaserBlade.MOD_ID + ":lb_hilt", texture);
    }

    public static RenderType unlit(Identifier texture) {
        return UNLIT_TRANSLUCENT.apply(ToLaserBlade.MOD_ID + ":lb_unlit", texture);
    }

    public static RenderType additive(Identifier texture) {
        return ADD.apply(ToLaserBlade.MOD_ID + ":lb_add", texture);
    }

    public static RenderType subtractive(Identifier texture) {
        return SUB.apply(ToLaserBlade.MOD_ID + ":lb_sub", texture);
    }

    private static final BiFunction<String, Identifier, RenderType> TRANSLUCENT = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.TRANSLUCENT)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> UNLIT_TRANSLUCENT = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.UNLIT_TRANSLUCENT)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> ADD = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.ADD)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> SUB = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(ModPipelines.SUB)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );

    private ModRenderTypes() {}
}
