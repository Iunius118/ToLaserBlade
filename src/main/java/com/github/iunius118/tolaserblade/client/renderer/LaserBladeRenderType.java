package com.github.iunius118.tolaserblade.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.SortedMap;

public class LaserBladeRenderType extends RenderType {
    public LaserBladeRenderType(String name, VertexFormat vertexFormat, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static final RenderType HILT = Atlases.getTranslucentCullBlockType();
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

    private static RenderType.State getLaserFlatRenderState() {
        return RenderType.State.getBuilder()
                .texture(BLOCK_SHEET)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
    }

    private static RenderType.State getLaserAddRenderState() {
        return RenderType.State.getBuilder()
                .texture(BLOCK_SHEET)
                .transparency(LIGHTNING_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
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

    @SuppressWarnings("unchecked")  // Unchecked cast to Map
    private static void registerRenderTypes() {
        Map<Object, Object> fixedBuffers = null;
        RenderTypeBuffers renderTypeBuffers = Minecraft.getInstance().getRenderTypeBuffers();

        try {
            // Get RenderTypeBuffers#fixedBuffers to register mod RenderTypes with reflection
            final Field[] fields = renderTypeBuffers.getClass().getDeclaredFields();
            String fieldName1 = "fixedBuffers";
            String fieldName2 = "field_228480_b_";
            Field fieldFixedBuffers = null;

            for (Field field : fields) {
                String name = field.getName();
                if (name.equals(fieldName1) || name.equals(fieldName2)) {
                    fieldFixedBuffers = field;
                    // ToLaserBlade.LOGGER.info("Add ToLaserBlade render types to RenderTypeBuffers." + name);
                    break;
                }
            }

            if (fieldFixedBuffers == null) {
                throw new NoSuchFieldException(fieldName1);
            }

            fieldFixedBuffers.setAccessible(true);
            Object objFixedBuffers = fieldFixedBuffers.get(renderTypeBuffers);

            if (objFixedBuffers instanceof SortedMap) {
                fixedBuffers = (Map<Object, Object>)objFixedBuffers;
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        if (fixedBuffers != null) {
            // Register mod RenderTypes
            fixedBuffers.put(LASER_FLAT, new BufferBuilder(LASER_FLAT.getBufferSize()));
            fixedBuffers.put(LASER_ADD, new BufferBuilder(LASER_ADD.getBufferSize()));
            fixedBuffers.put(LASER_SUB, new BufferBuilder(LASER_SUB.getBufferSize()));
        }
    }
}
