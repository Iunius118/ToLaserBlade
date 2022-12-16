package com.github.iunius118.tolaserblade.client.renderer.item;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LBSwordItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final LBSwordItemRenderer INSTANCE = new LBSwordItemRenderer();

    protected LBSwordItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
        var modelManager = LaserBladeModelManager.getInstance();
        LaserBladeModel model = modelManager.getModel(itemStack);

        if (model != null) {
            pose.pushPose();
            model.render(itemStack, transformType, pose, vertexConsumers, light, overlay);
            pose.popPose();
        }
    }
}
