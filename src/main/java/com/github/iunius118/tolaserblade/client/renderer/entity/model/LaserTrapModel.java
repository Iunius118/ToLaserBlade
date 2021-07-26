package com.github.iunius118.tolaserblade.client.renderer.entity.model;

import com.github.iunius118.tolaserblade.world.entity.LaserTrapEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class LaserTrapModel extends ListModel<LaserTrapEntity> {
    private final ModelPart laserBeam;

    public LaserTrapModel() {
        var cube = new ModelPart.Cube(0, 0, -1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, 0, 0, 0, false, 32, 32);
        laserBeam = new ModelPart(ImmutableList.of(cube), Collections.emptyMap());
        laserBeam.setPos(0.0F, 8.0F, 0.0F);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.laserBeam);
    }

    @Override
    public void setupAnim(LaserTrapEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        laserBeam.yRot = entityIn.getViewYRot(1) * ((float)Math.PI / 180F);
        laserBeam.xRot = (entityIn.getViewXRot(1) - 90F) * ((float)Math.PI / 180F);
    }
}
