package com.github.iunius118.tolaserblade.integration.jei;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.platform.Services;
import mezz.jei.api.IModPlugin;
import net.minecraft.resources.Identifier;

public class JEIModPlugin implements IModPlugin {
    public static final Identifier UID = CommonClass.modLocation("main");

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    public JEIModPlugin() {
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            Constants.LOG.info("JEI plugin ({}) is loaded on {}", getPluginUid(), Services.PLATFORM.getPlatformName());
        }
    }
}
