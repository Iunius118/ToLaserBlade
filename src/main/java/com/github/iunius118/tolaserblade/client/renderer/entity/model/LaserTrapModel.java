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
        texWidth = 32;
        texHeight = 32;

        laserBeam = new ModelRenderer(this);
        laserBeam.texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F);
        laserBeam.setPos(0.0F, 8.0F, 0.0F);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.laserBeam);
    }

    @Override
    public void setupAnim(LaserTrapEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        laserBeam.yRot = entityIn.getViewYRot(1) * ((float)Math.PI / 180F);
        laserBeam.xRot = (entityIn.getViewXRot(1) - 90F) * ((float)Math.PI / 180F);
    }
}
