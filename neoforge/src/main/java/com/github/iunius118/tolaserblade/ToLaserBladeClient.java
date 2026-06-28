package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.gui.BlueprintScreen;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.ModPipelines;
import com.github.iunius118.tolaserblade.menu.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class ToLaserBladeClient {

    public ToLaserBladeClient(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onRegisterMenuScreensEvent);
        modEventBus.addListener(this::onRegisterSpecialModelRenderer);
        modEventBus.addListener(this::onRegisterRenderPipelinesEvent);

        // Register laser blade json models using ToLaserBlade API
        ToLaserBladeAPI.registerModelRegistrationListener(LaserBladeModelManager::resisterModels);
        // Register laser blade model event listeners
        modEventBus.addListener(this::onModifyBakingResultEvent);
    }

    private void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BLUEPRINT, BlueprintScreen::new);
    }

    private void onRegisterSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
        event.register(CommonClass.modLocation("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private void onRegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event) {
        ModPipelines.MOD_PIPELINES.forEach(event::registerPipeline);
    }

    private void onModifyBakingResultEvent(ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        LaserBladeModelManager.getInstance().reload();
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }
}
