package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateItemModels(itemModels);
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        final var itemModelOutput = itemModels.itemModelOutput;
        final var modelOutput = itemModels.modelOutput;

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
}
