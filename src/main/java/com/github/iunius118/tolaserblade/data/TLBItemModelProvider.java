package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class TLBItemModelProvider extends ItemModelProvider {
    public TLBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile generatedModel = new UncheckedModelFile("item/generated");
        ModelFile handheldModel = new UncheckedModelFile("item/handheld");

        String dx_laser_blade = ModItems.DX_LASER_BLADE.getRegistryName().getPath();
        getBuilder(dx_laser_blade)
                .parent(handheldModel)
                .texture("layer0", "item/" + dx_laser_blade);

        String laser_blade = ModItems.LASER_BLADE.getRegistryName().getPath();
        getBuilder(laser_blade)
                .parent(handheldModel)
                .texture("layer0", "item/" + laser_blade + "_2d_0")
                .texture("layer1", "item/" + laser_blade + "_2d_1")
                .texture("layer2", "item/" + laser_blade + "_2d_2");

        String lb_broken = ModItems.LB_BROKEN.getRegistryName().getPath();
        getBuilder(lb_broken)
                .parent(handheldModel)
                .texture("layer0", "item/" + laser_blade + "_2d_0");

        registerPartItemModel(ModItems.LB_DISASSEMBLED, generatedModel);

        registerPartItemModel(ModItems.LB_BLUEPRINT, generatedModel);

        registerPartItemModel(ModItems.LB_BATTERY, generatedModel);

        registerTwoLayerPartItemModel(ModItems.LB_MEDIUM, generatedModel);

        registerTwoLayerPartItemModel(ModItems.LB_EMITTER, generatedModel);

        registerPartItemModel(ModItems.LB_CASING, handheldModel);
    }

    @Override
    public String getName() {
        return "ToLaserBlade Item Models";
    }

    private void registerPartItemModel(Item item, ModelFile parent) {
        if (item == null) return;

        String itemPath = item.getRegistryName().getPath();
        getBuilder(itemPath)
                .parent(parent)
                .texture("layer0", "item/parts/" + itemPath);
    }

    private void registerTwoLayerPartItemModel(Item item, ModelFile parent) {
        if (item == null) return;

        String itemPath = item.getRegistryName().getPath();
        getBuilder(itemPath)
                .parent(parent)
                .texture("layer0", "item/parts/" + itemPath + "_0")
                .texture("layer1", "item/parts/" + itemPath + "_1");

    }
}
