package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL14;

import java.lang.reflect.Method;
import java.util.Optional;

public class LaserBladeRenderType extends RenderType {
    private static final RenderStateShard.ShaderStateShard LASER_BLADE_UNLIT_SHADER_STATE = getUnlitShader();

    public LaserBladeRenderType(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    private static RenderStateShard.ShaderStateShard getUnlitShader() {
        // Temporarily set beacon beam shader from vanilla shaders
        return RENDERTYPE_BEACON_BEAM_SHADER;
    }

    public static RenderType.CompositeState getHiltRenderState(ResourceLocation texture) {
        return RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                .setTextureState(new TextureStateShard(texture, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static RenderType.CompositeState getUnlitRenderState(ResourceLocation texture) {
        return RenderType.CompositeState.builder()
                .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                .setTextureState(new TextureStateShard(texture, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static RenderType.CompositeState getAddRenderState(ResourceLocation texture) {
        return RenderType.CompositeState.builder()
                .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                .setTextureState(new TextureStateShard(texture, false, false))
                .setTransparencyState(LIGHTNING_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static RenderType.CompositeState getSubRenderState(ResourceLocation texture) {
        RenderStateShard.TransparencyStateShard transparencyState = new RenderStateShard.TransparencyStateShard("sub_transparency",
                () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                },
                () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
                    RenderSystem.defaultBlendFunc();
                });

        return RenderType.CompositeState.builder()
                .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                .setTextureState(new TextureStateShard(texture, false, false))
                .setTransparencyState(transparencyState)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static Optional<RenderType> getRenderType(String name, RenderType.CompositeState compositeState) {
        if (createRenderTypeMethod != null) {
            try {
                Object object = createRenderTypeMethod.invoke(null, name, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, compositeState);
                if (object instanceof RenderType renderType) return Optional.of(renderType);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                createRenderTypeMethod = null;
            }
        }

        return Optional.empty();
    }

    private static Method createRenderTypeMethod = getCreateRenderTypeMethod();

    @org.jetbrains.annotations.Nullable
    public static Method getCreateRenderTypeMethod() {
        Method method = getCreateMethod();
        if (method != null) method.setAccessible(true);

        ToLaserBlade.LOGGER.info((method != null ? "Succeeded" : "Failed") + " to get " + (method != null ? RenderType.class.getSimpleName() + "#" + method.getName() : "RenderType#create") + " method");
        return method;
    }

    @Nullable
    private static Method getCreateMethod() {
        Method[] methods = RenderType.class.getDeclaredMethods();
        var compositeRenderTypeClass = ((RenderType) RenderType.LINE_STRIP).getClass();
        int numberOfParameters = 5;

        for (var method : methods) {
            Class<?> returnType = method.getReturnType();
            if (compositeRenderTypeClass.equals(returnType) && method.getParameterTypes().length == numberOfParameters) {
                return method;
            }
        }

        return null;
    }
}
