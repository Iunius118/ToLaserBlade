package com.github.iunius118.tolaserblade.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class LaserBladeRenderType extends RenderType {
    public LaserBladeRenderType(String name, VertexFormat vertexFormat, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static final RenderType HILT = RenderType.getEntityTranslucent(LaserBladeItemRenderer.LASER_BLADE_TEXTURE);
    public static final RenderType LASER_ADD = getRenderType("laser_add", getLaserAddRenderState());
    public static final RenderType LASER_SUB = getRenderType("laser_sub", getLaserSubRenderState());

    private static RenderType getRenderType(String name, RenderType.State renderState) {
        return RenderType.makeType(name, DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 256, false, true, renderState);
    }

    private static RenderType.State getLaserAddRenderState() {
        RenderState.TransparencyState transparencyState = new RenderState.TransparencyState("add_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        });

        return RenderType.State.getBuilder()
                .lightmap(LIGHTMAP_ENABLED)
                .texture(new RenderState.TextureState(LaserBladeItemRenderer.LASER_BLADE_TEXTURE, false, false))
                .transparency(transparencyState)
                .build(true);
    }

    private static RenderType.State getLaserSubRenderState() {
        RenderState.TransparencyState transparencyState = new RenderState.TransparencyState("sub_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
            RenderSystem.defaultBlendFunc();
        });

        return RenderType.State.getBuilder()
                .lightmap(LIGHTMAP_ENABLED)
                .texture(new RenderState.TextureState(LaserBladeItemRenderer.LASER_BLADE_TEXTURE, false, false))
                .transparency(transparencyState)
                .build(true);
    }
}
