package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
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

public class LaserBladeItemModel {
    public static Map<Part, List<BakedQuad>> parts = Maps.newEnumMap(Part.class);

    public static void loadLaserBladeOBJModel(ModelLoader loader) {
        // Load model
        parts.clear();
        ResourceLocation modelLocation = new ResourceLocation(ToLaserBlade.MOD_ID, "item/laser_blade_obj");
        IUnbakedModel model = loader.getModelOrMissing(modelLocation);

        if (!(model instanceof BlockModel))  return;

        // Get model geometry object
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
                parts.put(part, builder.build().getQuads(null, null, new Random(42L), EmptyModelData.INSTANCE));
            }
        }
    }

    public enum Part {
        HILT("laser_blade/hilt"),
        HILT_2("laser_blade/hilt_2"),
        HILT_BRIGHT("laser_blade/hilt_bright"),
        BLADE_INNER("laser_blade/blade_inner"),
        BLADE_OUTER_1("laser_blade/blade_outer_1"),
        BLADE_OUTER_2("laser_blade/blade_outer_2"),
        BLADE_OUTER_MODE_1("laser_blade/blade_outer_mode_1"),
        BLADE_INNER_MODE_1("laser_blade/blade_inner_mode_1");

        private String name;

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
