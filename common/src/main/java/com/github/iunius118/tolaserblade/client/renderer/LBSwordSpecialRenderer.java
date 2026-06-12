package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class LBSwordSpecialRenderer implements SpecialModelRenderer<LBSwordRenderContext> {

    @Override
    public void submit(@Nullable LBSwordRenderContext argument, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil,
                       int outlineColor) {
        if (argument == null) {
            return;
        }

        var modelManager = LaserBladeModelManager.getInstance();
        LaserBladeModel model = modelManager.getModel(argument.itemStack);

        if (model != null) {
            poseStack.pushPose();
            model.submit(argument.itemStack, argument.displayContext, poseStack, submitNodeCollector, lightCoords,
                    overlayCoords);
            poseStack.popPose();
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        // Tentative extents of laser blades for drop item and GUI rendering
        PoseStack poseStack = new PoseStack();
        output.accept(poseStack.last().pose().transformPosition(-1F, -1F, -1F, new Vector3f()));
        output.accept(poseStack.last().pose().transformPosition(0F, 3F, 0F, new Vector3f()));
    }

    @Override
    public @Nullable LBSwordRenderContext extractArgument(ItemStack itemStack) {
        return new LBSwordRenderContext(itemStack);
    }

    public static class Unbaked implements SpecialModelRenderer.Unbaked<LBSwordRenderContext> {
        // No default values
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public @Nullable SpecialModelRenderer<LBSwordRenderContext> bake(BakingContext bakingContext) {
            return new LBSwordSpecialRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked<LBSwordRenderContext>> type() {
            return MAP_CODEC;
        }
    }
}
