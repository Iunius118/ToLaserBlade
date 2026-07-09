package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.gui.BlueprintScreen;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.ModPipelines;
import com.github.iunius118.tolaserblade.integration.jei.JEIModPlugin;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeManager;
import com.github.iunius118.tolaserblade.menu.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class ToLaserBladeClient {

    public ToLaserBladeClient(IEventBus modEventBus, ModContainer modContainer) {
        IEventBus eventBus = NeoForge.EVENT_BUS;

        // Register event listeners
        eventBus.addListener(this::onRecipesReceived);

        // Register mod event listeners
        modEventBus.addListener(this::registerMenuScreens);
        modEventBus.addListener(this::registerSpecialModelRenderer);
        modEventBus.addListener(this::registerRenderPipelines);

        // Register laser blade json models using ToLaserBlade API
        ToLaserBladeAPI.registerModelRegistrationListener(LaserBladeModelManager::resisterModels);
        // Register laser blade model event listeners
        modEventBus.addListener(this::modifyBakingResult);
    }

    private void onRecipesReceived(RecipesReceivedEvent event) {
        ModRecipeManager.setClientSyncedRecipes(event.getRecipeMap());
        JEIModPlugin.registrationPhase = JEIModPlugin.Phase.BEFORE_REGISTRATION;

        if (JEIModPlugin.recipeRegisters != null) {
            // If JEIModPlugin.registerRecipes() is called before recipe synchronization, register them again
            JEIModPlugin.recipeRegisters.run();
            JEIModPlugin.recipeRegisters = null;
        }
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BLUEPRINT, BlueprintScreen::new);
    }

    private void registerSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
        event.register(CommonClass.modLocation("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        ModPipelines.MOD_PIPELINES.forEach(event::registerPipeline);
    }

    private void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        LaserBladeModelManager.getInstance().reload();
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }
}
