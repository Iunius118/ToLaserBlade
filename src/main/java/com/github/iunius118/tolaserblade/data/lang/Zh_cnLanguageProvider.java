package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ToLaserBladeItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Zh_cnLanguageProvider extends LanguageProvider {
    public Zh_cnLanguageProvider(DataGenerator gen) {
        super(gen, ToLaserBlade.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(ToLaserBladeItems.DX_LASER_BLADE, "DX激光剑");
        add(ToLaserBladeItems.LASER_BLADE, "激光剑");

        // Configs
        add("tolaserblade.configgui.common.enableBlockingWithLaserBlade", "启用激光剑格挡");
        add("tolaserblade.configgui.common.laserBladeEfficiency", "激光剑采掘速度 (0–128)");
        add("tolaserblade.configgui.client.enableLaserBlade3DModel", "启用激光剑3D模型");
        add("tolaserblade.configgui.client.laserBladeRenderingMode", "激光剑渲染模式 (0–1)");

        // Update message
        add("tolaserblade.update.newVersion", "新版本 %s 已可用");
    }
}
