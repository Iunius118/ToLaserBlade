package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ToLaserBlade.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateItemModels(itemModels);
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.LASER_BLADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_MEDIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_EMITTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_CASING.get(), ModelTemplates.FLAT_ITEM);
    }
}
