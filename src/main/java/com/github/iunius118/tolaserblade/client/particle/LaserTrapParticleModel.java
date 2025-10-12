package com.github.iunius118.tolaserblade.client.particle;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class LaserTrapParticleModel extends Model.Simple {
    public static final ResourceLocation TEXTURE_LOCATION = ToLaserBlade.makeId("textures/particle/laser_trap.png");

    public LaserTrapParticleModel(Direction.Axis axis) {
        this(createModelPart(axis), texture -> RenderType.beaconBeam(texture, true));
    }

    public LaserTrapParticleModel(ModelPart modelPart, Function<ResourceLocation, RenderType> function) {
        super(modelPart, function);
    }

    public static ModelPart createModelPart(Direction.Axis axis) {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();
        var cubeBuilder = CubeListBuilder.create().texOffs(0, 0).addBox(-1F, -8F, -1F, 2F, 16F, 2F, new CubeDeformation(0.0F));
        var partPose = switch (axis) {
            case X -> PartPose.rotation(0, 0, (float) (Math.PI / 2.0));
            case Z -> PartPose.rotation((float) (Math.PI / 2.0), 0, 0);
            default -> PartPose.ZERO;
        };
        partDefinition.addOrReplaceChild("beam", cubeBuilder, partPose);
        return partDefinition.bake(8, 32);
    }
}
