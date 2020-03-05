package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.LaserBladeItemModel;
import com.github.iunius118.tolaserblade.item.LaserBlade;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LaserBladeItemRenderer extends ItemStackTileEntityRenderer {
    public static final int LIGHTMAP_BRIGHT = 0xF000F0;

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

    public void renderFaces(MatrixStack matrixStack, IVertexBuilder buffer, List<BakedQuad> quads, int color, int lightmapCoord, int overlayColor) {
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

    private void renderLaserBladeMode0(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        int gripColor = getLaserBladeColor(itemStack, LaserBlade.KEY_COLOR_GRIP, false, LaserBlade.DEFAULT_COLOR_GRIP);
        boolean isInnerSubColor = getSubColorFlag(itemStack, LaserBlade.KEY_IS_SUB_COLOR_CORE);
        int innerColor = getLaserBladeColor(itemStack, LaserBlade.KEY_COLOR_CORE, isInnerSubColor, LaserBlade.DEFAULT_COLOR_CORE);
        boolean isOuterSubColor = getSubColorFlag(itemStack, LaserBlade.KEY_IS_SUB_COLOR_HALO);
        int outerColor = getLaserBladeColor(itemStack, LaserBlade.KEY_COLOR_HALO, isOuterSubColor, LaserBlade.DEFAULT_COLOR_HALO);

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT), gripColor, lightmapCoord, overlayColor);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_2), gripColor, lightmapCoord, overlayColor);

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_BRIGHT), -1, LIGHTMAP_BRIGHT, overlayColor);

        currentBuffer = isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_INNER), innerColor, LIGHTMAP_BRIGHT, overlayColor);

        currentBuffer = isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_1), outerColor, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_2), outerColor, LIGHTMAP_BRIGHT, overlayColor);
    }

    private void renderLaserBladeMode1(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        int gripColor = getLaserBladeColor(itemStack, LaserBlade.KEY_COLOR_GRIP, false, LaserBlade.DEFAULT_COLOR_GRIP);
        boolean isOuterSubColor = getSubColorFlag(itemStack, LaserBlade.KEY_IS_SUB_COLOR_HALO);
        int outerColor = getLaserBladeColor(itemStack, LaserBlade.KEY_COLOR_HALO, isOuterSubColor, LaserBlade.DEFAULT_COLOR_HALO);
        boolean isInnerSubColor = getSubColorFlag(itemStack, LaserBlade.KEY_IS_SUB_COLOR_CORE);
        int innerColor = getLaserBladeColor(itemStack, LaserBlade.KEY_COLOR_CORE, isInnerSubColor, LaserBlade.DEFAULT_COLOR_CORE);

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT), gripColor, lightmapCoord, overlayColor);

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_BRIGHT), -1, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_MODE_1), outerColor, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.BLADE_INNER_MODE_1), innerColor, LIGHTMAP_BRIGHT, overlayColor);
    }

    private boolean getSubColorFlag(ItemStack itemStack, String keyIsSubColor) {
        CompoundNBT nbt = itemStack.getTag();

        if (nbt != null) {
            return nbt.getBoolean(keyIsSubColor);
        }

        return false;
    }

    private int getLaserBladeColor(ItemStack itemStack, String keyColor, boolean subColorFlag, int defaultColor) {
        CompoundNBT nbt = itemStack.getTag();
        int color = defaultColor;
        int mode = ToLaserBladeConfig.CLIENT.laserBladeRenderingMode.get();

        if (nbt != null && nbt.contains(keyColor, Constants.NBT.TAG_INT)) {
            color = nbt.getInt(keyColor);

            if (mode == 1 && subColorFlag) {
                color = ~color;
            }
        }

        if (mode == 1) {
            color |= 0xFF000000;
        }

        return color;
    }
}
