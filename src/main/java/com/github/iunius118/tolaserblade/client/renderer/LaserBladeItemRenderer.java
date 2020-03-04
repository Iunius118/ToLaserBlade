package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.client.model.LaserBladeItemModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LaserBladeItemRenderer extends ItemStackTileEntityRenderer {
    public static final int LIGHTMAP_BRIGHT = 0xF000F0;

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        matrixStack.push();

        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.HILT), getBakedQuads(LaserBladeItemModel.Part.HILT), -1, lightmapCoord, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.HILT), getBakedQuads(LaserBladeItemModel.Part.HILT_2), -1, lightmapCoord, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.LASER_ADD), getBakedQuads(LaserBladeItemModel.Part.BLADE_INNER), -1, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.LASER_ADD), getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_1), 0xFFFF0000, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.LASER_ADD), getBakedQuads(LaserBladeItemModel.Part.BLADE_OUTER_2), 0xFFFF0000, LIGHTMAP_BRIGHT, overlayColor);

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
}
