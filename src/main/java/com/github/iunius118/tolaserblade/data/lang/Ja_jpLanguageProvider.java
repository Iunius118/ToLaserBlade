package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Ja_jpLanguageProvider extends LanguageProvider {
    public Ja_jpLanguageProvider(DataGenerator gen) {
        super(gen, ToLaserBlade.MOD_ID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(ModItems.DX_LASER_BLADE, "DXレーザーブレ一ド");
        add(ModItems.LASER_BLADE, "レーザーブレイド");
        add(ModItems.LB_BROKEN, "壊れたレーザーブレイド");
        add(ModItems.LB_DISASSEMBLED, "分解したレーザーブレイド");
        add(ModItems.LB_BLUEPRINT, "レーザーブレイドの設計図");
        add(ModItems.LB_BATTERY, "LB電池");
        add(ModItems.LB_MEDIUM, "レーザー媒質");
        add(ModItems.LB_EMITTER, "レーザーブレイド放射器");
        add(ModItems.LB_CASING, "レーザーブレイドの外装");

        // Enchantments
        add(ModEnchantments.LIGHT_ELEMENT, "光属性");

        // Configs
        add("tolaserblade.configgui.common.enableBlockingWithLaserBlade", "レーザーブレイドによる防御を有効にする");
        add("tolaserblade.configgui.common.laserBladeEfficiency", "レーザーブレイドの採掘速度 (0–128)");
        add("tolaserblade.configgui.client.enableLaserBlade3DModel", "レーザーブレイドを3Dモデルで描画する");
        add("tolaserblade.configgui.client.laserBladeRenderingMode", "レーザーブレイドの描画モード (0–1)");

        // Update message
        add("tolaserblade.update.newVersion", "%s の新しいバージョンが利用可能です");
    }
}
