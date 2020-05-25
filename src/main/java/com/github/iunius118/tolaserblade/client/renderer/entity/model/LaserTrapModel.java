package com.github.iunius118.tolaserblade.client.renderer.entity.model;

import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LaserTrapModel extends SegmentedModel<LaserTrapEntity> {
    private final ModelRenderer laserBeam;

    public LaserTrapModel() {
        textureWidth = 32;
        textureHeight = 32;

        laserBeam = new ModelRenderer(this);
        laserBeam.setTextureOffset(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F);
        laserBeam.setRotationPoint(0.0F, 8.0F, 0.0F);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.laserBeam);
    }

    @Override
    public void setRotationAngles(LaserTrapEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        laserBeam.rotateAngleY = entityIn.getYaw(1) * ((float)Math.PI / 180F);
        laserBeam.rotateAngleX = (entityIn.getPitch(1) - 90F) * ((float)Math.PI / 180F);
    }
}
