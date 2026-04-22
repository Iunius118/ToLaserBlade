package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.registry.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ToLaserBlade.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateItemModels(itemModels);
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        Item laserBlade = ModItems.LASER_BLADE.get();
        ItemModel.Unbaked specialModel = ItemModelUtils.specialModel(
                createLBSwordModel(laserBlade, itemModels.modelOutput), new LBSwordSpecialRenderer.Unbaked());
        itemModels.itemModelOutput.accept(laserBlade, specialModel, new ClientItem.Properties(false, true, 1.0F));

        itemModels.generateFlatItem(ModItems.LB_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_MEDIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_EMITTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LB_CASING.get(), ModelTemplates.FLAT_ITEM);
    }

    private Identifier createLBSwordModel(Item item, BiConsumer<Identifier, ModelInstance> modelOutput) {
        Identifier location = ModelLocationUtils.getModelLocation(item, "");
        modelOutput.accept(location, () -> {
            var jsonObject = new JsonObject();
            jsonObject.addProperty("parent", "item/generated");
            jsonObject.addProperty("gui_light", "front");
            JsonObject textures = new JsonObject();
            textures.addProperty("particle", "minecraft:item/iron_ingot");
            jsonObject.add("textures", textures);
            return jsonObject;
        });
        return location;
    }
}
