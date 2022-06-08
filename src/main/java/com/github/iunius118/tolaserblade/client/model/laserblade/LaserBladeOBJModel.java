package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.BlockModelConfiguration;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import net.minecraftforge.client.model.obj.OBJModel;

import java.util.*;

public class LaserBladeOBJModel extends SimpleLaserBladeModel {
    private final Map<Part, List<BakedQuad>> PARTS = Maps.newEnumMap(Part.class);
    private final ResourceLocation TEXTURE = InventoryMenu.BLOCK_ATLAS;

    public void loadLaserBladeOBJModel(ForgeModelBakery loader) {
        // Load model
        PARTS.clear();
        ResourceLocation modelLocation = new ResourceLocation(ToLaserBlade.MOD_ID, "item/laser_blade_obj");
        UnbakedModel model = loader.getModelOrMissing(modelLocation);

        if (!(model instanceof BlockModel blockModel))  return;

        // Get model geometry objects
        BlockModelConfiguration modelConfig = blockModel.customData;
        IModelGeometry<?> modelGeometry = modelConfig.getCustomGeometry();
        Collection<? extends IModelGeometryPart> geometryParts = Collections.emptyList();

        if (modelGeometry instanceof OBJModel) {
            Optional<? extends IModelGeometryPart> part = modelGeometry.getPart("laser_blade");

            if (part.isPresent() && part.get() instanceof OBJModel.ModelGroup modelGroup) {
                geometryParts = modelGroup.getParts();
            }

        } else if (modelGeometry != null) {
            geometryParts = modelGeometry.getParts();

        } else {
            return;

        }

        // Get model parts and their baked quads
        for (IModelGeometryPart geometryPart : geometryParts) {
            Part part = Part.find(geometryPart.name());

            if (part != null) {
                var particleIcon = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_INGOT).getParticleIcon(EmptyModelData.INSTANCE);
                IModelBuilder<?> builder = IModelBuilder.of(modelConfig, ItemOverrides.EMPTY, particleIcon);
                geometryPart.addQuads(modelConfig, builder, loader, ForgeModelBakery.defaultTextureGetter(), SimpleModelState.IDENTITY, modelLocation);
                PARTS.put(part, builder.build().getQuads(null, null, RandomSource.create(42L), EmptyModelData.INSTANCE));
            }
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
        return PARTS.getOrDefault(part, Collections.emptyList());
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
