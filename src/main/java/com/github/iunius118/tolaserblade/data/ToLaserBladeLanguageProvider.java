package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ToLaserBladeItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ToLaserBladeLanguageProvider {
    public static void addProviders(DataGenerator gen) {
        gen.addProvider(new En_usLang(gen));
        gen.addProvider(new Ja_jpLang(gen));
        gen.addProvider(new Zh_cnLang(gen));
    }

    private static class En_usLang extends LanguageProvider {
        public En_usLang(DataGenerator gen) {
            super(gen, ToLaserBlade.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            // Items
            add(ToLaserBladeItems.DX_LASER_BLADE, "DX Laser B1ade");

            // Configs
            add("tolaserblade.configgui.enableLaserBlade3DModel", "Enable Laser Blade 3D Model");
            add("tolaserblade.configgui.laserBladeRenderingMode", "Laser Blade Rendering mode (0–1)");
            add("tolaserblade.configgui.enableBlockingWithLaserBlade", "Enable Blocking with Laser Blade");
            add("tolaserblade.configgui.laserBladeEfficiency", "Laser Blade Mining Speed (0–128)");

            // Update message
            add("tolaserblade.update.newversion", "A new %s version is available");
        }
    }

    private static class Ja_jpLang extends LanguageProvider {
        public Ja_jpLang(DataGenerator gen) {
            super(gen, ToLaserBlade.MOD_ID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            // Items
            add(ToLaserBladeItems.DX_LASER_BLADE, "DXレーザーブレ一ド");

            // Configs
            add("tolaserblade.configgui.enableLaserBlade3DModel", "レーザーブレイドを3Dモデルで描画する");
            add("tolaserblade.configgui.laserBladeRenderingMode", "レーザーブレイドの描画モード (0–1)");
            add("tolaserblade.configgui.enableBlockingWithLaserBlade", "レーザーブレイドによる防御を有効にする");
            add("tolaserblade.configgui.laserBladeEfficiency", "レーザーブレイドの採掘速度 (0–128)");

            // Update message
            add("tolaserblade.update.newversion", "%s の新しいバージョンが利用可能です");
        }
    }

    private static class Zh_cnLang extends LanguageProvider {
        public Zh_cnLang(DataGenerator gen) {
            super(gen, ToLaserBlade.MOD_ID, "zh_cn");
        }

        @Override
        protected void addTranslations() {
            // Items
            add(ToLaserBladeItems.DX_LASER_BLADE, "DX激光剑");

            // Configs
            add("tolaserblade.configgui.enableLaserBlade3DModel", "启用激光剑3D模型");
            add("tolaserblade.configgui.laserBladeRenderingMode", "激光剑渲染模式 (0–1)");
            add("tolaserblade.configgui.enableBlockingWithLaserBlade", "启用激光剑格挡");
            add("tolaserblade.configgui.laserBladeEfficiency", "激光剑采掘速度 (0–128)");

            // Update message
            add("tolaserblade.update.newversion", "新版本 %s 已可用");

            // Please add zh_cn translations
        }
    }

    // ... and other language translations
}
