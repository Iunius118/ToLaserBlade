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

public class LaserBladeModelType504 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_504.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v18 = new Vector3f(-0.062500F, 1.468750F, -0.062500F);
        Vector3f v57 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v3 = new Vector3f(0.031250F, -0.062500F, 0.031250F);
        Vector3f v15 = new Vector3f(-0.062500F, 0.343750F, 0.062500F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v55 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v13 = new Vector3f(0.062500F, 1.468750F, 0.062500F);
        Vector3f v60 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v14 = new Vector3f(0.062500F, 0.343750F, 0.062500F);
        Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v59 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v11 = new Vector3f(-0.031250F, 1.437500F, -0.031250F);
        Vector3f v56 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v19 = new Vector3f(-0.062500F, 0.343750F, -0.062500F);
        Vector3f v17 = new Vector3f(0.062500F, 0.343750F, -0.062500F);
        Vector3f v58 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v16 = new Vector3f(0.062500F, 1.468750F, -0.062500F);
        Vector3f v12 = new Vector3f(-0.062500F, 1.468750F, 0.062500F);
        Vector3f v5 = new Vector3f(-0.031250F, -0.062500F, -0.031250F);
        Vector3f v10 = new Vector3f(0.031250F, 1.437500F, -0.031250F);
        Vector3f v8 = new Vector3f(-0.031250F, 1.437500F, 0.031250F);
        Vector3f v9 = new Vector3f(0.031250F, 1.437500F, 0.031250F);
        Vector3f v7 = new Vector3f(-0.031250F, -0.062500F, 0.031250F);
        Vector3f v2 = new Vector3f(0.031250F, -0.062500F, -0.031250F);
        Vec2f v31 = new Vec2f(0.12500F, 0.25000F);
        Vec2f v23 = new Vec2f(0.00000F, 0.25000F);
        Vec2f v48 = new Vec2f(0.18750F, 0.84375F);
        Vec2f v41 = new Vec2f(0.06250F, 0.28125F);
        Vec2f v26 = new Vec2f(0.09375F, 0.03125F);
        Vec2f v30 = new Vec2f(0.12500F, 0.03125F);
        Vec2f v24 = new Vec2f(0.06250F, 0.03125F);
        Vec2f v37 = new Vec2f(0.00000F, 0.28125F);
        Vec2f v40 = new Vec2f(0.00000F, 0.81250F);
        Vec2f v46 = new Vec2f(0.31250F, 0.84375F);
        Vec2f v29 = new Vec2f(0.06250F, 0.00000F);
        Vec2f v51 = new Vec2f(0.25000F, 0.84375F);
        Vec2f v36 = new Vec2f(0.09375F, 0.81250F);
        Vec2f v38 = new Vec2f(0.03125F, 0.28125F);
        Vec2f v28 = new Vec2f(0.09375F, 0.00000F);
        Vec2f v32 = new Vec2f(0.03125F, 0.00000F);
        Vec2f v22 = new Vec2f(0.03125F, 0.25000F);
        Vec2f v35 = new Vec2f(0.12500F, 0.81250F);
        Vec2f v34 = new Vec2f(0.12500F, 0.28125F);
        Vec2f v45 = new Vec2f(0.37500F, 0.84375F);
        Vec2f v54 = new Vec2f(0.31250F, 0.21875F);
        Vec2f v47 = new Vec2f(0.18750F, 0.28125F);
        Vec2f v25 = new Vec2f(0.06250F, 0.25000F);
        Vec2f v39 = new Vec2f(0.03125F, 0.81250F);
        Vec2f v21 = new Vec2f(0.03125F, 0.03125F);
        Vec2f v52 = new Vec2f(0.18750F, 0.21875F);
        Vec2f v43 = new Vec2f(0.31250F, 0.28125F);
        Vec2f v42 = new Vec2f(0.06250F, 0.81250F);
        Vec2f v50 = new Vec2f(0.25000F, 0.28125F);
        Vec2f v20 = new Vec2f(0.00000F, 0.03125F);
        Vec2f v27 = new Vec2f(0.09375F, 0.25000F);
        Vec2f v33 = new Vec2f(0.09375F, 0.28125F);
        Vec2f v44 = new Vec2f(0.37500F, 0.28125F);
        Vec2f v49 = new Vec2f(0.12500F, 0.84375F);
        Vec2f v53 = new Vec2f(0.25000F, 0.21875F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v23, v55, v2, c0, v22, v55, v1, c0, v21, v55, v0, c0, v20, v55));
        builder.add(new SimpleQuad(v2, c0, v22, v56, v5, c0, v25, v56, v4, c0, v24, v56, v1, c0, v21, v56));
        builder.add(new SimpleQuad(v5, c0, v25, v57, v7, c0, v27, v57, v6, c0, v26, v57, v4, c0, v24, v57));
        builder.add(new SimpleQuad(v5, c0, v26, v58, v2, c0, v24, v58, v3, c0, v29, v58, v7, c0, v28, v58));
        builder.add(new SimpleQuad(v7, c0, v27, v59, v3, c0, v31, v59, v0, c0, v30, v59, v6, c0, v26, v59));
        builder.add(new SimpleQuad(v0, c0, v32, v60, v1, c0, v21, v60, v4, c0, v24, v60, v6, c0, v29, v60));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v6, c1, v36, v59, v0, c1, v35, v59, v9, c1, v34, v59, v8, c1, v33, v59));
        builder.add(new SimpleQuad(v0, c1, v40, v55, v1, c1, v39, v55, v10, c1, v38, v55, v9, c1, v37, v55));
        builder.add(new SimpleQuad(v1, c1, v39, v56, v4, c1, v42, v56, v11, c1, v41, v56, v10, c1, v38, v56));
        builder.add(new SimpleQuad(v4, c1, v42, v57, v6, c1, v36, v57, v8, c1, v33, v57, v11, c1, v41, v57));
        builder.add(new SimpleQuad(v8, c1, v25, v60, v9, c1, v22, v60, v10, c1, v38, v60, v11, c1, v41, v60));
        builder.add(new SimpleQuad(v6, c1, v27, v58, v4, c1, v33, v58, v1, c1, v41, v58, v0, c1, v25, v58));
        BLADE_IN_QUADS = builder.build();

        // BladeOut
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c2, v46, v59, v14, c2, v45, v59, v13, c2, v44, v59, v12, c2, v43, v59));
        builder.add(new SimpleQuad(v14, c2, v49, v55, v17, c2, v48, v55, v16, c2, v47, v55, v13, c2, v34, v55));
        builder.add(new SimpleQuad(v17, c2, v48, v56, v19, c2, v51, v56, v18, c2, v50, v56, v16, c2, v47, v56));
        builder.add(new SimpleQuad(v19, c2, v51, v57, v15, c2, v46, v57, v12, c2, v43, v57, v18, c2, v50, v57));
        builder.add(new SimpleQuad(v12, c2, v53, v60, v13, c2, v52, v60, v16, c2, v47, v60, v18, c2, v50, v60));
        builder.add(new SimpleQuad(v14, c2, v53, v58, v15, c2, v54, v58, v19, c2, v43, v58, v17, c2, v50, v58));
        BLADE_OUT_QUADS = builder.build();
    }
}
