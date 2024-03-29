package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.data.PackOutput;

public class Ja_jpLanguageProvider extends En_usLanguageProvider {
    public Ja_jpLanguageProvider(PackOutput packOutput) {
        super(packOutput, ToLaserBlade.MOD_ID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        // Item group
        add(LaserBladeTextKey.KEY_ITEM_GROUP_GENERAL.getKey(), ToLaserBlade.MOD_NAME);

        // Items
        add(ModItems.DX_LASER_BLADE, "DXレーザーブレ一ド");
        add(ModItems.LASER_BLADE, "レーザーブレイド");
        add(ModItems.LASER_BLADE_FP, "レーザーブレイド");
        add(ModItems.LB_BRAND_NEW, "新品のレーザーブレイド");
        add(ModItems.LB_BRAND_NEW_1, "新品のレーザーブレイドⅠ");
        add(ModItems.LB_BRAND_NEW_2, "新品のレーザーブレイドⅡ");
        add(ModItems.LB_BRAND_NEW_FP, "新品のレーザーブレイド");
        add(ModItems.LB_BROKEN, "壊れたレーザーブレイド");
        add(ModItems.LB_BROKEN_FP, "壊れたレーザーブレイド");
        add(ModItems.LB_DISASSEMBLED, "分解したレーザーブレイド");
        add(ModItems.LB_DISASSEMBLED_FP, "分解したレーザーブレイド");
        add(ModItems.LB_BLUEPRINT, "レーザーブレイドの設計図");
        add(ModItems.LB_BATTERY, "LB電池");
        add(ModItems.LB_MEDIUM, "レーザー媒質");
        add(ModItems.LB_EMITTER, "レーザーブレイド放射器");
        add(ModItems.LB_CASING, "レーザーブレイドの外装");
        add(ModItems.LB_CASING_FP, "レーザーブレイドの外装");

        // Enchantments
        addEnchantment(ModEnchantments.LIGHT_ELEMENT, "光属性",
                "レーザーブレイドのダメージを増加させ、アンデッドMobや邪悪な村人に追加ダメージを与える");

        // item tooltip
        add(LaserBladeTextKey.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_1.getKey(), "手に持って右クリックで");
        add(LaserBladeTextKey.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_2.getKey(), "レーザーブレイドを入手");
        add(LaserBladeTextKey.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_3.getKey(), "できます");
        add(LaserBladeTextKey.KEY_TOOLTIP_REMOVE.getKey(), "%sを除去");
        add(LaserBladeTextKey.KEY_TOOLTIP_MODEL.getKey(), "モデル%s");

        // Upgrade tooltip
        add(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_DAMAGE.getKey(), "レーザー出力 %s");
        add(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_SPEED.getKey(), "充電効率 %s");
        add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.getKey(), "耐熱強化 8000");

        // Advancements
        add("advancements.tolaserblade.main.root.title", "ToLaserBlade");
        add("advancements.tolaserblade.main.root.description", "レーザーブレイドは切削用工具です。武器として使用した際に発生した損害は、すべて補償の対象外となります");
        add("advancements.tolaserblade.main.dx_laser_blade.title", "レーザーブレイド？");
        add("advancements.tolaserblade.main.dx_laser_blade.description", "DXレーザーブレ一ドを入手する。光る！ 鳴る！ (単4電池があれば…)");
        add("advancements.tolaserblade.main.laser_blade.title", "超古代技術");
        add("advancements.tolaserblade.main.laser_blade.description", "レーザーブレイドを入手する");
        add("advancements.tolaserblade.main.laser_blade_light_element_5.title", "光のチカラ");
        add("advancements.tolaserblade.main.laser_blade_light_element_5.description", "レーザーブレイドに 光属性 Ⅴ のエンチャントを付与する");
        add("advancements.tolaserblade.main.laser_blade_light_element_10.title", "無限のパワー");
        add("advancements.tolaserblade.main.laser_blade_light_element_10.description", "レーザーブレイドに 光属性 Ⅹ のエンチャントを付与する");
        add("advancements.tolaserblade.main.laser_blade_mending_1.title", "無期限サポート");
        add("advancements.tolaserblade.main.laser_blade_mending_1.description", "レーザーブレイドに 修繕 のエンチャントを付与する");
        add("advancements.tolaserblade.main.laser_blade_attack_10.title", "９以上だ");
        add("advancements.tolaserblade.main.laser_blade_attack_10.description", "レーザーブレイドの攻撃力を 3回 アップグレードする");
        add("advancements.tolaserblade.main.laser_blade_attack_15.title", "限界突破");
        add("advancements.tolaserblade.main.laser_blade_attack_15.description", "レーザーブレイドの攻撃力を 8回 アップグレードする");
        add("advancements.tolaserblade.main.laser_blade_looting_3.title", "３つくれ");
        add("advancements.tolaserblade.main.laser_blade_looting_3.description", "レーザーブレイドに ドロップ増加 Ⅲ のエンチャントを付与する");
        add("advancements.tolaserblade.main.break_laser_blade.title", "返品交換");
        add("advancements.tolaserblade.main.break_laser_blade.description", "レーザーブレイドを使い切り、そして…");
        add("advancements.tolaserblade.main.laser_blade_fp.title", "核の中へ");
        add("advancements.tolaserblade.main.laser_blade_fp.description", "レーザーブレイドをネザライトインゴットで強化する");

        // Configs
        add("tolaserblade.configgui.server.enableBlockingWithLaserBlade", "レーザーブレイドによる防御を有効にする");
        add("tolaserblade.configgui.server.laserBladeEfficiency", "レーザーブレイドの採掘速度 (0–128)");
        add("tolaserblade.configgui.server.maxAttackDamageUpgradeCount", "攻撃力の最大強化回数 (0–39)");
        add("tolaserblade.configgui.server.enableLaserTrap", "レーザートラップを有効にする");
        add("tolaserblade.configgui.server.canLaserTrapAttackPlayer", "レーザートラップがプレイヤーへ攻撃可能か");
        add("tolaserblade.configgui.server.canLaserTrapHeatUpFurnace", "レーザートラップがかまどを加熱可能か");

        add("tolaserblade.configgui.client.showUpdateMessage", "アップデートの通知を表示する");
        add("tolaserblade.configgui.client.useFixedVertexBuffer", "Fixed Vertex Bufferを使用する");
        add("tolaserblade.configgui.client.useOriginalModelType", "独自のモデルタイプを使用する");
        add("tolaserblade.configgui.client.renderMultipleModels", "レーザーブレードを複数モデルで描画する");
        add("tolaserblade.configgui.client.defaultModel", "デフォルトのモデル");

        // Update message
        add("tolaserblade.update.newVersion", "%s の新しいバージョンが利用可能です");
    }
}
