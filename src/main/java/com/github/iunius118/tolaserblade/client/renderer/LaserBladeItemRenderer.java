package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.LaserBladeItemModel;
import com.github.iunius118.tolaserblade.client.model.SimpleItemModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LaserBladeItemRenderer extends ItemStackTileEntityRenderer {
    public static final ResourceLocation LASER_BLADE_TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/laser_blade.png");
    public static final int LIGHTMAP_BRIGHT = 0xF000F0;

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        matrixStack.push();

        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.HILT), LaserBladeItemModel.getHiltFaces(), -1, lightmapCoord, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.HILT), LaserBladeItemModel.getHilt2Faces(), -1, lightmapCoord, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.LASER_ADD), LaserBladeItemModel.getBladeInnerFaces(), -1, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.LASER_ADD), LaserBladeItemModel.getBladeOuter1Faces(), 0xFFFF0000, LIGHTMAP_BRIGHT, overlayColor);
        renderFaces(matrixStack, buffer.getBuffer(LaserBladeRenderType.LASER_ADD), LaserBladeItemModel.getBladeOuter2Faces(), 0xFFFF0000, LIGHTMAP_BRIGHT, overlayColor);

        matrixStack.pop();
    }

    public void renderFaces(MatrixStack matrixStack, IVertexBuilder buffer, List<SimpleItemModel.FaceData> faces, int color, int lightmapCoord, int overlayColor) {
        MatrixStack.Entry matrixEntry = matrixStack.getLast();
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red   = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;

        for (SimpleItemModel.FaceData face : faces) {
            for (int i = 0; i < 4; i++) {
                SimpleItemModel.VertexData vertex = face.vertices[i];

                Vector4f pos = new Vector4f(vertex.pos);
                pos.transform(matrixEntry.getMatrix());

                Vector4f vColor = vertex.color;
                Vec2f uv = vertex.uv;

                Vector3f normal = vertex.normal.copy();
                normal.transform(matrixEntry.getNormal());

                buffer.addVertex(pos.getX(), pos.getY(), pos.getZ(),
                        red * vColor.getX(), green * vColor.getY(), blue * vColor.getZ(), alpha * vColor.getW(),
                        uv.x, uv.y, overlayColor, lightmapCoord,
                        normal.getX(), normal.getY(), normal.getZ());
            }
        }
    }
}
