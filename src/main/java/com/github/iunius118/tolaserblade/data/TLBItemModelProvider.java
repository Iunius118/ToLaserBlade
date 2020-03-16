package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class TLBItemModelProvider extends ItemModelProvider {
    public TLBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        String dx_laser_blade = ModItems.DX_LASER_BLADE.getRegistryName().getPath();
        getBuilder(dx_laser_blade)
                .parent(new UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + dx_laser_blade);

        String laser_blade = ModItems.LASER_BLADE.getRegistryName().getPath();
        getBuilder(laser_blade)
                .parent(new UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + laser_blade + "_2d_0")
                .texture("layer1", "item/" + laser_blade + "_2d_1")
                .texture("layer2", "item/" + laser_blade + "_2d_2");
    }

    @Override
    public String getName() {
        return "ToLaserBlade item models";
    }
}
