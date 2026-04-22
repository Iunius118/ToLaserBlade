package com.github.iunius118.tolaserblade.client.renderer;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.LBSwordModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class LBSwordSpecialRenderer implements SpecialModelRenderer<LBSwordRenderContext> {
	Identifier TEXTURE = ToLaserBlade.id("textures/item/laser_blade_3d.png");
	private final LBSwordModel model;

	public LBSwordSpecialRenderer(LBSwordModel model) {
		this.model = model;
	}

	@Override
	public void submit(@Nullable LBSwordRenderContext argument, PoseStack poseStack,
					   SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil,
					   int outlineColor) {
		if (argument == null) {
			return;
		}

		poseStack.pushPose();
		PoseStack.Pose pose = poseStack.last();

		submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, RenderTypes.beaconBeam(TEXTURE, false),
				lightCoords, overlayCoords, outlineColor, null);

		poseStack.popPose();
	}

	@Override
	public void getExtents(Consumer<Vector3fc> output) {
		// Tentative extents of laser blades for drop item and GUI rendering
		PoseStack poseStack = new PoseStack();
		model.root().getExtentsForGui(poseStack, output);
	}

	@Override
	public @Nullable LBSwordRenderContext extractArgument(ItemStack stack) {
		return new LBSwordRenderContext(stack);
	}

	public static class Unbaked implements SpecialModelRenderer.Unbaked<LBSwordRenderContext> {
		// No default values
		public static final MapCodec<LBSwordSpecialRenderer.Unbaked> MAP_CODEC =
				MapCodec.unit(new LBSwordSpecialRenderer.Unbaked());

		@Override
		public @Nullable SpecialModelRenderer<LBSwordRenderContext> bake(BakingContext bakingContext) {
			return new LBSwordSpecialRenderer(
					new LBSwordModel(bakingContext.entityModelSet().bakeLayer(LBSwordModel.LAYER_LOCATION)));
		}

		@Override
		public MapCodec<? extends SpecialModelRenderer.Unbaked<LBSwordRenderContext>> type() {
			return MAP_CODEC;
		}
	}
}
