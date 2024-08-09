package com.github.iunius118.tolaserblade.client.renderer.item;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

@OnlyIn(Dist.CLIENT)
public class LBSwordItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final LBSwordItemRenderer INSTANCE = new LBSwordItemRenderer();
    public static final IClientItemExtensions CLIENT_ITEM_EXTENSIONS = new IClientItemExtensions() {
        @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return INSTANCE;
        }
    };

    protected LBSwordItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
        var modelManager = LaserBladeModelManager.getInstance();
        LaserBladeModel model = modelManager.getModel(itemStack);

        if (model != null) {
            pose.pushPose();
            model.render(itemStack, transformType, pose, vertexConsumers, light, overlay);
            pose.popPose();
        }
    }
}
