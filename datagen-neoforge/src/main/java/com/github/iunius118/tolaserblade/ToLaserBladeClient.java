package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class ToLaserBladeClient {

    public ToLaserBladeClient(IEventBus modEventBus, ModContainer modContainer) {
        // Register mod event listeners
        modEventBus.addListener(this::registerSpecialModelRenderer);
    }

    private void registerSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
        event.register(CommonClass.modLocation("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
