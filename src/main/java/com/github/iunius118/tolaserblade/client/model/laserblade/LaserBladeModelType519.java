package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.color.LaserBladeItemColor;
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

public class LaserBladeModelType519 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_519.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        if (transformType == ItemCameraTransforms.TransformType.FIXED || transformType == ItemCameraTransforms.TransformType.GUI) {
            matrixStack.translate(0.0D, 0.546875D, 0.0D);
        }

        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(getHiltRenderType());
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = buffer.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = buffer.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v12 = new Vector3f(0.031250F, -1.125000F, 0.031250F);
        Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v17 = new Vector3f(0.062500F, 1.468750F, 0.062500F);
        Vector3f v20 = new Vector3f(0.062500F, 1.468750F, -0.062500F);
        Vector3f v31 = new Vector3f(-0.062500F, -1.156250F, -0.062500F);
        Vector3f v11 = new Vector3f(-0.031250F, 1.437500F, -0.031250F);
        Vector3f v7 = new Vector3f(-0.031250F, -0.062500F, 0.031250F);
        Vector3f v8 = new Vector3f(-0.031250F, 1.437500F, 0.031250F);
        Vector3f v13 = new Vector3f(-0.031250F, -1.125000F, 0.031250F);
        Vector3f v2 = new Vector3f(0.031250F, -0.062500F, -0.031250F);
        Vector3f v5 = new Vector3f(-0.031250F, -0.062500F, -0.031250F);
        Vector3f v29 = new Vector3f(0.062500F, -1.156250F, -0.062500F);
        Vector3f v94 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v22 = new Vector3f(-0.062500F, 1.468750F, -0.062500F);
        Vector3f v9 = new Vector3f(0.031250F, 1.437500F, 0.031250F);
        Vector3f v26 = new Vector3f(0.062500F, -1.156250F, 0.062500F);
        Vector3f v93 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v27 = new Vector3f(-0.062500F, -1.156250F, 0.062500F);
        Vector3f v10 = new Vector3f(0.031250F, 1.437500F, -0.031250F);
        Vector3f v3 = new Vector3f(0.031250F, -0.062500F, 0.031250F);
        Vector3f v18 = new Vector3f(0.062500F, 0.343750F, 0.062500F);
        Vector3f v95 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v19 = new Vector3f(-0.062500F, 0.343750F, 0.062500F);
        Vector3f v23 = new Vector3f(-0.062500F, 0.343750F, -0.062500F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v16 = new Vector3f(-0.062500F, 1.468750F, 0.062500F);
        Vector3f v96 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v92 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v91 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v14 = new Vector3f(0.031250F, -1.125000F, -0.031250F);
        Vector3f v28 = new Vector3f(0.062500F, -0.031250F, -0.062500F);
        Vector3f v15 = new Vector3f(-0.031250F, -1.125000F, -0.031250F);
        Vector3f v30 = new Vector3f(-0.062500F, -0.031250F, -0.062500F);
        Vector3f v21 = new Vector3f(0.062500F, 0.343750F, -0.062500F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v25 = new Vector3f(0.062500F, -0.031250F, 0.062500F);
        Vector3f v24 = new Vector3f(-0.062500F, -0.031250F, 0.062500F);
        Vector2f v37 = new Vector2f(0.06250F, 0.25000F);
        Vector2f v58 = new Vector2f(0.46875F, 0.81250F);
        Vector2f v44 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v80 = new Vector2f(0.75000F, 0.28125F);
        Vector2f v90 = new Vector2f(0.68750F, 0.21875F);
        Vector2f v72 = new Vector2f(0.18750F, 0.84375F);
        Vector2f v48 = new Vector2f(0.09375F, 0.81250F);
        Vector2f v89 = new Vector2f(0.62500F, 0.21875F);
        Vector2f v70 = new Vector2f(0.31250F, 0.84375F);
        Vector2f v50 = new Vector2f(0.03125F, 0.28125F);
        Vector2f v49 = new Vector2f(0.00000F, 0.28125F);
        Vector2f v68 = new Vector2f(0.31250F, 0.28125F);
        Vector2f v87 = new Vector2f(0.62500F, 0.84375F);
        Vector2f v56 = new Vector2f(0.50000F, 0.28125F);
        Vector2f v82 = new Vector2f(0.68750F, 0.84375F);
        Vector2f v86 = new Vector2f(0.62500F, 0.28125F);
        Vector2f v84 = new Vector2f(0.56250F, 0.84375F);
        Vector2f v34 = new Vector2f(0.03125F, 0.25000F);
        Vector2f v73 = new Vector2f(0.12500F, 0.84375F);
        Vector2f v52 = new Vector2f(0.00000F, 0.81250F);
        Vector2f v85 = new Vector2f(0.50000F, 0.84375F);
        Vector2f v51 = new Vector2f(0.03125F, 0.81250F);
        Vector2f v83 = new Vector2f(0.56250F, 0.28125F);
        Vector2f v35 = new Vector2f(0.00000F, 0.25000F);
        Vector2f v77 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v63 = new Vector2f(0.43750F, 0.28125F);
        Vector2f v61 = new Vector2f(0.40625F, 0.81250F);
        Vector2f v81 = new Vector2f(0.75000F, 0.84375F);
        Vector2f v74 = new Vector2f(0.25000F, 0.28125F);
        Vector2f v78 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v76 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v46 = new Vector2f(0.12500F, 0.28125F);
        Vector2f v38 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v79 = new Vector2f(0.68750F, 0.28125F);
        Vector2f v42 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v88 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v66 = new Vector2f(0.43750F, 0.25000F);
        Vector2f v69 = new Vector2f(0.37500F, 0.84375F);
        Vector2f v67 = new Vector2f(0.46875F, 0.25000F);
        Vector2f v65 = new Vector2f(0.40625F, 0.25000F);
        Vector2f v64 = new Vector2f(0.43750F, 0.81250F);
        Vector2f v43 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v41 = new Vector2f(0.12500F, 0.25000F);
        Vector2f v71 = new Vector2f(0.18750F, 0.28125F);
        Vector2f v62 = new Vector2f(0.37500F, 0.81250F);
        Vector2f v32 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v60 = new Vector2f(0.40625F, 0.28125F);
        Vector2f v59 = new Vector2f(0.37500F, 0.28125F);
        Vector2f v40 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v54 = new Vector2f(0.06250F, 0.81250F);
        Vector2f v39 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v45 = new Vector2f(0.09375F, 0.28125F);
        Vector2f v55 = new Vector2f(0.46875F, 0.28125F);
        Vector2f v53 = new Vector2f(0.06250F, 0.28125F);
        Vector2f v33 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v36 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v75 = new Vector2f(0.25000F, 0.84375F);
        Vector2f v57 = new Vector2f(0.50000F, 0.81250F);
        Vector2f v47 = new Vector2f(0.12500F, 0.81250F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v35, v91, v2, c0, v34, v91, v1, c0, v33, v91, v0, c0, v32, v91));
        builder.add(new SimpleQuad(v2, c0, v34, v92, v5, c0, v37, v92, v4, c0, v36, v92, v1, c0, v33, v92));
        builder.add(new SimpleQuad(v5, c0, v37, v93, v7, c0, v39, v93, v6, c0, v38, v93, v4, c0, v36, v93));
        builder.add(new SimpleQuad(v7, c0, v39, v94, v3, c0, v41, v94, v0, c0, v40, v94, v6, c0, v38, v94));
        builder.add(new SimpleQuad(v0, c0, v43, v95, v1, c0, v33, v95, v4, c0, v36, v95, v6, c0, v42, v95));
        builder.add(new SimpleQuad(v5, c0, v38, v96, v2, c0, v36, v96, v3, c0, v42, v96, v7, c0, v44, v96));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v6, c1, v48, v94, v0, c1, v47, v94, v9, c1, v46, v94, v8, c1, v45, v94));
        builder.add(new SimpleQuad(v0, c1, v52, v91, v1, c1, v51, v91, v10, c1, v50, v91, v9, c1, v49, v91));
        builder.add(new SimpleQuad(v1, c1, v51, v92, v4, c1, v54, v92, v11, c1, v53, v92, v10, c1, v50, v92));
        builder.add(new SimpleQuad(v4, c1, v54, v93, v6, c1, v48, v93, v8, c1, v45, v93, v11, c1, v53, v93));
        builder.add(new SimpleQuad(v8, c1, v37, v95, v9, c1, v34, v95, v10, c1, v50, v95, v11, c1, v53, v95));
        builder.add(new SimpleQuad(v6, c1, v39, v96, v4, c1, v45, v96, v1, c1, v53, v96, v0, c1, v37, v96));
        builder.add(new SimpleQuad(v13, c1, v58, v94, v12, c1, v57, v94, v3, c1, v56, v94, v7, c1, v55, v94));
        builder.add(new SimpleQuad(v12, c1, v62, v91, v14, c1, v61, v91, v2, c1, v60, v91, v3, c1, v59, v91));
        builder.add(new SimpleQuad(v14, c1, v61, v92, v15, c1, v64, v92, v5, c1, v63, v92, v2, c1, v60, v92));
        builder.add(new SimpleQuad(v15, c1, v64, v93, v13, c1, v58, v93, v7, c1, v55, v93, v5, c1, v63, v93));
        builder.add(new SimpleQuad(v7, c1, v66, v95, v3, c1, v65, v95, v2, c1, v60, v95, v5, c1, v63, v95));
        builder.add(new SimpleQuad(v13, c1, v67, v96, v15, c1, v55, v96, v14, c1, v63, v96, v12, c1, v66, v96));
        BLADE_IN_QUADS = builder.build();

        // BladeOut
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v19, c2, v70, v94, v18, c2, v69, v94, v17, c2, v59, v94, v16, c2, v68, v94));
        builder.add(new SimpleQuad(v18, c2, v73, v91, v21, c2, v72, v91, v20, c2, v71, v91, v17, c2, v46, v91));
        builder.add(new SimpleQuad(v21, c2, v72, v92, v23, c2, v75, v92, v22, c2, v74, v92, v20, c2, v71, v92));
        builder.add(new SimpleQuad(v23, c2, v75, v93, v19, c2, v70, v93, v16, c2, v68, v93, v22, c2, v74, v93));
        builder.add(new SimpleQuad(v16, c2, v77, v95, v17, c2, v76, v95, v20, c2, v71, v95, v22, c2, v74, v95));
        builder.add(new SimpleQuad(v18, c2, v77, v96, v19, c2, v78, v96, v23, c2, v68, v96, v21, c2, v74, v96));
        builder.add(new SimpleQuad(v27, c2, v82, v94, v26, c2, v81, v94, v25, c2, v80, v94, v24, c2, v79, v94));
        builder.add(new SimpleQuad(v26, c2, v85, v91, v29, c2, v84, v91, v28, c2, v83, v91, v25, c2, v56, v91));
        builder.add(new SimpleQuad(v29, c2, v84, v92, v31, c2, v87, v92, v30, c2, v86, v92, v28, c2, v83, v92));
        builder.add(new SimpleQuad(v31, c2, v87, v93, v27, c2, v82, v93, v24, c2, v79, v93, v30, c2, v86, v93));
        builder.add(new SimpleQuad(v24, c2, v89, v95, v25, c2, v88, v95, v28, c2, v83, v95, v30, c2, v86, v95));
        builder.add(new SimpleQuad(v26, c2, v89, v96, v27, c2, v90, v96, v31, c2, v79, v96, v29, c2, v86, v96));
        BLADE_OUT_QUADS = builder.build();
    }
}
