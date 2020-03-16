package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class En_usLanguageProvider extends LanguageProvider {
    public En_usLanguageProvider(DataGenerator gen) {
        super(gen, ToLaserBlade.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Item group (only 'en_us')
        add("itemGroup.tolaserblade", ToLaserBlade.MOD_NAME);

        // Items
        add(ToLaserBladeItems.DX_LASER_BLADE, "DX Laser B1ade");
        add(ToLaserBladeItems.LASER_BLADE, "Laser Blade");

        // Configs
        add("tolaserblade.configgui.common.enableBlockingWithLaserBlade", "Enable Blocking with Laser Blade");
        add("tolaserblade.configgui.common.laserBladeEfficiency", "Laser Blade Mining Speed (0–128)");
        add("tolaserblade.configgui.client.enableLaserBlade3DModel", "Enable Laser Blade 3D Model");
        add("tolaserblade.configgui.client.laserBladeRenderingMode", "Laser Blade Rendering mode (0–1)");

        // Update message
        add("tolaserblade.update.newVersion", "A new %s version is available");
    }
}
