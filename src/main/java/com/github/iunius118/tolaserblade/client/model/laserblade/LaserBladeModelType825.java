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

public class LaserBladeModelType825 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_825.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
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
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.outerColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v97 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v20 = new Vector3f(-0.187500F, 3.468750F, 0.187500F);
        Vector3f v9 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v32 = new Vector3f(0.281250F, 3.562500F, -0.281250F);
        Vector3f v25 = new Vector3f(0.187500F, 0.375000F, -0.187500F);
        Vector3f v31 = new Vector3f(-0.281250F, 0.375000F, 0.281250F);
        Vector3f v33 = new Vector3f(0.281250F, 0.375000F, -0.281250F);
        Vector3f v29 = new Vector3f(0.281250F, 3.562500F, 0.281250F);
        Vector3f v4 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v22 = new Vector3f(0.187500F, 0.375000F, 0.187500F);
        Vector3f v30 = new Vector3f(0.281250F, 0.375000F, 0.281250F);
        Vector3f v98 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v5 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector3f v101 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v100 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v0 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v7 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v10 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v11 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v21 = new Vector3f(0.187500F, 3.468750F, 0.187500F);
        Vector3f v28 = new Vector3f(-0.281250F, 3.562500F, 0.281250F);
        Vector3f v18 = new Vector3f(0.093750F, 3.375000F, -0.093750F);
        Vector3f v96 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v14 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v27 = new Vector3f(-0.187500F, 0.375000F, -0.187500F);
        Vector3f v35 = new Vector3f(-0.281250F, 0.375000F, -0.281250F);
        Vector3f v8 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v34 = new Vector3f(-0.281250F, 3.562500F, -0.281250F);
        Vector3f v3 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v23 = new Vector3f(-0.187500F, 0.375000F, 0.187500F);
        Vector3f v19 = new Vector3f(-0.093750F, 3.375000F, -0.093750F);
        Vector3f v16 = new Vector3f(-0.093750F, 3.375000F, 0.093750F);
        Vector3f v26 = new Vector3f(-0.187500F, 3.468750F, -0.187500F);
        Vector3f v99 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v2 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v24 = new Vector3f(0.187500F, 3.468750F, -0.187500F);
        Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v17 = new Vector3f(0.093750F, 3.375000F, 0.093750F);
        Vector3f v13 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vec2f v82 = new Vec2f(0.25000F, 0.15625F);
        Vec2f v45 = new Vec2f(0.31250F, 0.12500F);
        Vec2f v43 = new Vec2f(0.12500F, 0.12500F);
        Vec2f v66 = new Vec2f(0.00000F, 0.21875F);
        Vec2f v40 = new Vec2f(0.12500F, 0.09375F);
        Vec2f v72 = new Vec2f(0.31250F, 0.21875F);
        Vec2f v84 = new Vec2f(0.65625F, 0.21875F);
        Vec2f v92 = new Vec2f(0.56250F, 0.78125F);
        Vec2f v58 = new Vec2f(0.21875F, 0.00000F);
        Vec2f v65 = new Vec2f(0.09375F, 0.71875F);
        Vec2f v38 = new Vec2f(0.50000F, 0.12500F);
        Vec2f v48 = new Vec2f(0.00000F, 0.03125F);
        Vec2f v93 = new Vec2f(0.46875F, 0.12500F);
        Vec2f v91 = new Vec2f(0.56250F, 0.21875F);
        Vec2f v87 = new Vec2f(0.65625F, 0.78125F);
        Vec2f v55 = new Vec2f(0.09375F, 0.18750F);
        Vec2f v68 = new Vec2f(0.03125F, 0.71875F);
        Vec2f v53 = new Vec2f(0.06250F, 0.18750F);
        Vec2f v47 = new Vec2f(0.31250F, 0.00000F);
        Vec2f v85 = new Vec2f(0.75000F, 0.21875F);
        Vec2f v90 = new Vec2f(0.37500F, 0.78125F);
        Vec2f v86 = new Vec2f(0.75000F, 0.78125F);
        Vec2f v88 = new Vec2f(0.46875F, 0.21875F);
        Vec2f v73 = new Vec2f(0.37500F, 0.21875F);
        Vec2f v75 = new Vec2f(0.31250F, 0.75000F);
        Vec2f v83 = new Vec2f(0.31250F, 0.15625F);
        Vec2f v77 = new Vec2f(0.18750F, 0.75000F);
        Vec2f v81 = new Vec2f(0.18750F, 0.15625F);
        Vec2f v52 = new Vec2f(0.06250F, 0.03125F);
        Vec2f v39 = new Vec2f(0.40625F, 0.12500F);
        Vec2f v80 = new Vec2f(0.25000F, 0.75000F);
        Vec2f v79 = new Vec2f(0.25000F, 0.21875F);
        Vec2f v50 = new Vec2f(0.03125F, 0.18750F);
        Vec2f v61 = new Vec2f(0.03125F, 0.00000F);
        Vec2f v78 = new Vec2f(0.12500F, 0.75000F);
        Vec2f v49 = new Vec2f(0.03125F, 0.03125F);
        Vec2f v76 = new Vec2f(0.18750F, 0.21875F);
        Vec2f v63 = new Vec2f(0.12500F, 0.21875F);
        Vec2f v69 = new Vec2f(0.00000F, 0.71875F);
        Vec2f v74 = new Vec2f(0.37500F, 0.75000F);
        Vec2f v71 = new Vec2f(0.06250F, 0.71875F);
        Vec2f v95 = new Vec2f(0.65625F, 0.12500F);
        Vec2f v62 = new Vec2f(0.09375F, 0.21875F);
        Vec2f v89 = new Vec2f(0.46875F, 0.78125F);
        Vec2f v42 = new Vec2f(0.21875F, 0.12500F);
        Vec2f v51 = new Vec2f(0.00000F, 0.18750F);
        Vec2f v94 = new Vec2f(0.56250F, 0.12500F);
        Vec2f v60 = new Vec2f(0.12500F, 0.18750F);
        Vec2f v46 = new Vec2f(0.40625F, 0.00000F);
        Vec2f v36 = new Vec2f(0.40625F, 0.09375F);
        Vec2f v54 = new Vec2f(0.09375F, 0.03125F);
        Vec2f v67 = new Vec2f(0.03125F, 0.21875F);
        Vec2f v44 = new Vec2f(0.31250F, 0.09375F);
        Vec2f v59 = new Vec2f(0.12500F, 0.03125F);
        Vec2f v37 = new Vec2f(0.50000F, 0.09375F);
        Vec2f v41 = new Vec2f(0.21875F, 0.09375F);
        Vec2f v64 = new Vec2f(0.12500F, 0.71875F);
        Vec2f v70 = new Vec2f(0.06250F, 0.21875F);
        Vec2f v56 = new Vec2f(0.09375F, 0.00000F);
        Vec2f v57 = new Vec2f(0.06250F, 0.00000F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v39, v96, v2, c0, v38, v96, v1, c0, v37, v96, v0, c0, v36, v96));
        builder.add(new SimpleQuad(v2, c0, v43, v97, v5, c0, v42, v97, v4, c0, v41, v97, v1, c0, v40, v97));
        builder.add(new SimpleQuad(v5, c0, v42, v98, v7, c0, v45, v98, v6, c0, v44, v98, v4, c0, v41, v98));
        builder.add(new SimpleQuad(v7, c0, v45, v99, v3, c0, v39, v99, v0, c0, v36, v99, v6, c0, v44, v99));
        builder.add(new SimpleQuad(v7, c0, v36, v100, v5, c0, v44, v100, v2, c0, v47, v100, v3, c0, v46, v100));
        builder.add(new SimpleQuad(v11, c0, v51, v97, v10, c0, v50, v97, v9, c0, v49, v97, v8, c0, v48, v97));
        builder.add(new SimpleQuad(v10, c0, v50, v98, v13, c0, v53, v98, v12, c0, v52, v98, v9, c0, v49, v98));
        builder.add(new SimpleQuad(v13, c0, v53, v99, v15, c0, v55, v99, v14, c0, v54, v99, v12, c0, v52, v99));
        builder.add(new SimpleQuad(v13, c0, v54, v100, v10, c0, v52, v100, v11, c0, v57, v100, v15, c0, v56, v100));
        builder.add(new SimpleQuad(v0, c0, v47, v101, v1, c0, v58, v101, v4, c0, v41, v101, v6, c0, v44, v101));
        builder.add(new SimpleQuad(v15, c0, v55, v96, v11, c0, v60, v96, v8, c0, v59, v96, v14, c0, v54, v96));
        builder.add(new SimpleQuad(v9, c0, v49, v101, v12, c0, v52, v101, v14, c0, v57, v101, v8, c0, v61, v101));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c1, v65, v96, v1, c1, v64, v96, v17, c1, v63, v96, v16, c1, v62, v96));
        builder.add(new SimpleQuad(v1, c1, v69, v97, v4, c1, v68, v97, v18, c1, v67, v97, v17, c1, v66, v97));
        builder.add(new SimpleQuad(v4, c1, v68, v98, v6, c1, v71, v98, v19, c1, v70, v98, v18, c1, v67, v98));
        builder.add(new SimpleQuad(v6, c1, v71, v99, v0, c1, v65, v99, v16, c1, v62, v99, v19, c1, v70, v99));
        builder.add(new SimpleQuad(v16, c1, v53, v101, v17, c1, v50, v101, v18, c1, v67, v101, v19, c1, v70, v101));
        builder.add(new SimpleQuad(v0, c1, v55, v100, v6, c1, v62, v100, v4, c1, v70, v100, v1, c1, v53, v100));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c2, v75, v96, v22, c2, v74, v96, v21, c2, v73, v96, v20, c2, v72, v96));
        builder.add(new SimpleQuad(v22, c2, v78, v97, v25, c2, v77, v97, v24, c2, v76, v97, v21, c2, v63, v97));
        builder.add(new SimpleQuad(v25, c2, v77, v98, v27, c2, v80, v98, v26, c2, v79, v98, v24, c2, v76, v98));
        builder.add(new SimpleQuad(v27, c2, v80, v99, v23, c2, v75, v99, v20, c2, v72, v99, v26, c2, v79, v99));
        builder.add(new SimpleQuad(v20, c2, v82, v101, v21, c2, v81, v101, v24, c2, v76, v101, v26, c2, v79, v101));
        builder.add(new SimpleQuad(v22, c2, v82, v100, v23, c2, v83, v100, v27, c2, v72, v100, v25, c2, v79, v100));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v31, c3, v87, v96, v30, c3, v86, v96, v29, c3, v85, v96, v28, c3, v84, v96));
        builder.add(new SimpleQuad(v30, c3, v90, v97, v33, c3, v89, v97, v32, c3, v88, v97, v29, c3, v73, v97));
        builder.add(new SimpleQuad(v33, c3, v89, v98, v35, c3, v92, v98, v34, c3, v91, v98, v32, c3, v88, v98));
        builder.add(new SimpleQuad(v35, c3, v92, v99, v31, c3, v87, v99, v28, c3, v84, v99, v34, c3, v91, v99));
        builder.add(new SimpleQuad(v28, c3, v94, v101, v29, c3, v93, v101, v32, c3, v88, v101, v34, c3, v91, v101));
        builder.add(new SimpleQuad(v35, c3, v84, v100, v33, c3, v91, v100, v30, c3, v94, v100, v31, c3, v95, v100));
        BLADE_OUT_QUADS = builder.build();
    }
}
