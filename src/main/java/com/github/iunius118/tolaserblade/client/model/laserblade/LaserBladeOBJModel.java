package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.BlockGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LaserBladeOBJModel extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = InventoryMenu.BLOCK_ATLAS;

    private final Map<Part, List<BakedQuad>> bakedParts = Maps.newEnumMap(Part.class);

    public void loadLaserBladeOBJModel(ModelBakery loader) {
        // Load model
        bakedParts.clear();
        var modelLocation = new ResourceLocation(ToLaserBlade.MOD_ID, "item/laser_blade_obj");
        UnbakedModel model = loader.getModel(modelLocation);

        if (!(model instanceof BlockModel blockModel))  return;

        // Get model geometry object
        BlockGeometryBakingContext customData = blockModel.customData;
        IUnbakedGeometry<?> modelGeometry = customData.getCustomGeometry();

        var defaultVisibilityData = new BlockGeometryBakingContext.VisibilityData();
        Part[] values = Part.values();
        Arrays.stream(values).forEach(part1 -> defaultVisibilityData.setVisibilityState(part1.getName(), false));

        // Get model parts and their baked quads
        for (Part part : values) {
            var visibilityData = customData.visibilityData;
            visibilityData.copyFrom(defaultVisibilityData);
            visibilityData.setVisibilityState(part.name, true);
            visibilityData.setVisibilityState(part.name, true);

            var bakedModel = modelGeometry.bake(customData, loader, loader.getAtlasSet()::getSprite, new SimpleModelState(Transformation.identity()), ItemOverrides.EMPTY, null);
            var bakedQuads = bakedModel.getQuads(null, null, RandomSource.create(42L), ModelData.EMPTY, null);
            bakedParts.put(part, bakedQuads);
        }
    }

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderBakedQuads(matrices, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.HILT), color.gripColor, light, overlay);
        renderBakedQuads(matrices, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.HILT_NO_TINT), -1, light, overlay);

        currentBuffer = vertexConsumers.getBuffer(getUnlitRenderType());
        renderBakedQuads(matrices, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.HILT_LIGHT), -1, fullLight, overlay);

        if (color.isBroken) {
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderBakedQuads(matrices, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.BLADE_INNER), color.innerColor, fullLight, overlay);

        currentBuffer = vertexConsumers.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderBakedQuads(matrices, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.BLADE_OUTER_1), color.outerColor, fullLight, overlay);
        renderBakedQuads(matrices, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.BLADE_OUTER_2), color.outerColor, fullLight, overlay);
    }

    public void renderBakedQuads(PoseStack matrices, VertexConsumer buffer, List<BakedQuad> quads, int color, int light, int overlay) {
        PoseStack.Pose matrixEntry = matrices.last();
        float alpha = (float)(color >>> 24 & 255) / 255.0F;
        float red   = (float)(color >>> 16 & 255) / 255.0F;
        float green = (float)(color >>> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;

        for (BakedQuad quad : quads) {
            buffer.putBulkData(matrixEntry, quad, red, green, blue, alpha, light, OverlayTexture.NO_OVERLAY, true);
        }
    }

    public List<BakedQuad> getBakedQuads(LaserBladeOBJModel.Part part) {
        return bakedParts.getOrDefault(part, Collections.emptyList());
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public enum Part {
        HILT("laser_blade/hilt"),
        HILT_NO_TINT("laser_blade/hilt_no_tint"),
        HILT_LIGHT("laser_blade/hilt_light"),
        BLADE_INNER("laser_blade/blade_inner"),
        BLADE_OUTER_1("laser_blade/blade_outer_1"),
        BLADE_OUTER_2("laser_blade/blade_outer_2");

        private final String name;

        Part(String nameIn) {
            name = nameIn;
        }

        public String getName() {
            return name;
        }

        public static Part find(String nameIn) {
            for(Part value : values()) {
                if(value.getName().equals(nameIn)) {
                    return value;
                }
            }

            return null;
        }
    }
}
