package com.github.iunius118.tolaserblade.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.Atlases;
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

    public static final RenderType HILT = Atlases.getTranslucentCullBlockType() ;
    public static final RenderType LASER_ADD = getRenderType("laser_add", getLaserAddRenderState());
    public static final RenderType LASER_SUB = getRenderType("laser_sub", getLaserSubRenderState());
    public static final RenderType LASER_FLAT = getRenderType("laser_flat", getLaserFlatRenderState());

    private static RenderType getRenderType(String name, RenderType.State renderState) {
        return RenderType.makeType(name, DefaultVertexFormats.ENTITY, GL11.GL_QUADS, 256, false, false, renderState);
    }

    private static RenderType.State getLaserFlatRenderState() {
        return RenderType.State.getBuilder()
                .texture(BLOCK_SHEET)
                .alpha(DEFAULT_ALPHA)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .build(true);
    }

    private static RenderType.State getLaserAddRenderState() {
        return RenderType.State.getBuilder()
                .texture(BLOCK_SHEET)
                .alpha(DEFAULT_ALPHA)
                .transparency(LIGHTNING_TRANSPARENCY)
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
                .texture(BLOCK_SHEET)
                .alpha(DEFAULT_ALPHA)
                .transparency(transparencyState)
                .build(true);
    }
}
