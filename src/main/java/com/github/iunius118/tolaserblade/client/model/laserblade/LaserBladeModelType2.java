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

        Vector3f v106 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v13 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v23 = new Vector3f(0.093750F, 0.3828125F, 0.093750F);
        Vector3f v26 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v20 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
        Vector3f v28 = new Vector3f(0.062500F, 0.3828125F, -0.062500F);
        Vector3f v35 = new Vector3f(0.031250F, 0.3828125F, 0.031250F);
        Vector3f v5 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector3f v7 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v25 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v115 = new Vector3f(-0.12309F, 0.98473F, -0.12309F);
        Vector3f v104 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v33 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v113 = new Vector3f(0.08276F, 0.99313F, 0.08276F);
        Vector3f v14 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v30 = new Vector3f(-0.062500F, 0.3828125F, 0.062500F);
        Vector3f v19 = new Vector3f(0.093750F, 0.3828125F, -0.093750F);
        Vector3f v121 = new Vector3f(-0.23570F, -0.94281F, 0.23570F);
        Vector3f v38 = new Vector3f(0.031250F, 0.3828125F, -0.031250F);
        Vector3f v36 = new Vector3f(-0.031250F, 0.3828125F, 0.031250F);
        Vector3f v119 = new Vector3f(0.23570F, -0.94281F, -0.23570F);
        Vector3f v40 = new Vector3f(-0.031250F, 0.3828125F, -0.031250F);
        Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v10 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v116 = new Vector3f(-0.12309F, 0.98473F, 0.12309F);
        Vector3f v2 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v31 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v111 = new Vector3f(-0.08276F, 0.99313F, -0.08276F);
        Vector3f v24 = new Vector3f(0.000000F, 0.3671875F, 0.000000F);
        Vector3f v110 = new Vector3f(0.08276F, 0.99313F, -0.08276F);
        Vector3f v109 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v17 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
        Vector3f v27 = new Vector3f(-0.062500F, 0.3828125F, -0.062500F);
        Vector3f v22 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
        Vector3f v107 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v0 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v114 = new Vector3f(0.12309F, 0.98473F, -0.12309F);
        Vector3f v118 = new Vector3f(0.23570F, -0.94281F, 0.23570F);
        Vector3f v39 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v11 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v3 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v120 = new Vector3f(-0.23570F, -0.94281F, -0.23570F);
        Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v16 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
        Vector3f v34 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v32 = new Vector3f(0.062500F, 0.3828125F, 0.062500F);
        Vector3f v112 = new Vector3f(-0.08276F, 0.99313F, 0.08276F);
        Vector3f v117 = new Vector3f(0.12309F, 0.98473F, 0.12309F);
        Vector3f v21 = new Vector3f(-0.093750F, 0.3828125F, 0.093750F);
        Vector3f v108 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v9 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v4 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v37 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v8 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v105 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v29 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v18 = new Vector3f(-0.093750F, 0.3828125F, -0.093750F);
        Vector2f v69 = new Vector2f(0.65625F, 0.78125F);
        Vector2f v74 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v103 = new Vector2f(0.078125F, 0.203125F);
        Vector2f v59 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v102 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v101 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v76 = new Vector2f(0.56250F, 0.78125F);
        Vector2f v50 = new Vector2f(0.31250F, 0.12500F);
        Vector2f v77 = new Vector2f(0.609375F, 0.171875F);
        Vector2f v100 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v51 = new Vector2f(0.40625F, 0.00000F);
        Vector2f v99 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v98 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v63 = new Vector2f(0.21875F, 0.00000F);
        Vector2f v57 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v97 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v96 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v82 = new Vector2f(0.31250F, 0.75000F);
        Vector2f v64 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v94 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v65 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v71 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v93 = new Vector2f(0.18750F, 0.15625F);
        Vector2f v56 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v58 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v49 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v90 = new Vector2f(0.28125F, 0.18750F);
        Vector2f v45 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v43 = new Vector2f(0.50000F, 0.12500F);
        Vector2f v83 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v46 = new Vector2f(0.21875F, 0.09375F);
        Vector2f v88 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v60 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v68 = new Vector2f(0.75000F, 0.78125F);
        Vector2f v81 = new Vector2f(0.37500F, 0.75000F);
        Vector2f v53 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v87 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v79 = new Vector2f(0.65625F, 0.12500F);
        Vector2f v85 = new Vector2f(0.18750F, 0.75000F);
        Vector2f v75 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v70 = new Vector2f(0.65625F, 0.21875F);
        Vector2f v84 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v66 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v95 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v52 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v48 = new Vector2f(0.12500F, 0.12500F);
        Vector2f v73 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v80 = new Vector2f(0.46875F, 0.12500F);
        Vector2f v86 = new Vector2f(0.12500F, 0.75000F);
        Vector2f v92 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v54 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v61 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v47 = new Vector2f(0.21875F, 0.12500F);
        Vector2f v78 = new Vector2f(0.56250F, 0.12500F);
        Vector2f v72 = new Vector2f(0.46875F, 0.78125F);
        Vector2f v91 = new Vector2f(0.25000F, 0.15625F);
        Vector2f v41 = new Vector2f(0.40625F, 0.09375F);
        Vector2f v89 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v67 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v44 = new Vector2f(0.40625F, 0.12500F);
        Vector2f v42 = new Vector2f(0.50000F, 0.09375F);
        Vector2f v62 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v55 = new Vector2f(0.03125F, 0.18750F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v44, v104, v2, c0, v43, v104, v1, c0, v42, v104, v0, c0, v41, v104));
        builder.add(new SimpleQuad(v2, c0, v48, v105, v5, c0, v47, v105, v4, c0, v46, v105, v1, c0, v45, v105));
        builder.add(new SimpleQuad(v5, c0, v47, v106, v7, c0, v50, v106, v6, c0, v49, v106, v4, c0, v46, v106));
        builder.add(new SimpleQuad(v7, c0, v50, v107, v3, c0, v44, v107, v0, c0, v41, v107, v6, c0, v49, v107));
        builder.add(new SimpleQuad(v7, c0, v41, v108, v5, c0, v49, v108, v2, c0, v52, v108, v3, c0, v51, v108));
        builder.add(new SimpleQuad(v11, c0, v56, v105, v10, c0, v55, v105, v9, c0, v54, v105, v8, c0, v53, v105));
        builder.add(new SimpleQuad(v10, c0, v55, v106, v13, c0, v58, v106, v12, c0, v57, v106, v9, c0, v54, v106));
        builder.add(new SimpleQuad(v13, c0, v58, v107, v15, c0, v60, v107, v14, c0, v59, v107, v12, c0, v57, v107));
        builder.add(new SimpleQuad(v13, c0, v59, v108, v10, c0, v57, v108, v11, c0, v62, v108, v15, c0, v61, v108));
        builder.add(new SimpleQuad(v0, c0, v52, v109, v1, c0, v63, v109, v4, c0, v46, v109, v6, c0, v49, v109));
        builder.add(new SimpleQuad(v15, c0, v60, v104, v11, c0, v65, v104, v8, c0, v64, v104, v14, c0, v59, v104));
        builder.add(new SimpleQuad(v9, c0, v54, v109, v12, c0, v57, v109, v14, c0, v62, v109, v8, c0, v66, v109));
        HILT_QUADS = builder.build();

        // BladeOut
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v17, c1, v70, v104, v18, c1, v69, v104, v19, c1, v68, v104, v16, c1, v67, v104));
        builder.add(new SimpleQuad(v20, c1, v74, v105, v21, c1, v73, v105, v18, c1, v72, v105, v17, c1, v71, v105));
        builder.add(new SimpleQuad(v22, c1, v71, v106, v23, c1, v72, v106, v21, c1, v76, v106, v20, c1, v75, v106));
        builder.add(new SimpleQuad(v16, c1, v75, v107, v19, c1, v76, v107, v23, c1, v69, v107, v22, c1, v70, v107));
        builder.add(new SimpleQuad(v21, c1, v79, v110, v23, c1, v78, v111, v24, c1, v77, v109, v24, c1, v77, v109));
        builder.add(new SimpleQuad(v23, c1, v78, v111, v19, c1, v75, v112, v24, c1, v77, v109, v24, c1, v77, v109));
        builder.add(new SimpleQuad(v19, c1, v75, v112, v18, c1, v70, v113, v24, c1, v77, v109, v24, c1, v77, v109));
        builder.add(new SimpleQuad(v18, c1, v70, v113, v21, c1, v79, v110, v24, c1, v77, v109, v24, c1, v77, v109));
        builder.add(new SimpleQuad(v17, c1, v75, v108, v16, c1, v71, v108, v22, c1, v80, v108, v20, c1, v78, v108));
        BLADE_OUT_QUADS = builder.build();

        // BladeMid
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v26, c2, v83, v104, v27, c2, v82, v104, v28, c2, v81, v104, v25, c2, v74, v104));
        builder.add(new SimpleQuad(v29, c2, v87, v105, v30, c2, v86, v105, v27, c2, v85, v105, v26, c2, v84, v105));
        builder.add(new SimpleQuad(v31, c2, v84, v106, v32, c2, v85, v106, v30, c2, v89, v106, v29, c2, v88, v106));
        builder.add(new SimpleQuad(v25, c2, v88, v107, v28, c2, v89, v107, v32, c2, v82, v107, v31, c2, v83, v107));
        builder.add(new SimpleQuad(v30, c2, v92, v114, v32, c2, v91, v115, v24, c2, v90, v109, v24, c2, v90, v109));
        builder.add(new SimpleQuad(v32, c2, v91, v115, v28, c2, v88, v116, v24, c2, v90, v109, v24, c2, v90, v109));
        builder.add(new SimpleQuad(v28, c2, v88, v116, v27, c2, v83, v117, v24, c2, v90, v109, v24, c2, v90, v109));
        builder.add(new SimpleQuad(v27, c2, v83, v117, v30, c2, v92, v114, v24, c2, v90, v109, v24, c2, v90, v109));
        builder.add(new SimpleQuad(v26, c2, v88, v108, v25, c2, v84, v108, v31, c2, v93, v108, v29, c2, v91, v108));
        BLADE_MID_QUADS = builder.build();

        // BladeIn
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v36, c3, v96, v104, v35, c3, v95, v104, v34, c3, v87, v104, v33, c3, v94, v104));
        builder.add(new SimpleQuad(v35, c3, v100, v105, v38, c3, v99, v105, v37, c3, v98, v105, v34, c3, v97, v105));
        builder.add(new SimpleQuad(v38, c3, v99, v106, v40, c3, v102, v106, v39, c3, v101, v106, v37, c3, v98, v106));
        builder.add(new SimpleQuad(v40, c3, v102, v107, v36, c3, v96, v107, v33, c3, v94, v107, v39, c3, v101, v107));
        builder.add(new SimpleQuad(v38, c3, v101, v119, v35, c3, v58, v118, v24, c3, v103, v108, v24, c3, v103, v108));
        builder.add(new SimpleQuad(v35, c3, v58, v118, v36, c3, v60, v121, v24, c3, v103, v108, v24, c3, v103, v108));
        builder.add(new SimpleQuad(v36, c3, v60, v121, v40, c3, v94, v120, v24, c3, v103, v108, v24, c3, v103, v108));
        builder.add(new SimpleQuad(v40, c3, v94, v120, v38, c3, v101, v119, v24, c3, v103, v108, v24, c3, v103, v108));
        builder.add(new SimpleQuad(v34, c3, v55, v109, v37, c3, v98, v109, v39, c3, v101, v109, v33, c3, v58, v109));
        BLADE_IN_QUADS = builder.build();
    }
}
