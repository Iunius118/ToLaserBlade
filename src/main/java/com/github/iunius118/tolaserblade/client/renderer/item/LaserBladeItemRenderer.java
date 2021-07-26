package com.github.iunius118.tolaserblade.client.renderer.item;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeInternalModelManager;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelHolder;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LaserBladeItemRenderer extends ItemStackTileEntityRenderer {
    @Override
    //  TODO: renderByItem = render
    public void renderByItem(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeModel model;
        LaserBladeInternalModelManager internalModelManager = LaserBladeInternalModelManager.getInstance();

        if (internalModelManager.canRenderMultipleModels()) {
            CompoundNBT tag = itemStack.getOrCreateTag();
            LaserBladeVisual.ModelType modelType = new LaserBladeVisual.ModelType(tag);
            model = internalModelManager.getModel(modelType.type);
        } else {
            model = LaserBladeModelHolder.getModel();
        }

        if (model != null) {
            matrixStack.pushPose();
            model.render(itemStack, transformType, matrixStack, buffer, lightmapCoord, overlayColor);
            matrixStack.popPose();
        }
    }
}
