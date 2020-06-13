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

public class LaserBladeModelType217 extends SimpleModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_217.png");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, hiltQuads, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            renderQuads(matrixStack, currentBuffer, hilt2Quads, color.gripColor, lightmapCoord, overlayColor);
            return;
        }

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderQuads(matrixStack, currentBuffer, bladeMidQuads, color.simpleOuterColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, bladeInQuads, color.simpleInnerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, bladeOutQuads, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    private static final Vector3f v75 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v15 = new Vector3f(-0.025000F, 0.381250F, 0.025000F);
    private static final Vector3f v27 = new Vector3f(-0.062500F, 0.343750F, -0.062500F);
    private static final Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v14 = new Vector3f(0.025000F, 0.381250F, 0.025000F);
    private static final Vector3f v1 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v17 = new Vector3f(0.025000F, 0.381250F, -0.025000F);
    private static final Vector3f v78 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v25 = new Vector3f(0.062500F, 0.343750F, -0.062500F);
    private static final Vector3f v74 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v12 = new Vector3f(-0.025000F, 1.431250F, 0.025000F);
    private static final Vector3f v10 = new Vector3f(-0.031250F, 1.437500F, 0.031250F);
    private static final Vector3f v21 = new Vector3f(0.062500F, 1.468750F, 0.062500F);
    private static final Vector3f v22 = new Vector3f(0.062500F, 0.343750F, 0.062500F);
    private static final Vector3f v19 = new Vector3f(-0.025000F, 0.381250F, -0.025000F);
    private static final Vector3f v23 = new Vector3f(-0.062500F, 0.343750F, 0.062500F);
    private static final Vector3f v77 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v11 = new Vector3f(0.031250F, 1.437500F, 0.031250F);
    private static final Vector3f v24 = new Vector3f(0.062500F, 1.468750F, -0.062500F);
    private static final Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v13 = new Vector3f(0.025000F, 1.431250F, 0.025000F);
    private static final Vector3f v76 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v20 = new Vector3f(-0.062500F, 1.468750F, 0.062500F);
    private static final Vector3f v79 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v8 = new Vector3f(0.031250F, 1.437500F, -0.031250F);
    private static final Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v9 = new Vector3f(-0.031250F, 1.437500F, -0.031250F);
    private static final Vector3f v4 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v26 = new Vector3f(-0.062500F, 1.468750F, -0.062500F);
    private static final Vector3f v18 = new Vector3f(-0.025000F, 1.431250F, -0.025000F);
    private static final Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v0 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v16 = new Vector3f(0.025000F, 1.431250F, -0.025000F);
    private static final Vec2f v43 = new Vec2f(0.21875F, 0.78125F);
    private static final Vec2f v70 = new Vec2f(0.37500F, 0.81250F);
    private static final Vec2f v64 = new Vec2f(0.50000F, 0.81250F);
    private static final Vec2f v39 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v59 = new Vec2f(0.00000F, 0.78125F);
    private static final Vec2f v35 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v53 = new Vec2f(0.15625F, 0.21875F);
    private static final Vec2f v31 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v40 = new Vec2f(0.03125F, 0.00000F);
    private static final Vec2f v73 = new Vec2f(0.43750F, 0.18750F);
    private static final Vec2f v47 = new Vec2f(0.12500F, 0.78125F);
    private static final Vec2f v50 = new Vec2f(0.18750F, 0.78125F);
    private static final Vec2f v68 = new Vec2f(0.25000F, 0.81250F);
    private static final Vec2f v46 = new Vec2f(0.15625F, 0.78125F);
    private static final Vec2f v60 = new Vec2f(0.06250F, 0.25000F);
    private static final Vec2f v42 = new Vec2f(0.25000F, 0.78125F);
    private static final Vec2f v56 = new Vec2f(0.00000F, 0.25000F);
    private static final Vec2f v52 = new Vec2f(0.21875F, 0.21875F);
    private static final Vec2f v44 = new Vec2f(0.21875F, 0.25000F);
    private static final Vec2f v51 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v58 = new Vec2f(0.03125F, 0.78125F);
    private static final Vec2f v55 = new Vec2f(0.09375F, 0.78125F);
    private static final Vec2f v62 = new Vec2f(0.43750F, 0.25000F);
    private static final Vec2f v65 = new Vec2f(0.43750F, 0.81250F);
    private static final Vec2f v54 = new Vec2f(0.09375F, 0.25000F);
    private static final Vec2f v37 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f v66 = new Vec2f(0.31250F, 0.25000F);
    private static final Vec2f v49 = new Vec2f(0.18750F, 0.25000F);
    private static final Vec2f v61 = new Vec2f(0.06250F, 0.78125F);
    private static final Vec2f v30 = new Vec2f(0.03125F, 0.21875F);
    private static final Vec2f v28 = new Vec2f(0.00000F, 0.03125F);
    private static final Vec2f v72 = new Vec2f(0.37500F, 0.18750F);
    private static final Vec2f v69 = new Vec2f(0.37500F, 0.25000F);
    private static final Vec2f v48 = new Vec2f(0.12500F, 0.25000F);
    private static final Vec2f v29 = new Vec2f(0.03125F, 0.03125F);
    private static final Vec2f v71 = new Vec2f(0.31250F, 0.18750F);
    private static final Vec2f v67 = new Vec2f(0.31250F, 0.81250F);
    private static final Vec2f v33 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v63 = new Vec2f(0.50000F, 0.25000F);
    private static final Vec2f v41 = new Vec2f(0.25000F, 0.25000F);
    private static final Vec2f v32 = new Vec2f(0.06250F, 0.03125F);
    private static final Vec2f v34 = new Vec2f(0.09375F, 0.03125F);
    private static final Vec2f v38 = new Vec2f(0.12500F, 0.03125F);
    private static final Vec2f v57 = new Vec2f(0.03125F, 0.25000F);
    private static final Vec2f v36 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v45 = new Vec2f(0.15625F, 0.25000F);


    public static final List<SimpleQuad> hiltQuads = getHiltQuads();

    public static List<SimpleQuad> getHiltQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v31, v74, v2, c, v30, v74, v1, c, v29, v74, v0, c, v28, v74));
        builder.add(new SimpleQuad(v2, c, v30, v75, v5, c, v33, v75, v4, c, v32, v75, v1, c, v29, v75));
        builder.add(new SimpleQuad(v5, c, v33, v76, v7, c, v35, v76, v6, c, v34, v76, v4, c, v32, v76));
        builder.add(new SimpleQuad(v5, c, v34, v77, v2, c, v32, v77, v3, c, v37, v77, v7, c, v36, v77));
        builder.add(new SimpleQuad(v7, c, v35, v78, v3, c, v39, v78, v0, c, v38, v78, v6, c, v34, v78));
        return builder.build();
    }

    public static final List<SimpleQuad> hilt2Quads = getHilt2Quads();

    public static List<SimpleQuad> getHilt2Quads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c, v40, v79, v1, c, v29, v79, v4, c, v32, v79, v6, c, v37, v79));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeMidQuads = getBladeMidQuads();

    public static List<SimpleQuad> getBladeMidQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v9, c, v44, v78, v4, c, v43, v78, v1, c, v42, v78, v8, c, v41, v78));
        builder.add(new SimpleQuad(v10, c, v48, v74, v6, c, v47, v74, v4, c, v46, v74, v9, c, v45, v74));
        builder.add(new SimpleQuad(v11, c, v45, v75, v0, c, v46, v75, v6, c, v50, v75, v10, c, v49, v75));
        builder.add(new SimpleQuad(v8, c, v49, v76, v1, c, v50, v76, v0, c, v43, v76, v11, c, v44, v76));
        builder.add(new SimpleQuad(v10, c, v52, v77, v9, c, v44, v77, v8, c, v49, v77, v11, c, v51, v77));
        builder.add(new SimpleQuad(v0, c, v53, v79, v1, c, v45, v79, v4, c, v49, v79, v6, c, v51, v79));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeInQuads = getBladeInQuads();

    public static List<SimpleQuad> getBladeInQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c, v55, v78, v14, c, v47, v78, v13, c, v48, v78, v12, c, v54, v78));
        builder.add(new SimpleQuad(v14, c, v59, v74, v17, c, v58, v74, v16, c, v57, v74, v13, c, v56, v74));
        builder.add(new SimpleQuad(v17, c, v58, v75, v19, c, v61, v75, v18, c, v60, v75, v16, c, v57, v75));
        builder.add(new SimpleQuad(v19, c, v61, v76, v15, c, v55, v76, v12, c, v54, v76, v18, c, v60, v76));
        builder.add(new SimpleQuad(v12, c, v33, v79, v13, c, v30, v79, v16, c, v57, v79, v18, c, v60, v79));
        builder.add(new SimpleQuad(v15, c, v35, v77, v19, c, v54, v77, v17, c, v60, v77, v14, c, v33, v77));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOutQuads = getBladeOutQuads();

    public static List<SimpleQuad> getBladeOutQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.5F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c, v65, v78, v22, c, v64, v78, v21, c, v63, v78, v20, c, v62, v78));
        builder.add(new SimpleQuad(v22, c, v68, v74, v25, c, v67, v74, v24, c, v66, v74, v21, c, v41, v74));
        builder.add(new SimpleQuad(v25, c, v67, v75, v27, c, v70, v75, v26, c, v69, v75, v24, c, v66, v75));
        builder.add(new SimpleQuad(v27, c, v70, v76, v23, c, v65, v76, v20, c, v62, v76, v26, c, v69, v76));
        builder.add(new SimpleQuad(v20, c, v72, v79, v21, c, v71, v79, v24, c, v66, v79, v26, c, v69, v79));
        builder.add(new SimpleQuad(v22, c, v72, v77, v23, c, v73, v77, v27, c, v62, v77, v25, c, v69, v77));
        return builder.build();
    }
}
