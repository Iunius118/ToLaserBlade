package com.github.iunius118.tolaserblade.client.renderer.entity;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.renderer.Color4Float;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.github.iunius118.tolaserblade.client.renderer.entity.model.LaserTrapModel;
import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class LaserTrapEntityRenderer extends EntityRenderer<LaserTrapEntity> {
    private final LaserTrapModel LASER_TRAP_MODEL = new LaserTrapModel();
    private final ResourceLocation LASER_TRAP_TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/entity/laser_trap/laser_trap.png");
    private final RenderType RENDER_TYPE = LaserBladeRenderType.getTrapRenderType(LASER_TRAP_TEXTURE);

    public LaserTrapEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(LaserTrapEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();

        Color4Float c = new Color4Float(entityIn.getColor());
        LASER_TRAP_MODEL.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        // Render shape (write depth)
        final int fullLight = 0xF000F0;
        IVertexBuilder buffer = bufferIn.getBuffer(RENDER_TYPE);
        LASER_TRAP_MODEL.renderToBuffer(matrixStackIn, buffer, fullLight, OverlayTexture.NO_OVERLAY, c.r, c.g, c.b, c.a);
        // Render color
        //buffer = bufferIn.getBuffer(RenderType.getEyes(LASER_TRAP_TEXTURE));
        //LASER_TRAP_MODEL.render(matrixStackIn, buffer, FULL_LIGHT, OverlayTexture.NO_OVERLAY, c.r, c.g, c.b, c.a);

        matrixStackIn.popPose();
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