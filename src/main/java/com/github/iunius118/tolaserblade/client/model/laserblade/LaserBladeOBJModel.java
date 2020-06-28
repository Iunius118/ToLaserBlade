package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.BlockModelConfiguration;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import net.minecraftforge.client.model.obj.OBJModel;

import java.util.*;

public class LaserBladeOBJModel extends SimpleModel {
    private final Map<Part, List<BakedQuad>> PARTS = Maps.newEnumMap(Part.class);
    private final ResourceLocation TEXTURE = PlayerContainer.LOCATION_BLOCKS_TEXTURE;

    public void loadLaserBladeOBJModel(ModelLoader loader) {
        // Load model
        PARTS.clear();
        ResourceLocation modelLocation = new ResourceLocation(ToLaserBlade.MOD_ID, "item/laser_blade_obj");
        IUnbakedModel model = loader.getModelOrMissing(modelLocation);

        if (!(model instanceof BlockModel))  return;

        // Get model geometry objects
        BlockModelConfiguration modelConfig = ((BlockModel)model).customData;
        IModelGeometry<?> modelGeometry = modelConfig.getCustomGeometry();
        Collection<? extends IModelGeometryPart> geometryParts = Collections.emptyList();

        if (modelGeometry instanceof OBJModel) {
            Optional<? extends IModelGeometryPart> part = modelGeometry.getPart("laser_blade");

            if (part.isPresent() && part.get() instanceof OBJModel.ModelGroup) {
                geometryParts = ((OBJModel.ModelGroup) part.get()).getParts();
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
                IModelBuilder<?> builder = IModelBuilder.of(modelConfig, ItemOverrideList.EMPTY, Minecraft.getInstance().getItemRenderer().getItemModelMesher().getParticleIcon(Items.IRON_INGOT));
                geometryPart.addQuads(modelConfig, builder, loader, ModelLoader.defaultTextureGetter(), SimpleModelTransform.IDENTITY, modelLocation);
                PARTS.put(part, builder.build().getQuads(null, null, new Random(42L), EmptyModelData.INSTANCE));
            }
        }
    }

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderBakedQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.HILT), color.gripColor, lightmapCoord, overlayColor);
        renderBakedQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.HILT_NO_TINT), -1, lightmapCoord, overlayColor);

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderBakedQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.HILT_LIGHT), -1, fullLight, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderBakedQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.BLADE_INNER), color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderBakedQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.BLADE_OUTER_1), color.outerColor, fullLight, overlayColor);
        renderBakedQuads(matrixStack, currentBuffer, getBakedQuads(LaserBladeOBJModel.Part.BLADE_OUTER_2), color.outerColor, fullLight, overlayColor);
    }

    public void renderBakedQuads(MatrixStack matrixStack, IVertexBuilder buffer, List<BakedQuad> quads, int color, int lightmapCoord, int overlayColor) {
        MatrixStack.Entry matrixEntry = matrixStack.getLast();
        float alpha = (float)(color >>> 24 & 255) / 255.0F;
        float red   = (float)(color >>> 16 & 255) / 255.0F;
        float green = (float)(color >>> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;

        for (BakedQuad quad : quads) {
            buffer.addVertexData(matrixEntry, quad, red, green, blue, alpha, lightmapCoord, OverlayTexture.NO_OVERLAY, true);
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
