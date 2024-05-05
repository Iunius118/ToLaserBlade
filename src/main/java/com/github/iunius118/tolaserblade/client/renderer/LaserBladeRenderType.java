package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL14;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class LaserBladeRenderType {
    private static final LaserBladeTextureState LASER_BLADE_TEXTURE_STATE = new LaserBladeTextureState();

    public static final String UNLIT_SHADER_INSTANCE_NAME = "rendertype_laser_blade_unlit";
    public static final RenderType HILT = getBladeRenderType("tlb_hilt", getHiltRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_UNLIT = getBladeRenderType("tlb_unlit", getUnlitRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_SUB_INNER = getBladeRenderType("tlb_sub_in", getSubRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_ADD = getBladeRenderType("tlb_add", getAddRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));
    public static final RenderType LASER_SUB = getBladeRenderType("tlb_sub", getSubRenderState(LaserBladeRenderType.LASER_BLADE_TEXTURE_STATE));

    public static RenderType getBladeRenderType(String name, RenderType.CompositeState renderState) {
        return RenderType.create(name, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, renderState);
    }

    public static RenderType.CompositeState getHiltRenderState(RenderStateShard.TextureStateShard textureState) {
        return Internal.getHiltRenderState(textureState);
    }

    public static RenderType.CompositeState getUnlitRenderState(TextureStateShard textureState) {
        return Internal.getUnlitRenderState(textureState);
    }

    public static RenderType.CompositeState getAddRenderState(TextureStateShard textureState) {
        return Internal.getAddRenderState(textureState);
    }

    public static RenderType.CompositeState getSubRenderState(TextureStateShard textureState) {
        return Internal.getSubRenderState(textureState);
    }

    public static void setUnlitShaderInstance(ShaderInstance shaderInstance) {
        Internal.setUnlitShaderInstance(shaderInstance);
    }

    @OnlyIn(Dist.CLIENT)
    private static class Internal extends RenderType {
        private static ShaderInstance laserBladeUnlitShader;
        private static final ShaderStateShard LASER_BLADE_UNLIT_SHADER_STATE = new ShaderStateShard(() -> laserBladeUnlitShader);

        private Internal(String name, VertexFormat fmt, VertexFormat.Mode glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
            super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
        }

        public static void setUnlitShaderInstance(ShaderInstance shader) {
            if (shader != null) {
                laserBladeUnlitShader = shader;
            }

        }

        public static RenderType.CompositeState getHiltRenderState(RenderStateShard.TextureStateShard textureState) {
            return RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                    .setTextureState(textureState)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true);
        }

        public static CompositeState getUnlitRenderState(TextureStateShard textureState) {
            return CompositeState.builder()
                    .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                    .setTextureState(textureState)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true);
        }

        public static CompositeState getAddRenderState(TextureStateShard textureState) {
            return CompositeState.builder()
                    .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                    .setTextureState(textureState)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true);
        }

        public static CompositeState getSubRenderState(TextureStateShard textureState) {
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

            return CompositeState.builder()
                    .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                    .setTextureState(textureState)
                    .setTransparencyState(transparencyState)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class LaserBladeTextureState extends TextureStateShard {
        private static final ResourceLocation TEXTURE = new ResourceLocation("forge", "textures/white.png");

        public LaserBladeTextureState() {
            super(TEXTURE, false, false);
        }

        @Override
        public void setupRenderState() {
            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
            texturemanager.bindForSetup(TEXTURE);
            texturemanager.getTexture(TEXTURE).setFilter(false, false);
            RenderSystem.setShaderTexture(0, TEXTURE);
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
                return this.cutoutTexture().equals(state.cutoutTexture());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return TEXTURE.hashCode();
        }

        @Override
        protected Optional<ResourceLocation> cutoutTexture() {
            return Optional.of(TEXTURE);
        }
    }
}
