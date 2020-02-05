package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ToLaserBladeItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class ToLaserBladeItemModelProvider extends ItemModelProvider {
    public ToLaserBladeItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        String dx_laser_blade = ToLaserBladeItems.DX_LASER_BLADE.getRegistryName().getPath();
        getBuilder(dx_laser_blade)
                .parent(new UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + dx_laser_blade);
    }

    @Override
    public String getName() {
        return "ToLaserBlade item models";
    }
}
