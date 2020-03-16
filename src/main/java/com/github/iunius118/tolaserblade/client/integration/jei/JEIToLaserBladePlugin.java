package com.github.iunius118.tolaserblade.client.integration.jei;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIToLaserBladePlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(ToLaserBlade.MOD_ID, "main");



    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
