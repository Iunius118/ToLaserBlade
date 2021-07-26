package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LaserBladeModelType0 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_0.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);

        if (color.isBroken) {
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrices, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlay);

        currentBuffer = vertexConsumers.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrices, currentBuffer, BLADE_MID_QUADS, color.outerColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlay);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v30 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v97 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v5 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector3f v11 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v3 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v27 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
        Vector3f v18 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v16 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v0 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v29 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
        Vector3f v17 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v101 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v10 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v26 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
        Vector3f v100 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v4 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v96 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v7 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v28 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v20 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v35 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
        Vector3f v8 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v25 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v2 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v14 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v99 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v9 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v98 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v34 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
        Vector3f v33 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
        Vector3f v32 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
        Vector3f v21 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v22 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v24 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v13 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v23 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v31 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
        Vector3f v19 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector2f v55 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v51 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v48 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v95 = new Vector2f(0.65625F, 0.12500F);
        Vector2f v47 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v94 = new Vector2f(0.56250F, 0.12500F);
        Vector2f v46 = new Vector2f(0.40625F, 0.00000F);
        Vector2f v93 = new Vector2f(0.46875F, 0.12500F);
        Vector2f v52 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v89 = new Vector2f(0.46875F, 0.78125F);
        Vector2f v63 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v90 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v78 = new Vector2f(0.12500F, 0.75000F);
        Vector2f v60 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v50 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v41 = new Vector2f(0.21875F, 0.09375F);
        Vector2f v49 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v61 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v59 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v75 = new Vector2f(0.31250F, 0.75000F);
        Vector2f v92 = new Vector2f(0.56250F, 0.78125F);
        Vector2f v58 = new Vector2f(0.21875F, 0.00000F);
        Vector2f v88 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v87 = new Vector2f(0.65625F, 0.78125F);
        Vector2f v81 = new Vector2f(0.18750F, 0.15625F);
        Vector2f v71 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v85 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v54 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v73 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v83 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v82 = new Vector2f(0.25000F, 0.15625F);
        Vector2f v86 = new Vector2f(0.75000F, 0.78125F);
        Vector2f v62 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v76 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v91 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v57 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v65 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v79 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v43 = new Vector2f(0.12500F, 0.12500F);
        Vector2f v70 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v36 = new Vector2f(0.40625F, 0.09375F);
        Vector2f v44 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v39 = new Vector2f(0.40625F, 0.12500F);
        Vector2f v56 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v42 = new Vector2f(0.21875F, 0.12500F);
        Vector2f v84 = new Vector2f(0.65625F, 0.21875F);
        Vector2f v37 = new Vector2f(0.50000F, 0.09375F);
        Vector2f v72 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v69 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v68 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v67 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v66 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v64 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v80 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v40 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v53 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v38 = new Vector2f(0.50000F, 0.12500F);
        Vector2f v45 = new Vector2f(0.31250F, 0.12500F);
        Vector2f v74 = new Vector2f(0.37500F, 0.75000F);
        Vector2f v77 = new Vector2f(0.18750F, 0.75000F);
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
        builder.add(new SimpleQuad(v19, c1, v65, v96, v18, c1, v64, v96, v17, c1, v63, v96, v16, c1, v62, v96));
        builder.add(new SimpleQuad(v18, c1, v69, v97, v21, c1, v68, v97, v20, c1, v67, v97, v17, c1, v66, v97));
        builder.add(new SimpleQuad(v21, c1, v68, v98, v23, c1, v71, v98, v22, c1, v70, v98, v20, c1, v67, v98));
        builder.add(new SimpleQuad(v23, c1, v71, v99, v19, c1, v65, v99, v16, c1, v62, v99, v22, c1, v70, v99));
        builder.add(new SimpleQuad(v16, c1, v53, v101, v17, c1, v50, v101, v20, c1, v67, v101, v22, c1, v70, v101));
        builder.add(new SimpleQuad(v19, c1, v55, v100, v23, c1, v62, v100, v21, c1, v70, v100, v18, c1, v53, v100));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c2, v75, v96, v26, c2, v74, v96, v25, c2, v73, v96, v24, c2, v72, v96));
        builder.add(new SimpleQuad(v26, c2, v78, v97, v29, c2, v77, v97, v28, c2, v76, v97, v25, c2, v63, v97));
        builder.add(new SimpleQuad(v29, c2, v77, v98, v31, c2, v80, v98, v30, c2, v79, v98, v28, c2, v76, v98));
        builder.add(new SimpleQuad(v31, c2, v80, v99, v27, c2, v75, v99, v24, c2, v72, v99, v30, c2, v79, v99));
        builder.add(new SimpleQuad(v24, c2, v82, v101, v25, c2, v81, v101, v28, c2, v76, v101, v30, c2, v79, v101));
        builder.add(new SimpleQuad(v26, c2, v82, v100, v27, c2, v83, v100, v31, c2, v72, v100, v29, c2, v79, v100));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c3, v87, v96, v1, c3, v86, v96, v33, c3, v85, v96, v32, c3, v84, v96));
        builder.add(new SimpleQuad(v1, c3, v90, v97, v4, c3, v89, v97, v34, c3, v88, v97, v33, c3, v73, v97));
        builder.add(new SimpleQuad(v4, c3, v89, v98, v6, c3, v92, v98, v35, c3, v91, v98, v34, c3, v88, v98));
        builder.add(new SimpleQuad(v6, c3, v92, v99, v0, c3, v87, v99, v32, c3, v84, v99, v35, c3, v91, v99));
        builder.add(new SimpleQuad(v32, c3, v94, v101, v33, c3, v93, v101, v34, c3, v88, v101, v35, c3, v91, v101));
        builder.add(new SimpleQuad(v6, c3, v84, v100, v4, c3, v91, v100, v1, c3, v94, v100, v0, c3, v95, v100));
        BLADE_OUT_QUADS = builder.build();
    }
}
