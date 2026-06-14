package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.block.ModBlocks;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.template.ElementBuilder;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateBlockModels(blockModels);
        generateItemModels(itemModels);
    }

    private void generateBlockModels(BlockModelGenerators blockModels) {
        generateBlueprintBlock(blockModels, ModBlocks.BL_BLUEPRINT);
    }

    private void generateBlueprintBlock(BlockModelGenerators blockModels, Block block) {
        Identifier modelLocation = ModelLocationUtils.getModelLocation(block);
        Consumer<ElementBuilder> element = builder -> builder
                .from(0, 0, 0).to(16, 1, 16)
                .face(Direction.UP, f -> f.texture(TextureSlot.TOP).uvs(0, 0, 16, 16))
                .face(Direction.DOWN, f -> f.texture(TextureSlot.BOTTOM).uvs(0, 0, 16, 16).cullface(Direction.DOWN))
                .face(Direction.NORTH, f -> f.texture(TextureSlot.SIDE).uvs(0, 12, 16, 13).cullface(Direction.NORTH))
                .face(Direction.EAST, f -> f.texture(TextureSlot.SIDE).uvs(0, 13, 16, 14).cullface(Direction.EAST))
                .face(Direction.SOUTH, f -> f.texture(TextureSlot.SIDE).uvs(0, 14, 16, 15).cullface(Direction.SOUTH))
                .face(Direction.WEST, f -> f.texture(TextureSlot.SIDE).uvs(0, 15, 16, 16).cullface(Direction.WEST));
        var textureMapping = new TextureMapping()
                .put(TextureSlot.TOP, new Material(modelLocation.withSuffix("_top")))
                .put(TextureSlot.SIDE, new Material(modelLocation.withSuffix("_side")))
                .put(TextureSlot.BOTTOM, new Material(modelLocation.withSuffix("_bottom")))
                .put(TextureSlot.PARTICLE, new Material(modelLocation.withSuffix("_top")));
        Identifier model = ExtendedModelTemplateBuilder.builder()
                .parent(this.mcLocation("block/block"))
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .element(element)
                .build().create(modelLocation, textureMapping, blockModels.modelOutput);
        var multiVariant = BlockModelGenerators.plainVariant(model);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, multiVariant)
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        final var itemModelOutput = itemModels.itemModelOutput;
        final var modelOutput = itemModels.modelOutput;

        // Blueprint
        generateBlueprintItem(itemModels, ModItems.BL_BLUEPRINT);

        // Laser blades
        Item laserBlade = ModItems.LASER_BLADE;
        ItemModel.Unbaked lbSwordModel = generateLBSwordModel(laserBlade, modelOutput);
        itemModelOutput.register(laserBlade,
                new ClientItem(lbSwordModel, new ClientItem.Properties(false, true, 1.0F)));
        Item laserBladeFp = ModItems.LASER_BLADE_FP;
        itemModelOutput.register(laserBladeFp,
                new ClientItem(lbSwordModel, new ClientItem.Properties(false, true, 1.0F)));

        // Laser blade parts
        itemModels.generateFlatItem(ModItems.LB_BATTERY, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_MEDIUM, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_EMITTER, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_CASING, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_CASING_FP, ModItems.LB_CASING, ModelTemplates.FLAT_ITEM);
    }

    private void generateBlueprintItem(ItemModelGenerators itemModels, Item item) {
        Identifier itemId = getItemId(item);
        Identifier blockTexture = itemId.withPrefix("block/").withSuffix("_top");
        // Default item model
        ItemModel.Unbaked plainModel = ItemModelUtils.plainModel(
                itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        // Item model in item frame
        ItemModel.Unbaked fixedModel = ItemModelUtils.plainModel(
                ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item).withSuffix("_fixed"),
                        new TextureMapping().put(TextureSlot.LAYER0, new Material(blockTexture)),
                        itemModels.modelOutput));
        ItemModel.Unbaked itemModel = ItemModelUtils.select(
                new DisplayContext(), plainModel, ItemModelUtils.when(ItemDisplayContext.FIXED, fixedModel));
        itemModels.itemModelOutput.accept(item, itemModel);
    }

    private ItemModel.Unbaked generateLBSwordModel(Item item, BiConsumer<Identifier, ModelInstance> modelOutput) {
        /* Also transform the off-hand pose while blocking (it will not be transformed if these are commented out).
        ItemModel.Unbaked blockingLeft = ItemModelUtils.specialModel(
                getLBSwordModel("_blocking_left"), new LBSwordSpecialRenderer.Unbaked());
        ItemModel.Unbaked blockingRight = ItemModelUtils.specialModel(
                getLBSwordModel("_blocking_right"), new LBSwordSpecialRenderer.Unbaked());

        ItemModel.Unbaked blockingModel = ItemModelUtils.select(
                new MainHand(), blockingRight, ItemModelUtils.when(HumanoidArm.LEFT, blockingLeft));
        ItemModel.Unbaked defaultModel = ItemModelUtils.specialModel(
                getLBSwordModel(""), new LBSwordSpecialRenderer.Unbaked());

        return ItemModelUtils.conditional(new Blocking(), blockingModel, defaultModel);
        //*/

        //*
        return ItemModelUtils.specialModel(getLBSwordModel(""), new LBSwordSpecialRenderer.Unbaked());
        //*/
    }

    private Identifier getLBSwordModel(String suffix) {
        return CommonClass.modLocation("item/lb_sword" + suffix);
    }

    private Identifier getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
