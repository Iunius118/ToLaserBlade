package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL14;

import java.util.function.BiFunction;

public class LaserBladeRenderType {
    public static RenderType getHiltRenderType(String name, ResourceLocation texture) {
        return TRANSLUCENT.apply(name, texture);
    }

    public static RenderType getUnlitRenderType(String name, ResourceLocation texture) {
        return UNLIT_TRANSLUCENT.apply(name, texture);
    }

    public static RenderType getAddRenderType(String name, ResourceLocation texture) {
        return ADD.apply(name, texture);
    }

    public static RenderType getSubRenderType(String name, ResourceLocation texture) {
        return SUB.apply(name, texture);
    }

    private static final BiFunction<String, ResourceLocation, RenderType> TRANSLUCENT = Util.memoize(
            (name, resourceLocation) -> {
                RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false))
                        .setLightmapState(RenderType.LIGHTMAP)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create(name, 1536, true, false, LaserBladePipelines.TRANSLUCENT, compositeState);
            }
    );
    private static final BiFunction<String, ResourceLocation, RenderType> UNLIT_TRANSLUCENT = Util.memoize(
            (name, resourceLocation) -> {
                RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false))
                        .setLightmapState(RenderType.LIGHTMAP)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create(name, 1536, true, false, LaserBladePipelines.UNLIT_TRANSLUCENT, compositeState);
            }
    );
    private static final BiFunction<String, ResourceLocation, RenderType> ADD = Util.memoize(
            (name, resourceLocation) -> {
                RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false))
                        .setLightmapState(RenderType.LIGHTMAP)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create(name, 1536, true, false, LaserBladePipelines.ADD, compositeState);
            }
    );
    private static final BiFunction<String, ResourceLocation, RenderType> SUB = Util.memoize(
            (name, resourceLocation) -> {
                // Create a custom texturing state with reverse subtract blend equation
                RenderStateShard.TexturingStateShard tlbSubColor = new RenderStateShard.TexturingStateShard(ToLaserBlade.MOD_ID + ":lb_sub_color",
                        () -> GL14.glBlendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT),
                        () -> GL14.glBlendEquation(GL14.GL_FUNC_ADD));
                RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false))
                        .setTexturingState(tlbSubColor)
                        .setLightmapState(RenderType.LIGHTMAP)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create(name, 1536, true, false, LaserBladePipelines.ADD, compositeState);
            }
    );

    private LaserBladeRenderType() {}
}
