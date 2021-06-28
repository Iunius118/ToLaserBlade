package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class LaserBladeModelType217 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_217.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> HILT_2_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(getHiltRenderType());
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            renderQuads(matrixStack, currentBuffer, HILT_2_QUADS, color.gripColor, lightmapCoord, overlayColor);
            return;
        }

        currentBuffer = buffer.getBuffer(getFlatRenderType());
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.simpleOuterColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.simpleInnerColor, fullLight, overlayColor);

        currentBuffer = buffer.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v22 = new Vector3f(0.062500F, 0.343750F, 0.062500F);
        Vector3f v18 = new Vector3f(-0.025000F, 1.431250F, -0.025000F);
        Vector3f v77 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v76 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v13 = new Vector3f(0.025000F, 1.431250F, 0.025000F);
        Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v10 = new Vector3f(-0.031250F, 1.437500F, 0.031250F);
        Vector3f v24 = new Vector3f(0.062500F, 1.468750F, -0.062500F);
        Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v17 = new Vector3f(0.025000F, 0.381250F, -0.025000F);
        Vector3f v12 = new Vector3f(-0.025000F, 1.431250F, 0.025000F);
        Vector3f v78 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v25 = new Vector3f(0.062500F, 0.343750F, -0.062500F);
        Vector3f v8 = new Vector3f(0.031250F, 1.437500F, -0.031250F);
        Vector3f v74 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v15 = new Vector3f(-0.025000F, 0.381250F, 0.025000F);
        Vector3f v20 = new Vector3f(-0.062500F, 1.468750F, 0.062500F);
        Vector3f v9 = new Vector3f(-0.031250F, 1.437500F, -0.031250F);
        Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v11 = new Vector3f(0.031250F, 1.437500F, 0.031250F);
        Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v75 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v23 = new Vector3f(-0.062500F, 0.343750F, 0.062500F);
        Vector3f v79 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v16 = new Vector3f(0.025000F, 1.431250F, -0.025000F);
        Vector3f v14 = new Vector3f(0.025000F, 0.381250F, 0.025000F);
        Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v21 = new Vector3f(0.062500F, 1.468750F, 0.062500F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v26 = new Vector3f(-0.062500F, 1.468750F, -0.062500F);
        Vector3f v27 = new Vector3f(-0.062500F, 0.343750F, -0.062500F);
        Vector3f v19 = new Vector3f(-0.025000F, 0.381250F, -0.025000F);
        Vector2f v47 = new Vector2f(0.12500F, 0.78125F);
        Vector2f v68 = new Vector2f(0.25000F, 0.81250F);
        Vector2f v43 = new Vector2f(0.21875F, 0.78125F);
        Vector2f v52 = new Vector2f(0.21875F, 0.21875F);
        Vector2f v48 = new Vector2f(0.12500F, 0.25000F);
        Vector2f v37 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v35 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v49 = new Vector2f(0.18750F, 0.25000F);
        Vector2f v62 = new Vector2f(0.43750F, 0.25000F);
        Vector2f v54 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v46 = new Vector2f(0.15625F, 0.78125F);
        Vector2f v42 = new Vector2f(0.25000F, 0.78125F);
        Vector2f v31 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v53 = new Vector2f(0.15625F, 0.21875F);
        Vector2f v56 = new Vector2f(0.00000F, 0.25000F);
        Vector2f v55 = new Vector2f(0.09375F, 0.78125F);
        Vector2f v32 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v58 = new Vector2f(0.03125F, 0.78125F);
        Vector2f v73 = new Vector2f(0.43750F, 0.18750F);
        Vector2f v72 = new Vector2f(0.37500F, 0.18750F);
        Vector2f v60 = new Vector2f(0.06250F, 0.25000F);
        Vector2f v70 = new Vector2f(0.37500F, 0.81250F);
        Vector2f v63 = new Vector2f(0.50000F, 0.25000F);
        Vector2f v28 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v38 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v67 = new Vector2f(0.31250F, 0.81250F);
        Vector2f v34 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v57 = new Vector2f(0.03125F, 0.25000F);
        Vector2f v66 = new Vector2f(0.31250F, 0.25000F);
        Vector2f v65 = new Vector2f(0.43750F, 0.81250F);
        Vector2f v64 = new Vector2f(0.50000F, 0.81250F);
        Vector2f v40 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v69 = new Vector2f(0.37500F, 0.25000F);
        Vector2f v45 = new Vector2f(0.15625F, 0.25000F);
        Vector2f v61 = new Vector2f(0.06250F, 0.78125F);
        Vector2f v41 = new Vector2f(0.25000F, 0.25000F);
        Vector2f v51 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v30 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v36 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v71 = new Vector2f(0.31250F, 0.18750F);
        Vector2f v44 = new Vector2f(0.21875F, 0.25000F);
        Vector2f v59 = new Vector2f(0.00000F, 0.78125F);
        Vector2f v50 = new Vector2f(0.18750F, 0.78125F);
        Vector2f v33 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v39 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v29 = new Vector2f(0.03125F, 0.03125F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v31, v74, v2, c0, v30, v74, v1, c0, v29, v74, v0, c0, v28, v74));
        builder.add(new SimpleQuad(v2, c0, v30, v75, v5, c0, v33, v75, v4, c0, v32, v75, v1, c0, v29, v75));
        builder.add(new SimpleQuad(v5, c0, v33, v76, v7, c0, v35, v76, v6, c0, v34, v76, v4, c0, v32, v76));
        builder.add(new SimpleQuad(v5, c0, v34, v77, v2, c0, v32, v77, v3, c0, v37, v77, v7, c0, v36, v77));
        builder.add(new SimpleQuad(v7, c0, v35, v78, v3, c0, v39, v78, v0, c0, v38, v78, v6, c0, v34, v78));
        HILT_QUADS = builder.build();

        // Hilt2
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c1, v40, v79, v1, c1, v29, v79, v4, c1, v32, v79, v6, c1, v37, v79));
        HILT_2_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v9, c2, v44, v78, v4, c2, v43, v78, v1, c2, v42, v78, v8, c2, v41, v78));
        builder.add(new SimpleQuad(v10, c2, v48, v74, v6, c2, v47, v74, v4, c2, v46, v74, v9, c2, v45, v74));
        builder.add(new SimpleQuad(v11, c2, v45, v75, v0, c2, v46, v75, v6, c2, v50, v75, v10, c2, v49, v75));
        builder.add(new SimpleQuad(v8, c2, v49, v76, v1, c2, v50, v76, v0, c2, v43, v76, v11, c2, v44, v76));
        builder.add(new SimpleQuad(v10, c2, v52, v77, v9, c2, v44, v77, v8, c2, v49, v77, v11, c2, v51, v77));
        builder.add(new SimpleQuad(v0, c2, v53, v79, v1, c2, v45, v79, v4, c2, v49, v79, v6, c2, v51, v79));
        BLADE_MID_QUADS = builder.build();

        // BladeIn
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c3, v55, v78, v14, c3, v47, v78, v13, c3, v48, v78, v12, c3, v54, v78));
        builder.add(new SimpleQuad(v14, c3, v59, v74, v17, c3, v58, v74, v16, c3, v57, v74, v13, c3, v56, v74));
        builder.add(new SimpleQuad(v17, c3, v58, v75, v19, c3, v61, v75, v18, c3, v60, v75, v16, c3, v57, v75));
        builder.add(new SimpleQuad(v19, c3, v61, v76, v15, c3, v55, v76, v12, c3, v54, v76, v18, c3, v60, v76));
        builder.add(new SimpleQuad(v12, c3, v33, v79, v13, c3, v30, v79, v16, c3, v57, v79, v18, c3, v60, v79));
        builder.add(new SimpleQuad(v15, c3, v35, v77, v19, c3, v54, v77, v17, c3, v60, v77, v14, c3, v33, v77));
        BLADE_IN_QUADS = builder.build();

        // BladeOut
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c4, v65, v78, v22, c4, v64, v78, v21, c4, v63, v78, v20, c4, v62, v78));
        builder.add(new SimpleQuad(v22, c4, v68, v74, v25, c4, v67, v74, v24, c4, v66, v74, v21, c4, v41, v74));
        builder.add(new SimpleQuad(v25, c4, v67, v75, v27, c4, v70, v75, v26, c4, v69, v75, v24, c4, v66, v75));
        builder.add(new SimpleQuad(v27, c4, v70, v76, v23, c4, v65, v76, v20, c4, v62, v76, v26, c4, v69, v76));
        builder.add(new SimpleQuad(v20, c4, v72, v79, v21, c4, v71, v79, v24, c4, v66, v79, v26, c4, v69, v79));
        builder.add(new SimpleQuad(v22, c4, v72, v77, v23, c4, v73, v77, v27, c4, v62, v77, v25, c4, v69, v77));
        BLADE_OUT_QUADS = builder.build();
    }
}
