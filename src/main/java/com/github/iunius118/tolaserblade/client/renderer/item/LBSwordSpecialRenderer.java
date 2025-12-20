package com.github.iunius118.tolaserblade.client.renderer.item;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class LBSwordSpecialRenderer implements SpecialModelRenderer<ItemStack> {
    @Override
    public void submit(@Nullable ItemStack stack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, int overlay, boolean hasFoil, int k) {
        if (stack == null) {
            return;
        }

        var modelManager = LaserBladeModelManager.getInstance();
        LaserBladeModel model = modelManager.getModel(stack);

        if (model != null) {
            poseStack.pushPose();
            model.submit(stack, itemDisplayContext, poseStack, submitNodeCollector, light, overlay);
            poseStack.popPose();
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        // Tentative extents of laser blades for drop item and GUI rendering
        PoseStack poseStack = new PoseStack();
        consumer.accept(poseStack.last().pose().transformPosition(-1F, -1F, -1F, new Vector3f()));
        consumer.accept(poseStack.last().pose().transformPosition(0F, 3F, 0F, new Vector3f()));
    }

    @Override
    public @Nullable ItemStack extractArgument(ItemStack stack) {
        return stack;
    }

    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        // No default values
        public static final MapCodec<LBSwordSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new LBSwordSpecialRenderer.Unbaked());

        @Override
        public @Nullable SpecialModelRenderer<?> bake(BakingContext bakingContext) {
            return new LBSwordSpecialRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
