package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class LBSwordModel extends Model.Simple {
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ToLaserBlade.id("laser_blade"), "main");
	private final ModelPart main;

	public LBSwordModel(ModelPart root) {
		super(root, RenderTypes::entityTranslucent);
		main = root.getChild("main");
	}

	public static LayerDefinition createLayer() {
		var meshDefinition = new MeshDefinition();
		var partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(7.5F, -6F, 7.5F, 1F, 32F, 1F), PartPose.ZERO);
		return LayerDefinition.create(meshDefinition, 16, 16);
	}
}
