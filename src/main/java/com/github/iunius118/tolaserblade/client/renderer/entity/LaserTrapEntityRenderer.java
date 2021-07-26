package com.github.iunius118.tolaserblade.client.renderer.entity;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.github.iunius118.tolaserblade.client.renderer.entity.model.LaserTrapModel;
import com.github.iunius118.tolaserblade.common.util.Color4Float;
import com.github.iunius118.tolaserblade.world.entity.LaserTrapEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class LaserTrapEntityRenderer extends EntityRenderer<LaserTrapEntity> {
    private final LaserTrapModel LASER_TRAP_MODEL = new LaserTrapModel();
    private final ResourceLocation LASER_TRAP_TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/entity/laser_trap/laser_trap.png");
    private final RenderType RENDER_TYPE = LaserBladeRenderType.getTrapRenderType(LASER_TRAP_TEXTURE);

    public LaserTrapEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LaserTrapEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
        poseStack.pushPose();

        Color4Float c = new Color4Float(entityIn.getColor());
        LASER_TRAP_MODEL.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        // Render shape (write depth)
        final int fullLight = 0xF000F0;
        VertexConsumer buffer = bufferSource.getBuffer(RENDER_TYPE);
        LASER_TRAP_MODEL.renderToBuffer(poseStack, buffer, fullLight, OverlayTexture.NO_OVERLAY, c.r, c.g, c.b, c.a);
        // Render color
        // buffer = bufferIn.getBuffer(RenderType.getEyes(LASER_TRAP_TEXTURE));
        // LASER_TRAP_MODEL.render(matrixStackIn, buffer, FULL_LIGHT, OverlayTexture.NO_OVERLAY, c.r, c.g, c.b, c.a);

        poseStack.popPose();
    }

    @Override
    protected int getBlockLightLevel(LaserTrapEntity entityIn, BlockPos blockPosIn) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(LaserTrapEntity entity) {
        return LASER_TRAP_TEXTURE;
    }
}