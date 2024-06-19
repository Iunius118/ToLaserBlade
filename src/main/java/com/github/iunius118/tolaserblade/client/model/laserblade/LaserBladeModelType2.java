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

public class LaserBladeModelType2 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_2.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);

        if (color.isBroken) {
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrices, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_MID_QUADS, color.outerColor, fullLight, overlay);

        currentBuffer = vertexConsumers.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrices, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlay);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // Hilt color
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.25F); // BladeOut color
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F); // BladeMid color
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.9F); // BladeIn color

        Vector3f v34 = new Vector3f(0.062000F, 1.395000F, 0.062000F);
        Vector3f v27 = new Vector3f(-0.031000F, 0.372000F, -0.031000F);
        Vector3f v116 = new Vector3f(-0.06226F, 0.99612F, 0.06226F);
        Vector3f v31 = new Vector3f(0.062000F, 0.379750F, -0.062000F);
        Vector3f v109 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v8 = new Vector3f(0.031000F, 0.310000F, 0.031000F);
        Vector3f v13 = new Vector3f(-0.031000F, 0.000000F, -0.031000F);
        Vector3f v9 = new Vector3f(0.031000F, 0.310000F, -0.031000F);
        Vector3f v121 = new Vector3f(0.12309F, 0.98473F, 0.12309F);
        Vector3f v108 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v32 = new Vector3f(-0.062000F, 1.395000F, 0.062000F);
        Vector3f v120 = new Vector3f(-0.12309F, 0.98473F, 0.12309F);
        Vector3f v15 = new Vector3f(-0.031000F, 0.000000F, 0.031000F);
        Vector3f v119 = new Vector3f(-0.12309F, 0.98473F, -0.12309F);
        Vector3f v29 = new Vector3f(-0.062000F, 1.395000F, -0.062000F);
        Vector3f v0 = new Vector3f(-0.093000F, 0.372000F, 0.093000F);
        Vector3f v37 = new Vector3f(0.031000F, 1.364000F, 0.031000F);
        Vector3f v118 = new Vector3f(0.12309F, 0.98473F, -0.12309F);
        Vector3f v3 = new Vector3f(-0.093000F, 0.310000F, 0.093000F);
        Vector3f v7 = new Vector3f(-0.093000F, 0.310000F, -0.093000F);
        Vector3f v14 = new Vector3f(-0.031000F, 0.310000F, 0.031000F);
        Vector3f v4 = new Vector3f(0.093000F, 0.372000F, -0.093000F);
        Vector3f v117 = new Vector3f(0.06226F, 0.99612F, 0.06226F);
        Vector3f v30 = new Vector3f(-0.062000F, 0.379750F, -0.062000F);
        Vector3f v28 = new Vector3f(0.062000F, 1.395000F, -0.062000F);
        Vector3f v115 = new Vector3f(-0.06226F, 0.99612F, -0.06226F);
        Vector3f v39 = new Vector3f(-0.031000F, 1.364000F, -0.031000F);
        Vector3f v19 = new Vector3f(0.093000F, 0.379750F, -0.093000F);
        Vector3f v114 = new Vector3f(0.06226F, 0.99612F, -0.06226F);
        Vector3f v21 = new Vector3f(-0.093000F, 0.379750F, 0.093000F);
        Vector3f v35 = new Vector3f(0.062000F, 0.379750F, 0.062000F);
        Vector3f v36 = new Vector3f(-0.031000F, 1.364000F, 0.031000F);
        Vector3f v113 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v112 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v111 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v12 = new Vector3f(-0.031000F, 0.310000F, -0.031000F);
        Vector3f v110 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v38 = new Vector3f(0.031000F, 1.364000F, -0.031000F);
        Vector3f v17 = new Vector3f(-0.093000F, 1.426000F, -0.093000F);
        Vector3f v33 = new Vector3f(-0.062000F, 0.379750F, 0.062000F);
        Vector3f v6 = new Vector3f(-0.093000F, 0.372000F, -0.093000F);
        Vector3f v25 = new Vector3f(0.031000F, 0.372000F, 0.031000F);
        Vector3f v23 = new Vector3f(0.093000F, 0.379750F, 0.093000F);
        Vector3f v18 = new Vector3f(-0.093000F, 0.379750F, -0.093000F);
        Vector3f v5 = new Vector3f(0.093000F, 0.310000F, -0.093000F);
        Vector3f v11 = new Vector3f(0.031000F, 0.000000F, 0.031000F);
        Vector3f v2 = new Vector3f(0.093000F, 0.310000F, 0.093000F);
        Vector3f v1 = new Vector3f(0.093000F, 0.372000F, 0.093000F);
        Vector3f v20 = new Vector3f(-0.093000F, 1.426000F, 0.093000F);
        Vector3f v26 = new Vector3f(0.031000F, 0.372000F, -0.031000F);
        Vector3f v22 = new Vector3f(0.093000F, 1.426000F, 0.093000F);
        Vector3f v16 = new Vector3f(0.093000F, 1.426000F, -0.093000F);
        Vector3f v24 = new Vector3f(-0.031000F, 0.372000F, 0.031000F);
        Vector3f v10 = new Vector3f(0.031000F, 0.000000F, -0.031000F);
        Vector2f v40 = new Vector2f(0.40625F, 0.09375F);
        Vector2f v92 = new Vector2f(0.25000F, 0.15625F);
        Vector2f v85 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v59 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v88 = new Vector2f(0.12500F, 0.75000F);
        Vector2f v102 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v66 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v64 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v62 = new Vector2f(0.21875F, 0.00000F);
        Vector2f v52 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v77 = new Vector2f(0.46875F, 0.12500F);
        Vector2f v78 = new Vector2f(0.62500F, 0.15625F);
        Vector2f v45 = new Vector2f(0.21875F, 0.09375F);
        Vector2f v56 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v49 = new Vector2f(0.31250F, 0.12500F);
        Vector2f v101 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v61 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v90 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v44 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v71 = new Vector2f(0.46875F, 0.78125F);
        Vector2f v51 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v42 = new Vector2f(0.50000F, 0.12500F);
        Vector2f v53 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v72 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v68 = new Vector2f(0.65625F, 0.78125F);
        Vector2f v104 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v97 = new Vector2f(0.265625F, 0.203125F);
        Vector2f v86 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v60 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v55 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v98 = new Vector2f(0.296875F, 0.203125F);
        Vector2f v47 = new Vector2f(0.12500F, 0.12500F);
        Vector2f v74 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v89 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v81 = new Vector2f(0.59375F, 0.18750F);
        Vector2f v73 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v105 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v63 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v48 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v43 = new Vector2f(0.40625F, 0.12500F);
        Vector2f v83 = new Vector2f(0.37500F, 0.75000F);
        Vector2f v107 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v106 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v103 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v79 = new Vector2f(0.59375F, 0.15625F);
        Vector2f v57 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v95 = new Vector2f(0.265625F, 0.171875F);
        Vector2f v87 = new Vector2f(0.18750F, 0.75000F);
        Vector2f v84 = new Vector2f(0.31250F, 0.75000F);
        Vector2f v75 = new Vector2f(0.56250F, 0.78125F);
        Vector2f v100 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v99 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v96 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v50 = new Vector2f(0.40625F, 0.00000F);
        Vector2f v94 = new Vector2f(0.296875F, 0.171875F);
        Vector2f v69 = new Vector2f(0.65625F, 0.21875F);
        Vector2f v93 = new Vector2f(0.18750F, 0.15625F);
        Vector2f v91 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v67 = new Vector2f(0.75000F, 0.78125F);
        Vector2f v82 = new Vector2f(0.62500F, 0.18750F);
        Vector2f v65 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v46 = new Vector2f(0.21875F, 0.12500F);
        Vector2f v41 = new Vector2f(0.50000F, 0.09375F);
        Vector2f v70 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v80 = new Vector2f(0.65625F, 0.12500F);
        Vector2f v76 = new Vector2f(0.56250F, 0.12500F);
        Vector2f v54 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v58 = new Vector2f(0.09375F, 0.03125F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v43, v108, v2, c0, v42, v108, v1, c0, v41, v108, v0, c0, v40, v108));
        builder.add(new SimpleQuad(v2, c0, v47, v109, v5, c0, v46, v109, v4, c0, v45, v109, v1, c0, v44, v109));
        builder.add(new SimpleQuad(v5, c0, v46, v110, v7, c0, v49, v110, v6, c0, v48, v110, v4, c0, v45, v110));
        builder.add(new SimpleQuad(v7, c0, v49, v111, v3, c0, v43, v111, v0, c0, v40, v111, v6, c0, v48, v111));
        builder.add(new SimpleQuad(v7, c0, v40, v112, v5, c0, v48, v112, v2, c0, v51, v112, v3, c0, v50, v112));
        builder.add(new SimpleQuad(v11, c0, v55, v109, v10, c0, v54, v109, v9, c0, v53, v109, v8, c0, v52, v109));
        builder.add(new SimpleQuad(v10, c0, v54, v110, v13, c0, v57, v110, v12, c0, v56, v110, v9, c0, v53, v110));
        builder.add(new SimpleQuad(v13, c0, v57, v111, v15, c0, v59, v111, v14, c0, v58, v111, v12, c0, v56, v111));
        builder.add(new SimpleQuad(v13, c0, v58, v112, v10, c0, v56, v112, v11, c0, v61, v112, v15, c0, v60, v112));
        builder.add(new SimpleQuad(v0, c0, v51, v113, v1, c0, v62, v113, v4, c0, v45, v113, v6, c0, v48, v113));
        builder.add(new SimpleQuad(v15, c0, v59, v108, v11, c0, v64, v108, v8, c0, v63, v108, v14, c0, v58, v108));
        builder.add(new SimpleQuad(v9, c0, v53, v113, v12, c0, v56, v113, v14, c0, v61, v113, v8, c0, v65, v113));
        HILT_QUADS = builder.build();

        // BladeOut
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v17, c1, v69, v108, v18, c1, v68, v108, v19, c1, v67, v108, v16, c1, v66, v108));
        builder.add(new SimpleQuad(v20, c1, v73, v109, v21, c1, v72, v109, v18, c1, v71, v109, v17, c1, v70, v109));
        builder.add(new SimpleQuad(v22, c1, v70, v110, v23, c1, v71, v110, v21, c1, v75, v110, v20, c1, v74, v110));
        builder.add(new SimpleQuad(v16, c1, v74, v111, v19, c1, v75, v111, v23, c1, v68, v111, v22, c1, v69, v111));
        builder.add(new SimpleQuad(v17, c1, v74, v112, v16, c1, v70, v112, v22, c1, v77, v112, v20, c1, v76, v112));
        builder.add(new SimpleQuad(v21, c1, v80, v114, v23, c1, v76, v115, v25, c1, v79, v115, v24, c1, v78, v114));
        builder.add(new SimpleQuad(v23, c1, v76, v115, v19, c1, v74, v116, v26, c1, v81, v116, v25, c1, v79, v115));
        builder.add(new SimpleQuad(v19, c1, v74, v116, v18, c1, v69, v117, v27, c1, v82, v117, v26, c1, v81, v116));
        builder.add(new SimpleQuad(v18, c1, v69, v117, v21, c1, v80, v114, v24, c1, v78, v114, v27, c1, v82, v117));
        BLADE_OUT_QUADS = builder.build();

        // BladeMid
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v29, c2, v85, v108, v30, c2, v84, v108, v31, c2, v83, v108, v28, c2, v73, v108));
        builder.add(new SimpleQuad(v32, c2, v89, v109, v33, c2, v88, v109, v30, c2, v87, v109, v29, c2, v86, v109));
        builder.add(new SimpleQuad(v34, c2, v86, v110, v35, c2, v87, v110, v33, c2, v91, v110, v32, c2, v90, v110));
        builder.add(new SimpleQuad(v28, c2, v90, v111, v31, c2, v91, v111, v35, c2, v84, v111, v34, c2, v85, v111));
        builder.add(new SimpleQuad(v29, c2, v90, v112, v28, c2, v86, v112, v34, c2, v93, v112, v32, c2, v92, v112));
        builder.add(new SimpleQuad(v33, c2, v96, v118, v35, c2, v92, v119, v25, c2, v95, v119, v24, c2, v94, v118));
        builder.add(new SimpleQuad(v35, c2, v92, v119, v31, c2, v90, v120, v26, c2, v97, v120, v25, c2, v95, v119));
        builder.add(new SimpleQuad(v31, c2, v90, v120, v30, c2, v85, v121, v27, c2, v98, v121, v26, c2, v97, v120));
        builder.add(new SimpleQuad(v30, c2, v85, v121, v33, c2, v96, v118, v24, c2, v94, v118, v27, c2, v98, v121));
        BLADE_MID_QUADS = builder.build();

        // BladeIn
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v24, c3, v101, v108, v25, c3, v100, v108, v37, c3, v89, v108, v36, c3, v99, v108));
        builder.add(new SimpleQuad(v25, c3, v105, v109, v26, c3, v104, v109, v38, c3, v103, v109, v37, c3, v102, v109));
        builder.add(new SimpleQuad(v26, c3, v104, v110, v27, c3, v107, v110, v39, c3, v106, v110, v38, c3, v103, v110));
        builder.add(new SimpleQuad(v27, c3, v107, v111, v24, c3, v101, v111, v36, c3, v99, v111, v39, c3, v106, v111));
        builder.add(new SimpleQuad(v37, c3, v54, v113, v38, c3, v103, v113, v39, c3, v106, v113, v36, c3, v57, v113));
        BLADE_IN_QUADS = builder.build();
    }
}
