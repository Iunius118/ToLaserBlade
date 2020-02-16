package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ToLaserBladeItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class En_usLanguageProvider extends LanguageProvider {
    public En_usLanguageProvider(DataGenerator gen) {
        super(gen, ToLaserBlade.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(ToLaserBladeItems.DX_LASER_BLADE, "DX Laser B1ade");
        add(ToLaserBladeItems.LASER_BLADE, "Laser Blade");

        // Configs
        add("tolaserblade.configgui.enableLaserBlade3DModel", "Enable Laser Blade 3D Model");
        add("tolaserblade.configgui.laserBladeRenderingMode", "Laser Blade Rendering mode (0–1)");
        add("tolaserblade.configgui.enableBlockingWithLaserBlade", "Enable Blocking with Laser Blade");
        add("tolaserblade.configgui.laserBladeEfficiency", "Laser Blade Mining Speed (0–128)");

        // Update message
        add("tolaserblade.update.newversion", "A new %s version is available");
    }
}
