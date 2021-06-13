package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelHolder;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import javax.annotation.Nullable;
import java.util.Optional;

public class LaserBladeRenderType extends RenderType {
    public LaserBladeRenderType(String name, VertexFormat vertexFormat, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    private static final LaserBladeTextureState LASER_BLADE_TEXTURE_STATE = new LaserBladeTextureState();

    public static final RenderType HILT = getBladeRenderType("hilt", getHiltRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_FLAT = getBladeRenderType("laser_flat", getFlatRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_ADD = getBladeRenderType("laser_add", getAddRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_SUB_INNER = getBladeRenderType("laser_sub_in", getSubRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_SUB = getBladeRenderType("laser_sub", getSubRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));

    private static final Boolean canUseFixedVertexBuffer;

    static {
        canUseFixedVertexBuffer = ToLaserBladeConfig.CLIENT.useFixedVertexBuffer.get();
        if (canUseFixedVertexBuffer) registerRenderTypes();
    }

    public static RenderType getBladeRenderType(String name, RenderType.State renderState) {
        return RenderType.create(name, DefaultVertexFormats.NEW_ENTITY, GL11.GL_QUADS, 256, true, false, renderState);
    }

    public static RenderType getTrapRenderType(ResourceLocation locationIn) {
        return RenderType.create("laser_trap", DefaultVertexFormats.NEW_ENTITY, GL11.GL_QUADS, 256, true, false, getLaserTrapRenderState(locationIn));
    }

    public static RenderType.State getHiltRenderState(TextureState textureState) {
        return RenderType.State.builder()
                .setTextureState(textureState)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setDiffuseLightingState(DIFFUSE_LIGHTING)
                .setAlphaState(DEFAULT_ALPHA)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static RenderType.State getFlatRenderState(TextureState textureState) {
        return RenderType.State.builder()
                .setTextureState(textureState)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setAlphaState(DEFAULT_ALPHA)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static RenderType.State getAddRenderState(TextureState textureState) {
        return RenderType.State.builder()
                .setTextureState(textureState)
                .setTransparencyState(LIGHTNING_TRANSPARENCY)
                .setAlphaState(DEFAULT_ALPHA)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static RenderType.State getSubRenderState(TextureState textureState) {
        TransparencyState transparencyState = new TransparencyState("sub_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
            RenderSystem.defaultBlendFunc();
        });

        return RenderType.State.builder()
                .setTextureState(textureState)
                .setTransparencyState(transparencyState)
                .setAlphaState(DEFAULT_ALPHA)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    private static RenderType.State getLaserTrapRenderState(ResourceLocation locationIn) {
        return RenderType.State.builder()
                .setTextureState(new RenderState.TextureState(locationIn, false, false))
                .setTransparencyState(NO_TRANSPARENCY)
                .setAlphaState(DEFAULT_ALPHA)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
    }

    public static boolean canUseFixedVertexBuffer() {
        return canUseFixedVertexBuffer;
    }

    private static void registerRenderTypes() {
        RenderTypeBuffers renderTypeBuffers = Minecraft.getInstance().renderBuffers();
        // Register mod RenderTypes
        renderTypeBuffers.fixedBuffers.put(LASER_FLAT, new BufferBuilder(LASER_FLAT.bufferSize()));
        renderTypeBuffers.fixedBuffers.put(LASER_SUB_INNER, new BufferBuilder(LASER_SUB_INNER.bufferSize()));
        renderTypeBuffers.fixedBuffers.put(LASER_ADD, new BufferBuilder(LASER_ADD.bufferSize()));
        renderTypeBuffers.fixedBuffers.put(LASER_SUB, new BufferBuilder(LASER_SUB.bufferSize()));
    }

    @OnlyIn(Dist.CLIENT)
    private static class LaserBladeTextureState extends TextureState {
        @Override
        public void setupRenderState() {
            RenderSystem.enableTexture();
            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
            texturemanager.bind(LaserBladeModelHolder.getTexture());
            texturemanager.getTexture(LaserBladeModelHolder.getTexture()).setFilter(false, false);
        }

        @Override
        public void clearRenderState() {

        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            } else if (other != null && this.getClass() == other.getClass()) {
                LaserBladeTextureState state = (LaserBladeTextureState)other;
                return this.texture().equals(state.texture());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return LaserBladeModelHolder.getTexture().hashCode();
        }

        @Override
        protected Optional<ResourceLocation> texture() {
            return Optional.of(LaserBladeModelHolder.getTexture());
        }
    }
}
