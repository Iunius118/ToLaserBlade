package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.client.model.LaserBladeItemModel;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;

public class LBBrokenItemRenderer extends LaserBladeItemRenderer {
    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        matrixStack.push();

        int gripColor = LaserBladeItemBase.getGripColor(itemStack);
        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);

        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT), gripColor, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_2), gripColor, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_NO_TINT), -1, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeItemModel.Part.HILT_BRIGHT), -1, lightmapCoord, overlayColor);

        matrixStack.pop();
    }
}
