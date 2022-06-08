package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class TLBItemModelProvider extends ItemModelProvider {
    public TLBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile generatedModel = new UncheckedModelFile("item/generated");
        ModelFile handheldModel = new UncheckedModelFile("item/handheld");

        String dx_laser_blade = getItemId(ModItems.DX_LASER_BLADE).getPath();
        getBuilder(dx_laser_blade)
                .parent(handheldModel)
                .texture("layer0", "item/" + dx_laser_blade);

        String laser_blade = getItemId(ModItems.LASER_BLADE).getPath();
        getBuilder(laser_blade)
                .parent(handheldModel)
                .texture("layer0", "item/" + laser_blade + "_2d_0")
                .texture("layer1", "item/" + laser_blade + "_2d_1")
                .texture("layer2", "item/" + laser_blade + "_2d_2");
        registerChildModel(ModItems.LASER_BLADE_FP, laser_blade);

        String gripModel = "laser_blade_grip";
        getBuilder(gripModel)
                .parent(handheldModel)
                .texture("layer0", "item/" + laser_blade + "_2d_0");

        registerChildModel(ModItems.LB_BRAND_NEW, gripModel);
        registerChildModel(ModItems.LB_BRAND_NEW_1, gripModel);
        registerChildModel(ModItems.LB_BRAND_NEW_2, gripModel);
        registerChildModel(ModItems.LB_BRAND_NEW_FP, gripModel);
        registerChildModel(ModItems.LB_BROKEN, gripModel);
        registerChildModel(ModItems.LB_BROKEN_FP, gripModel);

        registerPartItemModel(ModItems.LB_DISASSEMBLED, generatedModel);
        registerChildModel(ModItems.LB_DISASSEMBLED_FP, getItemId(ModItems.LB_DISASSEMBLED).getPath());

        registerPartItemModel(ModItems.LB_BLUEPRINT, generatedModel);

        registerPartItemModel(ModItems.LB_BATTERY, generatedModel);

        registerTwoLayerPartItemModel(ModItems.LB_MEDIUM, generatedModel);

        registerTwoLayerPartItemModel(ModItems.LB_EMITTER, generatedModel);

        registerPartItemModel(ModItems.LB_CASING, handheldModel);
        registerChildModel(ModItems.LB_CASING_FP, getItemId(ModItems.LB_CASING).getPath());
    }

    @Override
    public String getName() {
        return "ToLaserBlade Item Models";
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    private void registerPartItemModel(Item item, ModelFile parent) {
        if (item == null) return;

        String itemPath = getItemId(item).getPath();
        getBuilder(itemPath)
                .parent(parent)
                .texture("layer0", "item/parts/" + itemPath);
    }

    private void registerTwoLayerPartItemModel(Item item, ModelFile parent) {
        if (item == null) return;

        String itemPath = getItemId(item).getPath();
        getBuilder(itemPath)
                .parent(parent)
                .texture("layer0", "item/parts/" + itemPath + "_0")
                .texture("layer1", "item/parts/" + itemPath + "_1");

    }

    private void registerChildModel(Item item, String parent) {
        if (item == null || parent == null) return;

        String itemPath = getItemId(item).getPath();
        ModelFile parentModel = new UncheckedModelFile(ToLaserBlade.MOD_ID + ":item/" + parent);
        getBuilder(itemPath)
                .parent(parentModel);
    }
}
