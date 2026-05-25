package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.extensions.LBSwordItemExtensions;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.renderer.Blocking;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.registry.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ToLaserBlade.MOD_ID, dist = Dist.CLIENT)
public class ToLaserBladeClient {
    public ToLaserBladeClient(IEventBus modEventBus, ModContainer modContainer) {
        // Register client-side lifecycle event listeners

        // Register client-side event handlers
        modEventBus.addListener(this::onRegisterSpecialModelRenderer);
        // Also transform the off-hand pose while blocking (it will not be transformed if these are commented out)
        //modEventBus.addListener(this::onRegisterConditionalItemModelPropertyEvent);
        //modEventBus.addListener(this::onRegisterClientExtensionsEvent);

        // Register laser blade json models using ToLaserBlade API
        ToLaserBladeAPI.registerModelRegistrationListener(event -> event.register(LaserBladeModelManager.loadModels()));
        // Register model event listeners
        modEventBus.addListener(this::onModifyBakingResultEvent);
        modEventBus.addListener(this::onBakingCompletedEvent);

        // Allows NeoForge to create a config screen for this mod's configs
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void onRegisterSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
        event.register(ToLaserBlade.id("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private void onRegisterConditionalItemModelPropertyEvent(RegisterConditionalItemModelPropertyEvent event) {
        event.register(ToLaserBlade.id("blocking"), Blocking.MAP_CODEC);
    }

    private void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {
        event.registerItem(new LBSwordItemExtensions(), ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP);
    }

    private void onModifyBakingResultEvent(ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        LaserBladeModelManager.getInstance().reload();
    }

    private void onBakingCompletedEvent(ModelEvent.BakingCompleted event) {
        // Log loaded laser blade model count
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }
}
