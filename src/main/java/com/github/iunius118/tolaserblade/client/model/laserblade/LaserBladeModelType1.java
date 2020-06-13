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

public class LaserBladeModelType1 extends SimpleModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1.png");

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

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderQuads(matrixStack, currentBuffer, bladeOutQuads, color.simpleOuterColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, bladeInQuads, color.simpleInnerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    private static final Vector3f v84 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v27 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v0 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v30 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
    private static final Vector3f v83 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v25 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
    private static final Vector3f v14 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v13 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v23 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
    private static final Vector3f v87 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v21 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
    private static final Vector3f v22 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
    private static final Vector3f v4 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v85 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v3 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
    private static final Vector3f v17 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v16 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v29 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v11 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v26 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v9 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v2 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
    private static final Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v88 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v20 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
    private static final Vector3f v10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v86 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v5 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
    private static final Vector3f v19 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v31 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v28 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
    private static final Vector3f v24 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
    private static final Vector3f v8 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v7 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
    private static final Vector3f v18 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
    private static final Vec2f v35 = new Vec2f(0.234375F, 0.078125F);
    private static final Vec2f v59 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v61 = new Vec2f(0.37500F, 0.21875F);
    private static final Vec2f v72 = new Vec2f(0.31250F, 0.15625F);
    private static final Vec2f v62 = new Vec2f(0.37500F, 0.75000F);
    private static final Vec2f v63 = new Vec2f(0.31250F, 0.75000F);
    private static final Vec2f v68 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v32 = new Vec2f(0.21875F, 0.09375F);
    private static final Vec2f v37 = new Vec2f(0.296875F, 0.078125F);
    private static final Vec2f v48 = new Vec2f(0.40625F, 0.00000F);
    private static final Vec2f v69 = new Vec2f(0.25000F, 0.21875F);
    private static final Vec2f v54 = new Vec2f(0.03125F, 0.03125F);
    private static final Vec2f v82 = new Vec2f(0.06250F, 0.71875F);
    private static final Vec2f v45 = new Vec2f(0.21875F, 0.12500F);
    private static final Vec2f v80 = new Vec2f(0.00000F, 0.71875F);
    private static final Vec2f v51 = new Vec2f(0.12500F, 0.18750F);
    private static final Vec2f v79 = new Vec2f(0.03125F, 0.71875F);
    private static final Vec2f v36 = new Vec2f(0.31250F, 0.09375F);
    private static final Vec2f v42 = new Vec2f(0.50000F, 0.12500F);
    private static final Vec2f v34 = new Vec2f(0.234375F, 0.015625F);
    private static final Vec2f v78 = new Vec2f(0.03125F, 0.21875F);
    private static final Vec2f v57 = new Vec2f(0.06250F, 0.03125F);
    private static final Vec2f v77 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v76 = new Vec2f(0.09375F, 0.71875F);
    private static final Vec2f v50 = new Vec2f(0.12500F, 0.03125F);
    private static final Vec2f v74 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v73 = new Vec2f(0.18750F, 0.15625F);
    private static final Vec2f v71 = new Vec2f(0.25000F, 0.15625F);
    private static final Vec2f v70 = new Vec2f(0.25000F, 0.75000F);
    private static final Vec2f v67 = new Vec2f(0.12500F, 0.75000F);
    private static final Vec2f v65 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v39 = new Vec2f(0.296875F, 0.015625F);
    private static final Vec2f v81 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v47 = new Vec2f(0.31250F, 0.12500F);
    private static final Vec2f v53 = new Vec2f(0.00000F, 0.03125F);
    private static final Vec2f v40 = new Vec2f(0.40625F, 0.09375F);
    private static final Vec2f v43 = new Vec2f(0.40625F, 0.12500F);
    private static final Vec2f v41 = new Vec2f(0.50000F, 0.09375F);
    private static final Vec2f v52 = new Vec2f(0.09375F, 0.18750F);
    private static final Vec2f v64 = new Vec2f(0.31250F, 0.21875F);
    private static final Vec2f v33 = new Vec2f(0.21875F, 0.00000F);
    private static final Vec2f v58 = new Vec2f(0.06250F, 0.18750F);
    private static final Vec2f v44 = new Vec2f(0.12500F, 0.09375F);
    private static final Vec2f v49 = new Vec2f(0.09375F, 0.03125F);
    private static final Vec2f v66 = new Vec2f(0.18750F, 0.75000F);
    private static final Vec2f v60 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f v56 = new Vec2f(0.00000F, 0.18750F);
    private static final Vec2f v55 = new Vec2f(0.03125F, 0.18750F);
    private static final Vec2f v46 = new Vec2f(0.12500F, 0.12500F);
    private static final Vec2f v75 = new Vec2f(0.12500F, 0.71875F);
    private static final Vec2f v38 = new Vec2f(0.31250F, 0.00000F);

    public static final List<SimpleQuad> hiltQuads = getHiltQuads();

    public static List<SimpleQuad> getHiltQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v35, v83, v2, c, v34, v83, v1, c, v33, v83, v0, c, v32, v83));
        builder.add(new SimpleQuad(v5, c, v37, v83, v3, c, v35, v83, v0, c, v32, v83, v4, c, v36, v83));
        builder.add(new SimpleQuad(v2, c, v34, v83, v7, c, v39, v83, v6, c, v38, v83, v1, c, v33, v83));
        builder.add(new SimpleQuad(v6, c, v38, v83, v7, c, v39, v83, v5, c, v37, v83, v4, c, v36, v83));
        builder.add(new SimpleQuad(v9, c, v43, v84, v8, c, v42, v84, v1, c, v41, v84, v6, c, v40, v84));
        builder.add(new SimpleQuad(v8, c, v46, v85, v10, c, v45, v85, v0, c, v32, v85, v1, c, v44, v85));
        builder.add(new SimpleQuad(v10, c, v45, v86, v11, c, v47, v86, v4, c, v36, v86, v0, c, v32, v86));
        builder.add(new SimpleQuad(v11, c, v47, v87, v9, c, v43, v87, v6, c, v40, v87, v4, c, v36, v87));
        builder.add(new SimpleQuad(v11, c, v40, v88, v10, c, v36, v88, v8, c, v38, v88, v9, c, v48, v88));
        builder.add(new SimpleQuad(v15, c, v52, v84, v14, c, v51, v84, v13, c, v50, v84, v12, c, v49, v84));
        builder.add(new SimpleQuad(v14, c, v56, v85, v17, c, v55, v85, v16, c, v54, v85, v13, c, v53, v85));
        builder.add(new SimpleQuad(v17, c, v55, v86, v19, c, v58, v86, v18, c, v57, v86, v16, c, v54, v86));
        builder.add(new SimpleQuad(v19, c, v58, v87, v15, c, v52, v87, v12, c, v49, v87, v18, c, v57, v87));
        builder.add(new SimpleQuad(v19, c, v49, v88, v17, c, v57, v88, v14, c, v60, v88, v15, c, v59, v88));
        return builder.build();
    }

    public static final List<SimpleQuad> hilt2Quads = getHilt2Quads();

    public static List<SimpleQuad> getHilt2Quads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v35, v83, v5, c, v37, v83, v7, c, v39, v83, v2, c, v34, v83));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOutQuads = getBladeOutQuads();

    public static List<SimpleQuad> getBladeOutQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v21, c, v64, v84, v5, c, v63, v84, v3, c, v62, v84, v20, c, v61, v84));
        builder.add(new SimpleQuad(v22, c, v68, v85, v7, c, v67, v85, v5, c, v66, v85, v21, c, v65, v85));
        builder.add(new SimpleQuad(v23, c, v65, v86, v2, c, v66, v86, v7, c, v70, v86, v22, c, v69, v86));
        builder.add(new SimpleQuad(v20, c, v69, v87, v3, c, v70, v87, v2, c, v63, v87, v23, c, v64, v87));
        builder.add(new SimpleQuad(v22, c, v72, v88, v21, c, v64, v88, v20, c, v69, v88, v23, c, v71, v88));
        builder.add(new SimpleQuad(v2, c, v73, v83, v3, c, v65, v83, v5, c, v69, v83, v7, c, v71, v83));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeInQuads = getBladeInQuads();

    public static List<SimpleQuad> getBladeInQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c, v76, v84, v26, c, v75, v84, v25, c, v68, v84, v24, c, v74, v84));
        builder.add(new SimpleQuad(v26, c, v80, v85, v29, c, v79, v85, v28, c, v78, v85, v25, c, v77, v85));
        builder.add(new SimpleQuad(v29, c, v79, v86, v31, c, v82, v86, v30, c, v81, v86, v28, c, v78, v86));
        builder.add(new SimpleQuad(v31, c, v82, v87, v27, c, v76, v87, v24, c, v74, v87, v30, c, v81, v87));
        builder.add(new SimpleQuad(v24, c, v58, v83, v25, c, v55, v83, v28, c, v78, v83, v30, c, v81, v83));
        builder.add(new SimpleQuad(v27, c, v52, v88, v31, c, v74, v88, v29, c, v81, v88, v26, c, v58, v88));
        return builder.build();
    }
}
