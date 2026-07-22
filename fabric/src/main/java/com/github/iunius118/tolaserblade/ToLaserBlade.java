package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.item.crafting.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class ToLaserBlade implements ModInitializer {

    @Override
    public void onInitialize() {
        // Use Fabric to bootstrap the Common mod.
        //Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Register mod event listeners
        addResourcePack();

        // Synchronize recipes to client
        synchronizeRecipeSerializer();
    }

    private void addResourcePack() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(Constants.MOD_ID);
        // Register built-in data/resource packs
        container.ifPresent(modContainer -> {
            // Repulsive Force Pack (Server Data Pack)
            ResourceLoader.registerBuiltinPack(Constants.DataPacks.REPULSIVE_FORCE.id(),
                    modContainer, Component.translatable(Constants.DataPacks.REPULSIVE_FORCE.nameKey()),
                    PackActivationType.DEFAULT_ENABLED);
            // Sample Sound Pack (Client Resource Pack)
            ResourceLoader.registerBuiltinPack(Constants.DataPacks.SAMPLE_SOUND_PACK.id(),
                    modContainer, Component.translatable(Constants.DataPacks.SAMPLE_SOUND_PACK.nameKey()),
                    PackActivationType.NORMAL);
        });
    }

    private void synchronizeRecipeSerializer() {
        RecipeSynchronization.synchronizeRecipeSerializer(BlendingRecipe.SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(ColoringRecipe.SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(CraftingRecipe.SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(EnchantmentRecipe.SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(RemodelRecipe.SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(RepairRecipe.SERIALIZER);
    }
}
