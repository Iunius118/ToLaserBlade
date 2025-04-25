package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LBSwordItemTransforms;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.Blocking;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.UsingOriginalModel;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.select.MainHand;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3fc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class TLBModelProvider extends ModelProvider {
    public TLBModelProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected Stream<Block> getKnownBlocks() {
        return Stream.of();
    }

    @Override
    protected Stream<Item> getKnownItems() {
        // Return stream of all mod items
        return ForgeRegistries.ITEMS.getValues().stream().filter(item -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);

            if (id != null) {
                return ToLaserBlade.MOD_ID.equals(id.getNamespace());
            } else {
                return false;
            }
        });
    }

    protected BlockModelGenerators getBlockModelGenerators(BlockStateGeneratorCollector blocks, ItemInfoCollector items, SimpleModelCollector models) {
        return new BlockModelGenerators(blocks, items, models) {
            @Override
            public void run() {}
        };
    }

    protected ItemModelGenerators getItemModelGenerators(ItemInfoCollector items, SimpleModelCollector models) {
        return new TLBItemModelGenerators(items, models);
    }

    private static class TLBItemModelGenerators extends ItemModelGenerators {
        public TLBItemModelGenerators(ItemModelOutput items, BiConsumer<ResourceLocation, ModelInstance> models) {
            super(items, models);
        }

        @Override
        public void run() {
            // DX Laser B1ade
            generateFlatItem(ModItems.DX_LASER_BLADE, ModelTemplates.FLAT_HANDHELD_ITEM);

            // Laser Blades
            ItemModel.Unbaked lbSwordModel = generateLBSwordModel(ModItems.LASER_BLADE, modelOutput);
            itemModelOutput.accept(ModItems.LASER_BLADE, lbSwordModel);
            itemModelOutput.accept(ModItems.LASER_BLADE_FP, lbSwordModel);

            // Laser Blade Parts
            itemModelOutput.accept(ModItems.LB_BLUEPRINT,
                    ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.LB_BLUEPRINT),
                            TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_blueprint")), modelOutput)));

            itemModelOutput.accept(ModItems.LB_BATTERY,
                    ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.LB_BATTERY),
                            TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_battery")), modelOutput)));

            itemModelOutput.accept(ModItems.LB_MEDIUM,
                    ItemModelUtils.tintedModel(generateLayeredItem(ModItems.LB_MEDIUM,
                                    ToLaserBlade.makeId("item/parts/lb_medium_0"), ToLaserBlade.makeId("item/parts/lb_medium_1")),
                            new Constant(0xFFFFFF), new LaserBladeTintSource(LaserBladeColorPart.OUTER_BLADE)));

            itemModelOutput.accept(ModItems.LB_EMITTER,
                    ItemModelUtils.tintedModel(generateLayeredItem(ModItems.LB_EMITTER,
                                    ToLaserBlade.makeId("item/parts/lb_emitter_0"), ToLaserBlade.makeId("item/parts/lb_emitter_1")),
                            new Constant(0xFFFFFF), new LaserBladeTintSource(LaserBladeColorPart.INNER_BLADE)));

            ItemModel.Unbaked vanillaCasingModel = ItemModelUtils.tintedModel(ModelTemplates.FLAT_HANDHELD_ITEM.create(
                            ModelLocationUtils.getModelLocation(ModItems.LB_CASING),
                            TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_casing")), modelOutput),
                    new LaserBladeTintSource(LaserBladeColorPart.GRIP));
            itemModelOutput.accept(ModItems.LB_CASING, vanillaCasingModel);
            itemModelOutput.accept(ModItems.LB_CASING_FP, vanillaCasingModel);

            // Brand-new and Broken Laser Blades
            ItemModel.Unbaked specialCasingModel = ItemModelUtils.specialModel(createLBSwordModel(
                            ModelLocationUtils.getModelLocation(ModItems.LB_CASING, "_3d"), LBSwordItemTransforms.ITEM_TRANSFORMS, modelOutput),
                    new LBSwordSpecialRenderer.Unbaked());
            ItemModel.Unbaked suspendedLBModel = ItemModelUtils.conditional(new UsingOriginalModel(), specialCasingModel, vanillaCasingModel);
            itemModelOutput.accept(ModItems.LB_BRAND_NEW, suspendedLBModel);
            itemModelOutput.accept(ModItems.LB_BRAND_NEW_1, suspendedLBModel);
            itemModelOutput.accept(ModItems.LB_BRAND_NEW_2, suspendedLBModel);
            itemModelOutput.accept(ModItems.LB_BRAND_NEW_FP, suspendedLBModel);
            itemModelOutput.accept(ModItems.LB_BROKEN, suspendedLBModel);
            itemModelOutput.accept(ModItems.LB_BROKEN_FP, suspendedLBModel);

            // Disassembled Laser Blades
            ItemModel.Unbaked DisassembledLBModel = ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.LB_DISASSEMBLED),
                    TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_disassembled")), modelOutput));
            itemModelOutput.accept(ModItems.LB_DISASSEMBLED, DisassembledLBModel);
            itemModelOutput.accept(ModItems.LB_DISASSEMBLED_FP, DisassembledLBModel);
        }

        private ItemModel.Unbaked generateLBSwordModel(Item item, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
            ItemModel.Unbaked blockingLeft = ItemModelUtils.specialModel(createLBSwordModel(ModelLocationUtils.getModelLocation(item, "_blocking_left"),
                    LBSwordItemTransforms.BLOCKING_LEFT_ITEM_TRANSFORMS, modelOutput), new LBSwordSpecialRenderer.Unbaked());
            ItemModel.Unbaked blockingRight = ItemModelUtils.specialModel(createLBSwordModel(ModelLocationUtils.getModelLocation(item, "_blocking_right"),
                    LBSwordItemTransforms.BLOCKING_RIGHT_ITEM_TRANSFORMS, modelOutput), new LBSwordSpecialRenderer.Unbaked());

            ItemModel.Unbaked blockingModel = ItemModelUtils.select(new MainHand(), blockingRight, ItemModelUtils.when(HumanoidArm.LEFT, blockingLeft));
            ItemModel.Unbaked defaultModel = ItemModelUtils.specialModel(createLBSwordModel(ModelLocationUtils.getModelLocation(item, ""),
                    LBSwordItemTransforms.ITEM_TRANSFORMS, modelOutput), new LBSwordSpecialRenderer.Unbaked());

            ItemModel.Unbaked specialModel = ItemModelUtils.conditional(new Blocking(), blockingModel, defaultModel);
            ModelTemplate threeLayeredHandheldItem = new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("item/handheld")), Optional.empty(),
                    TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2);
            ItemModel.Unbaked vanillaModel = ItemModelUtils.tintedModel(threeLayeredHandheldItem.create(ModelLocationUtils.getModelLocation(item, "_2d"),
                            TextureMapping.layered(
                                    ToLaserBlade.makeId("item/laser_blade_2d_0"),
                                    ToLaserBlade.makeId("item/laser_blade_2d_1"),
                                    ToLaserBlade.makeId("item/laser_blade_2d_2")), modelOutput),
                    new LaserBladeTintSource(LaserBladeColorPart.GRIP),
                    new LaserBladeTintSource(LaserBladeColorPart.OUTER_BLADE),
                    new LaserBladeTintSource(LaserBladeColorPart.INNER_BLADE));

            return ItemModelUtils.conditional(new UsingOriginalModel(), specialModel, vanillaModel);
        }

        private ResourceLocation createLBSwordModel(ResourceLocation location, LBSwordItemTransforms transforms, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
            modelOutput.accept(location, () -> (new Gson()).toJsonTree(new LBSwordModel(ResourceLocation.withDefaultNamespace("item/iron_ingot"), transforms.get())));
            return location;
        }

        public static class LBSwordModel {
            @SerializedName("gui_light")
            public String guiLight = "front";
            public Map<String, String> textures = new HashMap<>();
            public Map<String, Map<String, float[]>> display = new HashMap<>();

            public LBSwordModel(ResourceLocation particle, ItemTransforms itemTransforms) {
                textures.put("particle", particle.toString());
                Arrays.stream(ItemDisplayContext.values())
                        .forEach(c -> addTransform(c.getSerializedName(), itemTransforms.getTransform(c)));
            }

            private void addTransform(String displayContext, ItemTransform itemTransform) {
                HashMap<String, float[]> transform = new HashMap<>();
                if (!ItemTransform.NO_TRANSFORM.scale().equals(itemTransform.scale())) {
                    Vector3fc v = itemTransform.scale();
                    transform.put("scale", new float[]{v.x(), v.y(), v.z()});
                }
                if (!ItemTransform.NO_TRANSFORM.rotation().equals(itemTransform.rotation())) {
                    Vector3fc v = itemTransform.rotation();
                    transform.put("rotation", new float[]{v.x(), v.y(), v.z()});
                }
                if (!ItemTransform.NO_TRANSFORM.translation().equals(itemTransform.translation())) {
                    Vector3fc v = itemTransform.translation();
                    transform.put("translation", new float[]{v.x() * 16F, v.y() * 16F, v.z() * 16F});
                }
                if (!transform.isEmpty()) {
                    display.put(displayContext, transform);
                }
            }
        }
    }
}
