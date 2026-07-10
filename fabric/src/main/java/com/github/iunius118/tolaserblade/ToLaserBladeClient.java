package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.gui.BlueprintScreen;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeManager;
import com.github.iunius118.tolaserblade.menu.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.recipe.v1.sync.ClientRecipeSynchronizedEvent;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeMap;

public class ToLaserBladeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerMenuScreens();
        registerSpecialModelRenderers();

        // Register laser blade json models using ToLaserBlade API
        ToLaserBladeAPI.registerModelRegistrationListener(LaserBladeModelManager::resisterModels);
        // Register laser blade model event listeners
        registerClientReloadListeners();

        // Register client event listeners
        ClientRecipeSynchronizedEvent.EVENT.register(this::onRecipeSynchronized);
    }

    private void registerMenuScreens() {
        MenuScreens.register(ModMenuTypes.BLUEPRINT, BlueprintScreen::new);
    }

    private void registerSpecialModelRenderers() {
        SpecialModelRenderers.ID_MAPPER.put(CommonClass.modLocation("laser_blade"),
                LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private void registerClientReloadListeners() {
        ResourceLoader resourceLoader = ResourceLoader.get(PackType.CLIENT_RESOURCES);
        resourceLoader.registerReloadListener(ResourceReloaderKeys.Client.MODELS, (ResourceManagerReloadListener) m -> {
            // Load/Reload laser blade json models
            LaserBladeModelManager.getInstance().reload();
            LaserBladeModelManager.getInstance().logLoadedModelCount();
        });
    }

    private void onRecipeSynchronized(Minecraft client, SynchronizedRecipes recipes) {
        ModRecipeManager.setClientSyncedRecipes(RecipeMap.create(recipes.recipes()));
    }
}
