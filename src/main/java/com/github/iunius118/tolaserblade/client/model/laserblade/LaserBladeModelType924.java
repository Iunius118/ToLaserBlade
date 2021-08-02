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

public class LaserBladeModelType924 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_924.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> HILT_NO_TINT_QUADS;
    public static final List<SimpleQuad> HILT_LIGHT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int noTintColor = 0xFFFFFFFF;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);
        renderQuads(matrices, currentBuffer, HILT_NO_TINT_QUADS, noTintColor, light, overlay);

        if (color.isBroken) {
            renderQuads(matrices, currentBuffer, HILT_LIGHT_QUADS, noTintColor, light, overlay);
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getUnlitRenderType());
        renderQuads(matrices, currentBuffer, HILT_LIGHT_QUADS, noTintColor, fullLight, overlay);

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
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // Hilt color
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // HiltNoTint color
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 1F); // HiltLight color
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.9F); // BladeIn color
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.5F); // BladeMid color
        Vector4f c5 = new Vector4f(1F, 1F, 1F, 0.25F); // BladeOut color

        Vector3f v12 = new Vector3f(-0.062500F, 0.312500F, -0.062500F);
        Vector3f v9 = new Vector3f(0.062500F, 0.312500F, -0.062500F);
        Vector3f v128 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v29 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v3 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v43 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
        Vector3f v0 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v33 = new Vector3f(0.031250F, 0.500000F, -0.031250F);
        Vector3f v31 = new Vector3f(-0.031250F, 0.500000F, 0.031250F);
        Vector3f v37 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v5 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector3f v41 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
        Vector3f v32 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v132 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v11 = new Vector3f(0.062500F, 0.000000F, 0.062500F);
        Vector3f v10 = new Vector3f(0.062500F, 0.000000F, -0.062500F);
        Vector3f v15 = new Vector3f(-0.062500F, 0.000000F, 0.062500F);
        Vector3f v23 = new Vector3f(0.062500F, 0.437500F, 0.062500F);
        Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v25 = new Vector3f(-0.062500F, 0.437500F, -0.062500F);
        Vector3f v28 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v34 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v129 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v127 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v42 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
        Vector3f v24 = new Vector3f(-0.062500F, 0.500000F, -0.062500F);
        Vector3f v40 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
        Vector3f v16 = new Vector3f(-0.093750F, 0.437500F, 0.093750F);
        Vector3f v38 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v17 = new Vector3f(0.093750F, 0.437500F, 0.093750F);
        Vector3f v7 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v35 = new Vector3f(-0.031250F, 0.500000F, -0.031250F);
        Vector3f v2 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v130 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v39 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v27 = new Vector3f(-0.062500F, 0.437500F, 0.062500F);
        Vector3f v13 = new Vector3f(-0.062500F, 0.000000F, -0.062500F);
        Vector3f v36 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v131 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v14 = new Vector3f(-0.062500F, 0.312500F, 0.062500F);
        Vector3f v8 = new Vector3f(0.062500F, 0.312500F, 0.062500F);
        Vector3f v19 = new Vector3f(-0.093750F, 0.437500F, -0.093750F);
        Vector3f v20 = new Vector3f(0.062500F, 0.500000F, 0.062500F);
        Vector3f v4 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v30 = new Vector3f(0.031250F, 0.500000F, 0.031250F);
        Vector3f v26 = new Vector3f(-0.062500F, 0.500000F, 0.062500F);
        Vector3f v22 = new Vector3f(0.062500F, 0.437500F, -0.062500F);
        Vector3f v18 = new Vector3f(0.093750F, 0.437500F, -0.093750F);
        Vector3f v21 = new Vector3f(0.062500F, 0.500000F, -0.062500F);
        Vector2f v47 = new Vector2f(0.53125F, 0.12500F);
        Vector2f v101 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v79 = new Vector2f(0.81250F, 0.00000F);
        Vector2f v87 = new Vector2f(0.43750F, 0.21875F);
        Vector2f v89 = new Vector2f(0.50000F, 0.18750F);
        Vector2f v71 = new Vector2f(1.00000F, 0.09375F);
        Vector2f v63 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v57 = new Vector2f(0.06250F, 0.06250F);
        Vector2f v48 = new Vector2f(0.25000F, 0.09375F);
        Vector2f v118 = new Vector2f(0.37500F, 0.31250F);
        Vector2f v97 = new Vector2f(0.03125F, 0.25000F);
        Vector2f v117 = new Vector2f(0.65625F, 0.87500F);
        Vector2f v90 = new Vector2f(0.50000F, 0.21875F);
        Vector2f v59 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v72 = new Vector2f(1.00000F, 0.12500F);
        Vector2f v69 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v53 = new Vector2f(0.43750F, 0.12500F);
        Vector2f v68 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v60 = new Vector2f(0.12500F, 0.06250F);
        Vector2f v52 = new Vector2f(0.43750F, 0.09375F);
        Vector2f v96 = new Vector2f(0.00000F, 0.25000F);
        Vector2f v70 = new Vector2f(0.90625F, 0.09375F);
        Vector2f v66 = new Vector2f(0.34375F, 0.00000F);
        Vector2f v112 = new Vector2f(0.25000F, 0.28125F);
        Vector2f v99 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v76 = new Vector2f(0.81250F, 0.09375F);
        Vector2f v93 = new Vector2f(0.12500F, 0.25000F);
        Vector2f v74 = new Vector2f(0.71875F, 0.09375F);
        Vector2f v110 = new Vector2f(0.18750F, 0.78125F);
        Vector2f v50 = new Vector2f(0.34375F, 0.12500F);
        Vector2f v94 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v85 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v64 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v103 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v106 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v121 = new Vector2f(0.37500F, 0.87500F);
        Vector2f v100 = new Vector2f(0.06250F, 0.25000F);
        Vector2f v114 = new Vector2f(0.65625F, 0.31250F);
        Vector2f v125 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v77 = new Vector2f(0.81250F, 0.12500F);
        Vector2f v61 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v75 = new Vector2f(0.71875F, 0.12500F);
        Vector2f v67 = new Vector2f(0.25000F, 0.06250F);
        Vector2f v55 = new Vector2f(0.43750F, 0.00000F);
        Vector2f v51 = new Vector2f(0.25000F, 0.12500F);
        Vector2f v92 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v105 = new Vector2f(0.37500F, 0.28125F);
        Vector2f v107 = new Vector2f(0.31250F, 0.78125F);
        Vector2f v104 = new Vector2f(0.31250F, 0.28125F);
        Vector2f v81 = new Vector2f(0.25000F, 0.18750F);
        Vector2f v122 = new Vector2f(0.56250F, 0.31250F);
        Vector2f v86 = new Vector2f(0.43750F, 0.18750F);
        Vector2f v95 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v124 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v111 = new Vector2f(0.12500F, 0.78125F);
        Vector2f v123 = new Vector2f(0.56250F, 0.87500F);
        Vector2f v126 = new Vector2f(0.65625F, 0.21875F);
        Vector2f v62 = new Vector2f(0.18750F, 0.06250F);
        Vector2f v119 = new Vector2f(0.46875F, 0.31250F);
        Vector2f v116 = new Vector2f(0.75000F, 0.87500F);
        Vector2f v115 = new Vector2f(0.75000F, 0.31250F);
        Vector2f v73 = new Vector2f(0.90625F, 0.12500F);
        Vector2f v113 = new Vector2f(0.25000F, 0.78125F);
        Vector2f v109 = new Vector2f(0.18750F, 0.28125F);
        Vector2f v108 = new Vector2f(0.12500F, 0.28125F);
        Vector2f v49 = new Vector2f(0.34375F, 0.09375F);
        Vector2f v46 = new Vector2f(0.62500F, 0.12500F);
        Vector2f v44 = new Vector2f(0.53125F, 0.09375F);
        Vector2f v83 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v84 = new Vector2f(0.37500F, 0.18750F);
        Vector2f v45 = new Vector2f(0.62500F, 0.09375F);
        Vector2f v102 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v80 = new Vector2f(0.71875F, 0.00000F);
        Vector2f v54 = new Vector2f(0.53125F, 0.00000F);
        Vector2f v98 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v65 = new Vector2f(0.12500F, 0.00000F);
        Vector2f v120 = new Vector2f(0.46875F, 0.87500F);
        Vector2f v56 = new Vector2f(0.00000F, 0.06250F);
        Vector2f v91 = new Vector2f(0.31250F, 0.12500F);
        Vector2f v88 = new Vector2f(0.37500F, 0.12500F);
        Vector2f v58 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v82 = new Vector2f(0.31250F, 0.18750F);
        Vector2f v78 = new Vector2f(0.90625F, 0.00000F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v47, v127, v2, c0, v46, v127, v1, c0, v45, v127, v0, c0, v44, v127));
        builder.add(new SimpleQuad(v2, c0, v51, v128, v5, c0, v50, v128, v4, c0, v49, v128, v1, c0, v48, v128));
        builder.add(new SimpleQuad(v5, c0, v50, v129, v7, c0, v53, v129, v6, c0, v52, v129, v4, c0, v49, v129));
        builder.add(new SimpleQuad(v7, c0, v53, v130, v3, c0, v47, v130, v0, c0, v44, v130, v6, c0, v52, v130));
        builder.add(new SimpleQuad(v7, c0, v44, v131, v5, c0, v52, v131, v2, c0, v55, v131, v3, c0, v54, v131));
        builder.add(new SimpleQuad(v11, c0, v59, v128, v10, c0, v58, v128, v9, c0, v57, v128, v8, c0, v56, v128));
        builder.add(new SimpleQuad(v10, c0, v58, v129, v13, c0, v61, v129, v12, c0, v60, v129, v9, c0, v57, v129));
        builder.add(new SimpleQuad(v13, c0, v61, v130, v15, c0, v63, v130, v14, c0, v62, v130, v12, c0, v60, v130));
        builder.add(new SimpleQuad(v13, c0, v62, v131, v10, c0, v60, v131, v11, c0, v65, v131, v15, c0, v64, v131));
        builder.add(new SimpleQuad(v0, c0, v55, v132, v1, c0, v66, v132, v4, c0, v49, v132, v6, c0, v52, v132));
        builder.add(new SimpleQuad(v15, c0, v63, v127, v11, c0, v68, v127, v8, c0, v67, v127, v14, c0, v62, v127));
        builder.add(new SimpleQuad(v9, c0, v57, v132, v12, c0, v60, v132, v14, c0, v65, v132, v8, c0, v69, v132));
        HILT_QUADS = builder.build();

        // HiltNoTint
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c1, v73, v127, v1, c1, v72, v127, v17, c1, v71, v127, v16, c1, v70, v127));
        builder.add(new SimpleQuad(v1, c1, v46, v128, v4, c1, v75, v128, v18, c1, v74, v128, v17, c1, v45, v128));
        builder.add(new SimpleQuad(v4, c1, v75, v129, v6, c1, v77, v129, v19, c1, v76, v129, v18, c1, v74, v129));
        builder.add(new SimpleQuad(v6, c1, v77, v130, v0, c1, v73, v130, v16, c1, v70, v130, v19, c1, v76, v130));
        builder.add(new SimpleQuad(v6, c1, v70, v131, v4, c1, v76, v131, v1, c1, v79, v131, v0, c1, v78, v131));
        builder.add(new SimpleQuad(v16, c1, v79, v132, v17, c1, v80, v132, v18, c1, v74, v132, v19, c1, v76, v132));
        HILT_NO_TINT_QUADS = builder.build();

        // HiltLight
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c2, v68, v128, v22, c2, v83, v128, v21, c2, v82, v128, v20, c2, v81, v128));
        builder.add(new SimpleQuad(v22, c2, v83, v129, v25, c2, v85, v129, v24, c2, v84, v129, v21, c2, v82, v129));
        builder.add(new SimpleQuad(v25, c2, v85, v130, v27, c2, v87, v130, v26, c2, v86, v130, v24, c2, v84, v130));
        builder.add(new SimpleQuad(v25, c2, v86, v131, v22, c2, v84, v131, v23, c2, v88, v131, v27, c2, v53, v131));
        builder.add(new SimpleQuad(v27, c2, v87, v127, v23, c2, v90, v127, v20, c2, v89, v127, v26, c2, v86, v127));
        builder.add(new SimpleQuad(v21, c2, v82, v132, v24, c2, v84, v132, v26, c2, v88, v132, v20, c2, v91, v132));
        HILT_LIGHT_QUADS = builder.build();

        // BladeIn
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v31, c3, v95, v127, v30, c3, v94, v127, v29, c3, v93, v127, v28, c3, v92, v127));
        builder.add(new SimpleQuad(v30, c3, v99, v128, v33, c3, v98, v128, v32, c3, v97, v128, v29, c3, v96, v128));
        builder.add(new SimpleQuad(v33, c3, v98, v129, v35, c3, v101, v129, v34, c3, v100, v129, v32, c3, v97, v129));
        builder.add(new SimpleQuad(v35, c3, v101, v130, v31, c3, v95, v130, v28, c3, v92, v130, v34, c3, v100, v130));
        builder.add(new SimpleQuad(v28, c3, v58, v132, v29, c3, v102, v132, v32, c3, v97, v132, v34, c3, v100, v132));
        builder.add(new SimpleQuad(v31, c3, v103, v131, v35, c3, v92, v131, v33, c3, v100, v131, v30, c3, v58, v131));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v26, c4, v107, v127, v20, c4, v106, v127, v37, c4, v105, v127, v36, c4, v104, v127));
        builder.add(new SimpleQuad(v20, c4, v111, v128, v21, c4, v110, v128, v38, c4, v109, v128, v37, c4, v108, v128));
        builder.add(new SimpleQuad(v21, c4, v110, v129, v24, c4, v113, v129, v39, c4, v112, v129, v38, c4, v109, v129));
        builder.add(new SimpleQuad(v24, c4, v113, v130, v26, c4, v107, v130, v36, c4, v104, v130, v39, c4, v112, v130));
        builder.add(new SimpleQuad(v36, c4, v68, v132, v37, c4, v63, v132, v38, c4, v109, v132, v39, c4, v112, v132));
        builder.add(new SimpleQuad(v20, c4, v68, v131, v26, c4, v83, v131, v24, c4, v104, v131, v21, c4, v112, v131));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v16, c5, v117, v127, v17, c5, v116, v127, v41, c5, v115, v127, v40, c5, v114, v127));
        builder.add(new SimpleQuad(v17, c5, v121, v128, v18, c5, v120, v128, v42, c5, v119, v128, v41, c5, v118, v128));
        builder.add(new SimpleQuad(v18, c5, v120, v129, v19, c5, v123, v129, v43, c5, v122, v129, v42, c5, v119, v129));
        builder.add(new SimpleQuad(v19, c5, v123, v130, v16, c5, v117, v130, v40, c5, v114, v130, v43, c5, v122, v130));
        builder.add(new SimpleQuad(v40, c5, v125, v132, v41, c5, v124, v132, v42, c5, v119, v132, v43, c5, v122, v132));
        builder.add(new SimpleQuad(v19, c5, v114, v131, v18, c5, v122, v131, v17, c5, v125, v131, v16, c5, v126, v131));
        BLADE_OUT_QUADS = builder.build();
    }
}
