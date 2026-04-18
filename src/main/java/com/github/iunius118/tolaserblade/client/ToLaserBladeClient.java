package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ToLaserBlade.MOD_ID, dist = Dist.CLIENT)
public class ToLaserBladeClient {
    public ToLaserBladeClient(ModContainer modContainer) {
        // Register client-side lifecycle event listeners

        // Register client-side event handlers

        // Allows NeoForge to create a config screen for this mod's configs
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
