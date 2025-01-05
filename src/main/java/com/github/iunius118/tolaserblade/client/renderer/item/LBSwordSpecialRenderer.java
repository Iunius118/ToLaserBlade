package com.github.iunius118.tolaserblade.client.renderer.item;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class LBSwordSpecialRenderer implements SpecialModelRenderer<ItemStack> {
    @Override
    public void render(@Nullable ItemStack stack, ItemDisplayContext displayContext, PoseStack pose, MultiBufferSource bufferSource, int light, int overlay, boolean hasFoil) {
        if (stack == null) {
            return;
        }

        var modelManager = LaserBladeModelManager.getInstance();
        LaserBladeModel model = modelManager.getModel(stack);

        if (model != null) {
            pose.pushPose();
            model.render(stack, displayContext, pose, bufferSource, light, overlay);
            pose.popPose();
        }
    }

    @Override
    public @Nullable ItemStack extractArgument(ItemStack stack) {
        return stack;
    }

    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        // No default values
        public static final MapCodec<LBSwordSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new LBSwordSpecialRenderer.Unbaked());

        @Override
        public @Nullable SpecialModelRenderer<?> bake(EntityModelSet p_388631_) {
            return new LBSwordSpecialRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
