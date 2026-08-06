package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.gui.BlueprintScreen;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.renderer.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.menu.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ToLaserBladeClient {

    public static void initClient(FMLJavaModLoadingContext context) {
        final var modBusGroup = context.getModBusGroup();

        // Register event listeners
        FMLClientSetupEvent.getBus(modBusGroup).addListener(ToLaserBladeClient::setup);

        // Register laser blade json models using ToLaserBlade API
        ToLaserBladeAPI.registerModelRegistrationListener(LaserBladeModelManager::resisterModels);
        // Register laser blade model event listeners
        ModelEvent.ModifyBakingResult.BUS.addListener(ToLaserBladeClient::modifyBakingResult);
        // Register special model renderers
        SpecialModelRenderers.ID_MAPPER
                .put(CommonClass.modLocation("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private static void setup(final FMLClientSetupEvent event) {
        // Register screen for mod menu
        MenuScreens.register(ModMenuTypes.BLUEPRINT, BlueprintScreen::new);
    }

    private static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        LaserBladeModelManager.getInstance().reload();
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }
}
