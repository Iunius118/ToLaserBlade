package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ToLaserBladeItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Ja_jpLanguageGenerator extends LanguageProvider {
    public Ja_jpLanguageGenerator(DataGenerator gen) {
        super(gen, ToLaserBlade.MOD_ID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(ToLaserBladeItems.DX_LASER_BLADE, "DXレーザーブレ一ド");
        add(ToLaserBladeItems.LASER_BLADE, "レーザーブレイド");

        // Configs
        add("tolaserblade.configgui.common.enableBlockingWithLaserBlade", "レーザーブレイドによる防御を有効にする");
        add("tolaserblade.configgui.common.laserBladeEfficiency", "レーザーブレイドの採掘速度 (0–128)");
        add("tolaserblade.configgui.client.enableLaserBlade3DModel", "レーザーブレイドを3Dモデルで描画する");
        add("tolaserblade.configgui.client.laserBladeRenderingMode", "レーザーブレイドの描画モード (0–1)");

        // Update message
        add("tolaserblade.update.newVersion", "%s の新しいバージョンが利用可能です");
    }
}
