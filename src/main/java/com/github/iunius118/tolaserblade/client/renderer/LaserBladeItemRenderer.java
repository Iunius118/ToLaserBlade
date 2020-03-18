package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.LaserBladeItemModel;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LaserBladeItemRenderer extends ItemStackTileEntityRenderer {
    private static final int LIGHTMAP_BRIGHT = 0xF000F0;

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        matrixStack.push();

        if (ToLaserBladeConfig.CLIENT.laserBladeRenderingMode.get() == 1) {
            renderLaserBladeMode1(itemStack, matrixStack, buffer, lightmapCoord, overlayColor);

        } else {
            renderLaserBladeMode0(itemStack, matrixStack, buffer, lightmapCoord, overlayColor);
        }

        matrixStack.pop();
    }

    private void renderLaserBladeMode0(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        int gripColor = LaserBladeItemBase.getGripColor(itemStack);

        Pair<Integer, Boolean> bladeColor = LaserBladeItemBase.getBladeInnerColor(itemStack);
        int innerColor = bladeColor.getLeft();
        boolean isInnerSubColor = bladeColor.getRight();

        bladeColor = LaserBladeItemBase.getBladeOuterColor(itemStack);
        int outerColor = bladeColor.getLeft();
        boolean isOuterSubColor = bladeColor.getRight();

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT), gripColor, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_2), gripColor, lightmapCoord, overlayColor);

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_BRIGHT), -1, LIGHTMAP_BRIGHT, overlayColor);

        currentBuffer = isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_INNER), innerColor, LIGHTMAP_BRIGHT, overlayColor);

        currentBuffer = isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_1), outerColor, LIGHTMAP_BRIGHT, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_2), outerColor, LIGHTMAP_BRIGHT, overlayColor);
    }

    private void renderLaserBladeMode1(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        int gripColor = LaserBladeItemBase.getGripColor(itemStack);

        Pair<Integer, Boolean> bladeColor = LaserBladeItemBase.getBladeOuterColor(itemStack);
        int outerColor = (bladeColor.getRight() ? ~bladeColor.getLeft() : bladeColor.getLeft()) | 0xFF000000;

        bladeColor = LaserBladeItemBase.getBladeInnerColor(itemStack);
        int innerColor = (bladeColor.getRight() ? ~bladeColor.getLeft() : bladeColor.getLeft()) | 0xFF000000;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT), gripColor, lightmapCoord, overlayColor);

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_BRIGHT), -1, LIGHTMAP_BRIGHT, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_MODE_1), outerColor, LIGHTMAP_BRIGHT, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_INNER_MODE_1), innerColor, LIGHTMAP_BRIGHT, overlayColor);
    }

    public void renderQuads(MatrixStack matrixStack, IVertexBuilder buffer, List<BakedQuad> quads, int color, int lightmapCoord, int overlayColor) {
        MatrixStack.Entry matrixEntry = matrixStack.getLast();
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red   = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;

        for (BakedQuad quad : quads) {
            buffer.addVertexData(matrixEntry, quad, red, green, blue, alpha, lightmapCoord, overlayColor, true);
        }
    }

    private List<BakedQuad> getBakedQuads(LaserBladeItemModel.Part part) {
        return LaserBladeItemModel.parts.getOrDefault(part, Collections.emptyList());
    }
}
