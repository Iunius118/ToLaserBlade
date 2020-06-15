package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;

import java.util.List;

public class LaserBladeModelType407 extends SimpleModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_407.png");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, hiltQuads, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            renderQuads(matrixStack, currentBuffer, hilt2Quads, color.gripColor, lightmapCoord, overlayColor);
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, bladeInQuads, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, bladeOutQuads, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    private static final Vector3f v55 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v12 = new Vector3f(-0.062500F, 1.468750F, 0.062500F);
    private static final Vector3f v10 = new Vector3f(0.031250F, 1.437500F, -0.031250F);
    private static final Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v9 = new Vector3f(0.031250F, 1.437500F, 0.031250F);
    private static final Vector3f v17 = new Vector3f(0.062500F, 0.343750F, -0.062500F);
    private static final Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v56 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v58 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v15 = new Vector3f(-0.062500F, 0.343750F, 0.062500F);
    private static final Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v60 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v59 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v13 = new Vector3f(0.062500F, 1.468750F, 0.062500F);
    private static final Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v19 = new Vector3f(-0.062500F, 0.343750F, -0.062500F);
    private static final Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v18 = new Vector3f(-0.062500F, 1.468750F, -0.062500F);
    private static final Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v16 = new Vector3f(0.062500F, 1.468750F, -0.062500F);
    private static final Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v14 = new Vector3f(0.062500F, 0.343750F, 0.062500F);
    private static final Vector3f v57 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v11 = new Vector3f(-0.031250F, 1.437500F, -0.031250F);
    private static final Vector3f v8 = new Vector3f(-0.031250F, 1.437500F, 0.031250F);
    private static final Vec2f v48 = new Vec2f(0.18750F, 0.81250F);
    private static final Vec2f v24 = new Vec2f(0.06250F, 0.03125F);
    private static final Vec2f v49 = new Vec2f(0.12500F, 0.81250F);
    private static final Vec2f v38 = new Vec2f(0.03125F, 0.25000F);
    private static final Vec2f v45 = new Vec2f(0.37500F, 0.81250F);
    private static final Vec2f v26 = new Vec2f(0.09375F, 0.03125F);
    private static final Vec2f v34 = new Vec2f(0.12500F, 0.25000F);
    private static final Vec2f v43 = new Vec2f(0.31250F, 0.25000F);
    private static final Vec2f v25 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v37 = new Vec2f(0.00000F, 0.25000F);
    private static final Vec2f v44 = new Vec2f(0.37500F, 0.25000F);
    private static final Vec2f v33 = new Vec2f(0.09375F, 0.25000F);
    private static final Vec2f v35 = new Vec2f(0.12500F, 0.78125F);
    private static final Vec2f v42 = new Vec2f(0.06250F, 0.78125F);
    private static final Vec2f v36 = new Vec2f(0.09375F, 0.78125F);
    private static final Vec2f v31 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v28 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v30 = new Vec2f(0.12500F, 0.03125F);
    private static final Vec2f v54 = new Vec2f(0.31250F, 0.18750F);
    private static final Vec2f v53 = new Vec2f(0.25000F, 0.18750F);
    private static final Vec2f v39 = new Vec2f(0.03125F, 0.78125F);
    private static final Vec2f v52 = new Vec2f(0.18750F, 0.18750F);
    private static final Vec2f v46 = new Vec2f(0.31250F, 0.81250F);
    private static final Vec2f v21 = new Vec2f(0.03125F, 0.03125F);
    private static final Vec2f v20 = new Vec2f(0.00000F, 0.03125F);
    private static final Vec2f v23 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v27 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v22 = new Vec2f(0.03125F, 0.21875F);
    private static final Vec2f v47 = new Vec2f(0.18750F, 0.25000F);
    private static final Vec2f v50 = new Vec2f(0.25000F, 0.25000F);
    private static final Vec2f v32 = new Vec2f(0.03125F, 0.00000F);
    private static final Vec2f v40 = new Vec2f(0.00000F, 0.78125F);
    private static final Vec2f v41 = new Vec2f(0.06250F, 0.25000F);
    private static final Vec2f v51 = new Vec2f(0.25000F, 0.81250F);
    private static final Vec2f v29 = new Vec2f(0.06250F, 0.00000F);

    public static final List<SimpleQuad> hiltQuads = getHiltQuads();

    public static List<SimpleQuad> getHiltQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v23, v55, v2, c, v22, v55, v1, c, v21, v55, v0, c, v20, v55));
        builder.add(new SimpleQuad(v2, c, v22, v56, v5, c, v25, v56, v4, c, v24, v56, v1, c, v21, v56));
        builder.add(new SimpleQuad(v5, c, v25, v57, v7, c, v27, v57, v6, c, v26, v57, v4, c, v24, v57));
        builder.add(new SimpleQuad(v5, c, v26, v58, v2, c, v24, v58, v3, c, v29, v58, v7, c, v28, v58));
        builder.add(new SimpleQuad(v7, c, v27, v59, v3, c, v31, v59, v0, c, v30, v59, v6, c, v26, v59));
        return builder.build();
    }

    public static final List<SimpleQuad> hilt2Quads = getHilt2Quads();

    public static List<SimpleQuad> getHilt2Quads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c, v32, v60, v1, c, v21, v60, v4, c, v24, v60, v6, c, v29, v60));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeInQuads = getBladeInQuads();

    public static List<SimpleQuad> getBladeInQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.9F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v6, c, v36, v59, v0, c, v35, v59, v9, c, v34, v59, v8, c, v33, v59));
        builder.add(new SimpleQuad(v0, c, v40, v55, v1, c, v39, v55, v10, c, v38, v55, v9, c, v37, v55));
        builder.add(new SimpleQuad(v1, c, v39, v56, v4, c, v42, v56, v11, c, v41, v56, v10, c, v38, v56));
        builder.add(new SimpleQuad(v4, c, v42, v57, v6, c, v36, v57, v8, c, v33, v57, v11, c, v41, v57));
        builder.add(new SimpleQuad(v8, c, v25, v60, v9, c, v22, v60, v10, c, v38, v60, v11, c, v41, v60));
        builder.add(new SimpleQuad(v6, c, v27, v58, v4, c, v33, v58, v1, c, v41, v58, v0, c, v25, v58));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOutQuads = getBladeOutQuads();

    public static List<SimpleQuad> getBladeOutQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.5F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c, v46, v59, v14, c, v45, v59, v13, c, v44, v59, v12, c, v43, v59));
        builder.add(new SimpleQuad(v14, c, v49, v55, v17, c, v48, v55, v16, c, v47, v55, v13, c, v34, v55));
        builder.add(new SimpleQuad(v17, c, v48, v56, v19, c, v51, v56, v18, c, v50, v56, v16, c, v47, v56));
        builder.add(new SimpleQuad(v19, c, v51, v57, v15, c, v46, v57, v12, c, v43, v57, v18, c, v50, v57));
        builder.add(new SimpleQuad(v12, c, v53, v60, v13, c, v52, v60, v16, c, v47, v60, v18, c, v50, v60));
        builder.add(new SimpleQuad(v14, c, v53, v58, v15, c, v54, v58, v19, c, v43, v58, v17, c, v50, v58));
        return builder.build();
    }
}
