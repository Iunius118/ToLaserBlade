package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class TLBItemModelProvider extends ItemModelProvider {
    public TLBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerHandheldItemModel(ModItems.DX_LASER_BLADE);

        String laser_blade = ModItems.LASER_BLADE.getRegistryName().getPath();
        getBuilder(laser_blade)
                .parent(new UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + laser_blade + "_2d_0")
                .texture("layer1", "item/" + laser_blade + "_2d_1")
                .texture("layer2", "item/" + laser_blade + "_2d_2");

        String lb_broken = ModItems.LB_BROKEN.getRegistryName().getPath();
        getBuilder(lb_broken)
                .parent(new UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + laser_blade + "_2d_0");

        registerGeneratedItemModel(ModItems.LB_DISASSEMBLED);

        registerGeneratedItemModel(ModItems.LB_BLUEPRINT);

        registerGeneratedItemModel(ModItems.LB_BATTERY);

        registerGenerated2LayerItemModel(ModItems.LB_MEDIUM);

        registerGenerated2LayerItemModel(ModItems.LB_EMITTER);

        registerHandheldItemModel(ModItems.LB_CASING);
    }

    @Override
    public String getName() {
        return "ToLaserBlade Item Models";
    }

    private void registerGeneratedItemModel(Item item) {
        if (item == null) return;

        String itemPath = item.getRegistryName().getPath();
        getBuilder(itemPath)
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", "item/" + itemPath);
    }

    private void registerGenerated2LayerItemModel(Item item) {
        if (item == null) return;

        String itemPath = item.getRegistryName().getPath();
        getBuilder(itemPath)
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", "item/" + itemPath + "_0")
                .texture("layer1", "item/" + itemPath + "_1");

    }

    private void registerHandheldItemModel(Item item) {
        if (item == null) return;

        String itemPath = item.getRegistryName().getPath();
        getBuilder(itemPath)
                .parent(new UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + itemPath);
    }
}
