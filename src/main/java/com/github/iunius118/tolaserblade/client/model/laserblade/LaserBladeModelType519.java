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

public class LaserBladeModelType519 extends SimpleModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_519.png");

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

    private static final Vector3f v9 = new Vector3f(0.031250F, 1.437500F, 0.031250F);
    private static final Vector3f v20 = new Vector3f(0.062500F, 1.468750F, -0.062500F);
    private static final Vector3f v21 = new Vector3f(0.062500F, 0.343750F, -0.062500F);
    private static final Vector3f v11 = new Vector3f(-0.031250F, 1.437500F, -0.031250F);
    private static final Vector3f v31 = new Vector3f(-0.062500F, -1.156250F, -0.062500F);
    private static final Vector3f v18 = new Vector3f(0.062500F, 0.343750F, 0.062500F);
    private static final Vector3f v95 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v16 = new Vector3f(-0.062500F, 1.468750F, 0.062500F);
    private static final Vector3f v8 = new Vector3f(-0.031250F, 1.437500F, 0.031250F);
    private static final Vector3f v3 = new Vector3f(0.031250F, -0.062500F, 0.031250F);
    private static final Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v12 = new Vector3f(0.031250F, -1.125000F, 0.031250F);
    private static final Vector3f v14 = new Vector3f(0.031250F, -1.125000F, -0.031250F);
    private static final Vector3f v94 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v28 = new Vector3f(0.062500F, -0.031250F, -0.062500F);
    private static final Vector3f v29 = new Vector3f(0.062500F, -1.156250F, -0.062500F);
    private static final Vector3f v24 = new Vector3f(-0.062500F, -0.031250F, 0.062500F);
    private static final Vector3f v96 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v30 = new Vector3f(-0.062500F, -0.031250F, -0.062500F);
    private static final Vector3f v5 = new Vector3f(-0.031250F, -0.062500F, -0.031250F);
    private static final Vector3f v91 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v10 = new Vector3f(0.031250F, 1.437500F, -0.031250F);
    private static final Vector3f v2 = new Vector3f(0.031250F, -0.062500F, -0.031250F);
    private static final Vector3f v93 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v26 = new Vector3f(0.062500F, -1.156250F, 0.062500F);
    private static final Vector3f v27 = new Vector3f(-0.062500F, -1.156250F, 0.062500F);
    private static final Vector3f v13 = new Vector3f(-0.031250F, -1.125000F, 0.031250F);
    private static final Vector3f v15 = new Vector3f(-0.031250F, -1.125000F, -0.031250F);
    private static final Vector3f v7 = new Vector3f(-0.031250F, -0.062500F, 0.031250F);
    private static final Vector3f v25 = new Vector3f(0.062500F, -0.031250F, 0.062500F);
    private static final Vector3f v92 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v23 = new Vector3f(-0.062500F, 0.343750F, -0.062500F);
    private static final Vector3f v19 = new Vector3f(-0.062500F, 0.343750F, 0.062500F);
    private static final Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v22 = new Vector3f(-0.062500F, 1.468750F, -0.062500F);
    private static final Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v17 = new Vector3f(0.062500F, 1.468750F, 0.062500F);
    private static final Vec2f v42 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f v58 = new Vec2f(0.46875F, 0.81250F);
    private static final Vec2f v60 = new Vec2f(0.40625F, 0.28125F);
    private static final Vec2f v61 = new Vec2f(0.40625F, 0.81250F);
    private static final Vec2f v90 = new Vec2f(0.68750F, 0.21875F);
    private static final Vec2f v89 = new Vec2f(0.62500F, 0.21875F);
    private static final Vec2f v72 = new Vec2f(0.18750F, 0.84375F);
    private static final Vec2f v87 = new Vec2f(0.62500F, 0.84375F);
    private static final Vec2f v86 = new Vec2f(0.62500F, 0.28125F);
    private static final Vec2f v41 = new Vec2f(0.12500F, 0.25000F);
    private static final Vec2f v85 = new Vec2f(0.50000F, 0.84375F);
    private static final Vec2f v79 = new Vec2f(0.68750F, 0.28125F);
    private static final Vec2f v40 = new Vec2f(0.12500F, 0.03125F);
    private static final Vec2f v83 = new Vec2f(0.56250F, 0.28125F);
    private static final Vec2f v45 = new Vec2f(0.09375F, 0.28125F);
    private static final Vec2f v82 = new Vec2f(0.68750F, 0.84375F);
    private static final Vec2f v81 = new Vec2f(0.75000F, 0.84375F);
    private static final Vec2f v73 = new Vec2f(0.12500F, 0.84375F);
    private static final Vec2f v62 = new Vec2f(0.37500F, 0.81250F);
    private static final Vec2f v78 = new Vec2f(0.31250F, 0.21875F);
    private static final Vec2f v77 = new Vec2f(0.25000F, 0.21875F);
    private static final Vec2f v74 = new Vec2f(0.25000F, 0.28125F);
    private static final Vec2f v34 = new Vec2f(0.03125F, 0.25000F);
    private static final Vec2f v32 = new Vec2f(0.00000F, 0.03125F);
    private static final Vec2f v84 = new Vec2f(0.56250F, 0.84375F);
    private static final Vec2f v75 = new Vec2f(0.25000F, 0.84375F);
    private static final Vec2f v38 = new Vec2f(0.09375F, 0.03125F);
    private static final Vec2f v80 = new Vec2f(0.75000F, 0.28125F);
    private static final Vec2f v88 = new Vec2f(0.56250F, 0.21875F);
    private static final Vec2f v69 = new Vec2f(0.37500F, 0.84375F);
    private static final Vec2f v56 = new Vec2f(0.50000F, 0.28125F);
    private static final Vec2f v71 = new Vec2f(0.18750F, 0.28125F);
    private static final Vec2f v70 = new Vec2f(0.31250F, 0.84375F);
    private static final Vec2f v33 = new Vec2f(0.03125F, 0.03125F);
    private static final Vec2f v54 = new Vec2f(0.06250F, 0.81250F);
    private static final Vec2f v59 = new Vec2f(0.37500F, 0.28125F);
    private static final Vec2f v68 = new Vec2f(0.31250F, 0.28125F);
    private static final Vec2f v64 = new Vec2f(0.43750F, 0.81250F);
    private static final Vec2f v51 = new Vec2f(0.03125F, 0.81250F);
    private static final Vec2f v76 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v53 = new Vec2f(0.06250F, 0.28125F);
    private static final Vec2f v52 = new Vec2f(0.00000F, 0.81250F);
    private static final Vec2f v43 = new Vec2f(0.03125F, 0.00000F);
    private static final Vec2f v48 = new Vec2f(0.09375F, 0.81250F);
    private static final Vec2f v36 = new Vec2f(0.06250F, 0.03125F);
    private static final Vec2f v47 = new Vec2f(0.12500F, 0.81250F);
    private static final Vec2f v57 = new Vec2f(0.50000F, 0.81250F);
    private static final Vec2f v65 = new Vec2f(0.40625F, 0.25000F);
    private static final Vec2f v37 = new Vec2f(0.06250F, 0.25000F);
    private static final Vec2f v44 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v49 = new Vec2f(0.00000F, 0.28125F);
    private static final Vec2f v35 = new Vec2f(0.00000F, 0.25000F);
    private static final Vec2f v50 = new Vec2f(0.03125F, 0.28125F);
    private static final Vec2f v67 = new Vec2f(0.46875F, 0.25000F);
    private static final Vec2f v55 = new Vec2f(0.46875F, 0.28125F);
    private static final Vec2f v46 = new Vec2f(0.12500F, 0.28125F);
    private static final Vec2f v63 = new Vec2f(0.43750F, 0.28125F);
    private static final Vec2f v39 = new Vec2f(0.09375F, 0.25000F);
    private static final Vec2f v66 = new Vec2f(0.43750F, 0.25000F);

    public static final List<SimpleQuad> hiltQuads = getHiltQuads();

    public static List<SimpleQuad> getHiltQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v35, v91, v2, c, v34, v91, v1, c, v33, v91, v0, c, v32, v91));
        builder.add(new SimpleQuad(v2, c, v34, v92, v5, c, v37, v92, v4, c, v36, v92, v1, c, v33, v92));
        builder.add(new SimpleQuad(v5, c, v37, v93, v7, c, v39, v93, v6, c, v38, v93, v4, c, v36, v93));
        builder.add(new SimpleQuad(v7, c, v39, v94, v3, c, v41, v94, v0, c, v40, v94, v6, c, v38, v94));
        return builder.build();
    }

    public static final List<SimpleQuad> hilt2Quads = getHilt2Quads();

    public static List<SimpleQuad> getHilt2Quads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c, v43, v95, v1, c, v33, v95, v4, c, v36, v95, v6, c, v42, v95));
        builder.add(new SimpleQuad(v5, c, v38, v96, v2, c, v36, v96, v3, c, v42, v96, v7, c, v44, v96));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeInQuads = getBladeInQuads();

    public static List<SimpleQuad> getBladeInQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.9F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v6, c, v48, v94, v0, c, v47, v94, v9, c, v46, v94, v8, c, v45, v94));
        builder.add(new SimpleQuad(v0, c, v52, v91, v1, c, v51, v91, v10, c, v50, v91, v9, c, v49, v91));
        builder.add(new SimpleQuad(v1, c, v51, v92, v4, c, v54, v92, v11, c, v53, v92, v10, c, v50, v92));
        builder.add(new SimpleQuad(v4, c, v54, v93, v6, c, v48, v93, v8, c, v45, v93, v11, c, v53, v93));
        builder.add(new SimpleQuad(v8, c, v37, v95, v9, c, v34, v95, v10, c, v50, v95, v11, c, v53, v95));
        builder.add(new SimpleQuad(v6, c, v39, v96, v4, c, v45, v96, v1, c, v53, v96, v0, c, v37, v96));
        builder.add(new SimpleQuad(v13, c, v58, v94, v12, c, v57, v94, v3, c, v56, v94, v7, c, v55, v94));
        builder.add(new SimpleQuad(v12, c, v62, v91, v14, c, v61, v91, v2, c, v60, v91, v3, c, v59, v91));
        builder.add(new SimpleQuad(v14, c, v61, v92, v15, c, v64, v92, v5, c, v63, v92, v2, c, v60, v92));
        builder.add(new SimpleQuad(v15, c, v64, v93, v13, c, v58, v93, v7, c, v55, v93, v5, c, v63, v93));
        builder.add(new SimpleQuad(v7, c, v66, v95, v3, c, v65, v95, v2, c, v60, v95, v5, c, v63, v95));
        builder.add(new SimpleQuad(v13, c, v67, v96, v15, c, v55, v96, v14, c, v63, v96, v12, c, v66, v96));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOutQuads = getBladeOutQuads();

    public static List<SimpleQuad> getBladeOutQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.5F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v19, c, v70, v94, v18, c, v69, v94, v17, c, v59, v94, v16, c, v68, v94));
        builder.add(new SimpleQuad(v18, c, v73, v91, v21, c, v72, v91, v20, c, v71, v91, v17, c, v46, v91));
        builder.add(new SimpleQuad(v21, c, v72, v92, v23, c, v75, v92, v22, c, v74, v92, v20, c, v71, v92));
        builder.add(new SimpleQuad(v23, c, v75, v93, v19, c, v70, v93, v16, c, v68, v93, v22, c, v74, v93));
        builder.add(new SimpleQuad(v16, c, v77, v95, v17, c, v76, v95, v20, c, v71, v95, v22, c, v74, v95));
        builder.add(new SimpleQuad(v18, c, v77, v96, v19, c, v78, v96, v23, c, v68, v96, v21, c, v74, v96));
        builder.add(new SimpleQuad(v27, c, v82, v94, v26, c, v81, v94, v25, c, v80, v94, v24, c, v79, v94));
        builder.add(new SimpleQuad(v26, c, v85, v91, v29, c, v84, v91, v28, c, v83, v91, v25, c, v56, v91));
        builder.add(new SimpleQuad(v29, c, v84, v92, v31, c, v87, v92, v30, c, v86, v92, v28, c, v83, v92));
        builder.add(new SimpleQuad(v31, c, v87, v93, v27, c, v82, v93, v24, c, v79, v93, v30, c, v86, v93));
        builder.add(new SimpleQuad(v24, c, v89, v95, v25, c, v88, v95, v28, c, v83, v95, v30, c, v86, v95));
        builder.add(new SimpleQuad(v26, c, v89, v96, v27, c, v90, v96, v31, c, v79, v96, v29, c, v86, v96));
        return builder.build();
    }
}
