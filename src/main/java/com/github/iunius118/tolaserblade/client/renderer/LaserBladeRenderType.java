package com.github.iunius118.tolaserblade.client.renderer;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class LaserBladeRenderType {
    public static RenderType getHiltRenderType(String name, Identifier texture) {
        return TRANSLUCENT.apply(name, texture);
    }

    public static RenderType getUnlitRenderType(String name, Identifier texture) {
        return UNLIT_TRANSLUCENT.apply(name, texture);
    }

    public static RenderType getAddRenderType(String name, Identifier texture) {
        return ADD.apply(name, texture);
    }

    public static RenderType getSubRenderType(String name, Identifier texture) {
        return SUB.apply(name, texture);
    }

    private static final BiFunction<String, Identifier, RenderType> TRANSLUCENT = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(LaserBladePipelines.TRANSLUCENT)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> UNLIT_TRANSLUCENT = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(LaserBladePipelines.UNLIT_TRANSLUCENT)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> ADD = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(LaserBladePipelines.ADD)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );
    private static final BiFunction<String, Identifier, RenderType> SUB = Util.memoize(
            (name, identifier) -> {
                RenderSetup renderSetup = RenderSetup.builder(LaserBladePipelines.SUB)
                        .withTexture("Sampler0", identifier)
                        .useLightmap()
                        .useOverlay()
                        .createRenderSetup();
                return RenderType.create(name, renderSetup);
            }
    );

    private LaserBladeRenderType() {}
}
