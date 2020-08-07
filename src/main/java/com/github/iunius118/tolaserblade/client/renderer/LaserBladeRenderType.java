package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelHolder;
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

    public static final RenderType HILT = getBladeRenderType("hilt", getLaserHiltRenderState());
    public static final RenderType LASER_FLAT = getBladeRenderType("laser_flat", getLaserFlatRenderState());
    public static final RenderType LASER_ADD = getBladeRenderType("laser_add", getLaserAddRenderState());
    public static final RenderType LASER_SUB = getBladeRenderType("laser_sub", getLaserSubRenderState());

    static {
        registerRenderTypes();
    }

    private static RenderType getBladeRenderType(String name, RenderType.State renderState) {
        return RenderType.makeType(name, DefaultVertexFormats.ENTITY, GL11.GL_QUADS, 256, true, false, renderState);
    }

    public static RenderType getTrapRenderType(ResourceLocation locationIn) {
        return RenderType.makeType("laser_trap", DefaultVertexFormats.ENTITY, GL11.GL_QUADS, 256, true, false, getLaserTrapRenderState(locationIn));
    }

    private static RenderType.State getLaserHiltRenderState() {
        return RenderType.State.getBuilder()
                .texture(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .diffuseLighting(DIFFUSE_LIGHTING_ENABLED)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
    }

    private static RenderType.State getLaserFlatRenderState() {
        return RenderType.State.getBuilder()
                .texture(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
    }

    private static RenderType.State getLaserAddRenderState() {
        return RenderType.State.getBuilder()
                .texture(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE)
                .transparency(LIGHTNING_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
    }

    private static RenderType.State getLaserSubRenderState() {
        TransparencyState transparencyState = new TransparencyState("sub_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
            RenderSystem.defaultBlendFunc();
        });

        return RenderType.State.getBuilder()
                .texture(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE)
                .transparency(transparencyState)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
    }

    private static RenderType.State getLaserTrapRenderState(ResourceLocation locationIn) {
        return RenderType.State.getBuilder()
                .texture(new RenderState.TextureState(locationIn, false, false))
                .transparency(NO_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
    }

    private static void registerRenderTypes() {
        if (!ToLaserBladeConfig.CLIENT.useFixedVertexBuffer.get()) {
            // Canceled
            return;
        }

        RenderTypeBuffers renderTypeBuffers = Minecraft.getInstance().getRenderTypeBuffers();
        // Register mod RenderTypes
        renderTypeBuffers.fixedBuffers.put(LASER_FLAT, new BufferBuilder(LASER_FLAT.getBufferSize()));
        renderTypeBuffers.fixedBuffers.put(LASER_ADD, new BufferBuilder(LASER_ADD.getBufferSize()));
        renderTypeBuffers.fixedBuffers.put(LASER_SUB, new BufferBuilder(LASER_SUB.getBufferSize()));
    }

    @OnlyIn(Dist.CLIENT)
    private static class LaserBladeTextureState extends TextureState {
        @Override
        public void setupRenderState() {
            RenderSystem.enableTexture();
            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
            texturemanager.bindTexture(LaserBladeModelHolder.getTexture());
            texturemanager.getTexture(LaserBladeModelHolder.getTexture()).setBlurMipmapDirect(false, false);
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
