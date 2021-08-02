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

public class LaserBladeModelType1 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> HILT_2_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);

        if (color.isBroken) {
            renderQuads(matrices, currentBuffer, HILT_2_QUADS, color.gripColor, light, overlay);
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getUnlitRenderType());
        renderQuads(matrices, currentBuffer, BLADE_OUT_QUADS, color.simpleOuterColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_IN_QUADS, color.simpleInnerColor, fullLight, overlay);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v86 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v83 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v13 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v31 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v29 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v21 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v7 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
        Vector3f v2 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
        Vector3f v26 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v84 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v25 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v5 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
        Vector3f v87 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v23 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v8 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v11 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v20 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v30 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v0 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v88 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v85 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v22 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v4 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v18 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v28 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v27 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v24 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v3 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
        Vector3f v14 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v16 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v19 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v17 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v9 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector2f v47 = new Vector2f(0.31250F, 0.12500F);
        Vector2f v77 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v33 = new Vector2f(0.21875F, 0.00000F);
        Vector2f v66 = new Vector2f(0.18750F, 0.75000F);
        Vector2f v82 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v51 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v36 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v32 = new Vector2f(0.21875F, 0.09375F);
        Vector2f v61 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v35 = new Vector2f(0.234375F, 0.078125F);
        Vector2f v48 = new Vector2f(0.40625F, 0.00000F);
        Vector2f v70 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v44 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v65 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v81 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v68 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v75 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v64 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v74 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v55 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v39 = new Vector2f(0.296875F, 0.015625F);
        Vector2f v78 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v43 = new Vector2f(0.40625F, 0.12500F);
        Vector2f v76 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v53 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v80 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v41 = new Vector2f(0.50000F, 0.09375F);
        Vector2f v59 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v54 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v56 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v50 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v46 = new Vector2f(0.12500F, 0.12500F);
        Vector2f v72 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v71 = new Vector2f(0.25000F, 0.15625F);
        Vector2f v69 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v67 = new Vector2f(0.12500F, 0.75000F);
        Vector2f v45 = new Vector2f(0.21875F, 0.12500F);
        Vector2f v42 = new Vector2f(0.50000F, 0.12500F);
        Vector2f v79 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v57 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v58 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v62 = new Vector2f(0.37500F, 0.75000F);
        Vector2f v40 = new Vector2f(0.40625F, 0.09375F);
        Vector2f v38 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v34 = new Vector2f(0.234375F, 0.015625F);
        Vector2f v60 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v73 = new Vector2f(0.18750F, 0.15625F);
        Vector2f v63 = new Vector2f(0.31250F, 0.75000F);
        Vector2f v49 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v52 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v37 = new Vector2f(0.296875F, 0.078125F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v35, v83, v2, c0, v34, v83, v1, c0, v33, v83, v0, c0, v32, v83));
        builder.add(new SimpleQuad(v5, c0, v37, v83, v3, c0, v35, v83, v0, c0, v32, v83, v4, c0, v36, v83));
        builder.add(new SimpleQuad(v2, c0, v34, v83, v7, c0, v39, v83, v6, c0, v38, v83, v1, c0, v33, v83));
        builder.add(new SimpleQuad(v6, c0, v38, v83, v7, c0, v39, v83, v5, c0, v37, v83, v4, c0, v36, v83));
        builder.add(new SimpleQuad(v9, c0, v43, v84, v8, c0, v42, v84, v1, c0, v41, v84, v6, c0, v40, v84));
        builder.add(new SimpleQuad(v8, c0, v46, v85, v10, c0, v45, v85, v0, c0, v32, v85, v1, c0, v44, v85));
        builder.add(new SimpleQuad(v10, c0, v45, v86, v11, c0, v47, v86, v4, c0, v36, v86, v0, c0, v32, v86));
        builder.add(new SimpleQuad(v11, c0, v47, v87, v9, c0, v43, v87, v6, c0, v40, v87, v4, c0, v36, v87));
        builder.add(new SimpleQuad(v11, c0, v40, v88, v10, c0, v36, v88, v8, c0, v38, v88, v9, c0, v48, v88));
        builder.add(new SimpleQuad(v15, c0, v52, v84, v14, c0, v51, v84, v13, c0, v50, v84, v12, c0, v49, v84));
        builder.add(new SimpleQuad(v14, c0, v56, v85, v17, c0, v55, v85, v16, c0, v54, v85, v13, c0, v53, v85));
        builder.add(new SimpleQuad(v17, c0, v55, v86, v19, c0, v58, v86, v18, c0, v57, v86, v16, c0, v54, v86));
        builder.add(new SimpleQuad(v19, c0, v58, v87, v15, c0, v52, v87, v12, c0, v49, v87, v18, c0, v57, v87));
        builder.add(new SimpleQuad(v19, c0, v49, v88, v17, c0, v57, v88, v14, c0, v60, v88, v15, c0, v59, v88));
        HILT_QUADS = builder.build();

        // Hilt2
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c1, v35, v83, v5, c1, v37, v83, v7, c1, v39, v83, v2, c1, v34, v83));
        HILT_2_QUADS = builder.build();

        // BladeOut
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v21, c2, v64, v84, v5, c2, v63, v84, v3, c2, v62, v84, v20, c2, v61, v84));
        builder.add(new SimpleQuad(v22, c2, v68, v85, v7, c2, v67, v85, v5, c2, v66, v85, v21, c2, v65, v85));
        builder.add(new SimpleQuad(v23, c2, v65, v86, v2, c2, v66, v86, v7, c2, v70, v86, v22, c2, v69, v86));
        builder.add(new SimpleQuad(v20, c2, v69, v87, v3, c2, v70, v87, v2, c2, v63, v87, v23, c2, v64, v87));
        builder.add(new SimpleQuad(v22, c2, v72, v88, v21, c2, v64, v88, v20, c2, v69, v88, v23, c2, v71, v88));
        builder.add(new SimpleQuad(v2, c2, v73, v83, v3, c2, v65, v83, v5, c2, v69, v83, v7, c2, v71, v83));
        BLADE_OUT_QUADS = builder.build();

        // BladeIn
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c3, v76, v84, v26, c3, v75, v84, v25, c3, v68, v84, v24, c3, v74, v84));
        builder.add(new SimpleQuad(v26, c3, v80, v85, v29, c3, v79, v85, v28, c3, v78, v85, v25, c3, v77, v85));
        builder.add(new SimpleQuad(v29, c3, v79, v86, v31, c3, v82, v86, v30, c3, v81, v86, v28, c3, v78, v86));
        builder.add(new SimpleQuad(v31, c3, v82, v87, v27, c3, v76, v87, v24, c3, v74, v87, v30, c3, v81, v87));
        builder.add(new SimpleQuad(v24, c3, v58, v83, v25, c3, v55, v83, v28, c3, v78, v83, v30, c3, v81, v83));
        builder.add(new SimpleQuad(v27, c3, v52, v88, v31, c3, v74, v88, v29, c3, v81, v88, v26, c3, v58, v88));
        BLADE_IN_QUADS = builder.build();
    }
}
