package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.LBSwordModel;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ToLaserBlade.MOD_ID, dist = Dist.CLIENT)
public class ToLaserBladeClient {
    public ToLaserBladeClient(IEventBus modEventBus, ModContainer modContainer) {
        // Register client-side lifecycle event listeners

        // Register client-side event handlers
        modEventBus.addListener(this::onRegisterSpecialModelRenderer);
        modEventBus.addListener(this::onRegisterLayerDefinitions);

        // Allows NeoForge to create a config screen for this mod's configs
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void onRegisterSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
        event.register(ToLaserBlade.id("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LBSwordModel.LAYER_LOCATION, LBSwordModel::createLayer);
    }
}
