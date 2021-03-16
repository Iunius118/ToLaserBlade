package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class LaserBladeModelType1108 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1108.png");
    public static final List<SimpleQuad> BLADE_OFF_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        if (transformType == ItemCameraTransforms.TransformType.FIXED || transformType == ItemCameraTransforms.TransformType.GUI) {
            matrixStack.translate(0.0D, 0.03125D, 0.0D);
        }

        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        IVertexBuilder currentBuffer;

        if (color.isBroken) {
            currentBuffer = buffer.getBuffer(getHiltRenderType());
            renderQuads(matrixStack, currentBuffer, BLADE_OFF_QUADS, color.gripColor, lightmapCoord, overlayColor);
            return;
        }

        // Rotate blade
        float angle = Util.getMillis() % 250L * 1.44F;   // 240 rpm
        matrixStack.mulPose(new Quaternion(Vector3f.YP, angle, true));

        currentBuffer = buffer.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = buffer.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v16 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v25 = new Vector3f(0.093750F, -0.062500F, -0.093750F);
        Vector3f v20 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
        Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v18 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v14 = new Vector3f(0.062500F, -0.031250F, 0.062500F);
        Vector3f v19 = new Vector3f(-0.062500F, -0.031250F, -0.062500F);
        Vector3f v76 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v27 = new Vector3f(-0.093750F, -0.062500F, -0.093750F);
        Vector3f v9 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v22 = new Vector3f(0.093750F, -0.062500F, 0.093750F);
        Vector3f v12 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v15 = new Vector3f(-0.062500F, -0.031250F, 0.062500F);
        Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v23 = new Vector3f(-0.093750F, -0.062500F, 0.093750F);
        Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v75 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v17 = new Vector3f(0.062500F, -0.031250F, -0.062500F);
        Vector3f v78 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v21 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
        Vector3f v79 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v80 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v77 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v10 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v13 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v26 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v11 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v24 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
        Vector3f v8 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector2f v51 = new Vector2f(0.31250F, 0.25000F);
        Vector2f v34 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v74 = new Vector2f(0.65625F, 0.15625F);
        Vector2f v72 = new Vector2f(0.46875F, 0.15625F);
        Vector2f v65 = new Vector2f(0.75000F, 1.00000F);
        Vector2f v60 = new Vector2f(0.18750F, 0.18750F);
        Vector2f v56 = new Vector2f(0.18750F, 0.96875F);
        Vector2f v61 = new Vector2f(0.25000F, 0.18750F);
        Vector2f v66 = new Vector2f(0.65625F, 1.00000F);
        Vector2f v48 = new Vector2f(0.00000F, 0.93750F);
        Vector2f v32 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v40 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v45 = new Vector2f(0.00000F, 0.25000F);
        Vector2f v29 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v42 = new Vector2f(0.12500F, 0.25000F);
        Vector2f v63 = new Vector2f(0.65625F, 0.25000F);
        Vector2f v68 = new Vector2f(0.46875F, 1.00000F);
        Vector2f v64 = new Vector2f(0.75000F, 0.25000F);
        Vector2f v73 = new Vector2f(0.56250F, 0.15625F);
        Vector2f v57 = new Vector2f(0.12500F, 0.96875F);
        Vector2f v70 = new Vector2f(0.56250F, 0.25000F);
        Vector2f v35 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v46 = new Vector2f(0.03125F, 0.25000F);
        Vector2f v49 = new Vector2f(0.06250F, 0.25000F);
        Vector2f v36 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v69 = new Vector2f(0.37500F, 1.00000F);
        Vector2f v39 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v59 = new Vector2f(0.25000F, 0.96875F);
        Vector2f v62 = new Vector2f(0.31250F, 0.18750F);
        Vector2f v67 = new Vector2f(0.46875F, 0.25000F);
        Vector2f v47 = new Vector2f(0.03125F, 0.93750F);
        Vector2f v52 = new Vector2f(0.37500F, 0.25000F);
        Vector2f v53 = new Vector2f(0.37500F, 0.96875F);
        Vector2f v71 = new Vector2f(0.56250F, 1.00000F);
        Vector2f v55 = new Vector2f(0.18750F, 0.25000F);
        Vector2f v54 = new Vector2f(0.31250F, 0.96875F);
        Vector2f v44 = new Vector2f(0.09375F, 0.93750F);
        Vector2f v58 = new Vector2f(0.25000F, 0.25000F);
        Vector2f v37 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v28 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v50 = new Vector2f(0.06250F, 0.93750F);
        Vector2f v41 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v43 = new Vector2f(0.12500F, 0.93750F);
        Vector2f v38 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v33 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v31 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v30 = new Vector2f(0.03125F, 0.21875F);
        ImmutableList.Builder<SimpleQuad> builder;

        // BladeOff
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v31, v75, v2, c0, v30, v75, v1, c0, v29, v75, v0, c0, v28, v75));
        builder.add(new SimpleQuad(v2, c0, v30, v76, v5, c0, v33, v76, v4, c0, v32, v76, v1, c0, v29, v76));
        builder.add(new SimpleQuad(v5, c0, v33, v77, v7, c0, v35, v77, v6, c0, v34, v77, v4, c0, v32, v77));
        builder.add(new SimpleQuad(v5, c0, v34, v78, v2, c0, v32, v78, v3, c0, v37, v78, v7, c0, v36, v78));
        builder.add(new SimpleQuad(v7, c0, v35, v79, v3, c0, v39, v79, v0, c0, v38, v79, v6, c0, v34, v79));
        builder.add(new SimpleQuad(v1, c0, v29, v80, v4, c0, v32, v80, v6, c0, v37, v80, v0, c0, v40, v80));
        BLADE_OFF_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v7, c1, v44, v79, v3, c1, v43, v79, v9, c1, v42, v79, v8, c1, v41, v79));
        builder.add(new SimpleQuad(v3, c1, v48, v75, v2, c1, v47, v75, v10, c1, v46, v75, v9, c1, v45, v75));
        builder.add(new SimpleQuad(v2, c1, v47, v76, v5, c1, v50, v76, v11, c1, v49, v76, v10, c1, v46, v76));
        builder.add(new SimpleQuad(v5, c1, v50, v77, v7, c1, v44, v77, v8, c1, v41, v77, v11, c1, v49, v77));
        builder.add(new SimpleQuad(v8, c1, v33, v80, v9, c1, v30, v80, v10, c1, v46, v80, v11, c1, v49, v80));
        builder.add(new SimpleQuad(v7, c1, v35, v78, v5, c1, v41, v78, v2, c1, v49, v78, v3, c1, v33, v78));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c2, v54, v79, v14, c2, v53, v79, v13, c2, v52, v79, v12, c2, v51, v79));
        builder.add(new SimpleQuad(v14, c2, v57, v75, v17, c2, v56, v75, v16, c2, v55, v75, v13, c2, v42, v75));
        builder.add(new SimpleQuad(v17, c2, v56, v76, v19, c2, v59, v76, v18, c2, v58, v76, v16, c2, v55, v76));
        builder.add(new SimpleQuad(v19, c2, v59, v77, v15, c2, v54, v77, v12, c2, v51, v77, v18, c2, v58, v77));
        builder.add(new SimpleQuad(v12, c2, v61, v80, v13, c2, v60, v80, v16, c2, v55, v80, v18, c2, v58, v80));
        builder.add(new SimpleQuad(v14, c2, v61, v78, v15, c2, v62, v78, v19, c2, v51, v78, v17, c2, v58, v78));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c3, v66, v79, v22, c3, v65, v79, v21, c3, v64, v79, v20, c3, v63, v79));
        builder.add(new SimpleQuad(v22, c3, v69, v75, v25, c3, v68, v75, v24, c3, v67, v75, v21, c3, v52, v75));
        builder.add(new SimpleQuad(v25, c3, v68, v76, v27, c3, v71, v76, v26, c3, v70, v76, v24, c3, v67, v76));
        builder.add(new SimpleQuad(v27, c3, v71, v77, v23, c3, v66, v77, v20, c3, v63, v77, v26, c3, v70, v77));
        builder.add(new SimpleQuad(v20, c3, v73, v80, v21, c3, v72, v80, v24, c3, v67, v80, v26, c3, v70, v80));
        builder.add(new SimpleQuad(v27, c3, v63, v78, v25, c3, v70, v78, v22, c3, v73, v78, v23, c3, v74, v78));
        BLADE_OUT_QUADS = builder.build();
    }
}
