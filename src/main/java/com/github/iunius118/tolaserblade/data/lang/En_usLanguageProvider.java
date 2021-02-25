package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.LBBrandNewItem;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
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
        add(ModItems.DX_LASER_BLADE, "DX Laser B1ade");
        add(ModItems.LASER_BLADE, "Laser Blade");
        add(ModItems.LASER_BLADE_FP, "Laser Blade");
        add(ModItems.LB_BRAND_NEW, "Brand-new Laser Blade");
        add(ModItems.LB_BRAND_NEW_1, "Brand-new Laser Blade I");
        add(ModItems.LB_BRAND_NEW_2, "Brand-new Laser Blade II");
        add(ModItems.LB_BRAND_NEW_FP, "Brand-new Laser Blade");
        add(ModItems.LB_BROKEN, "Broken Laser Blade");
        add(ModItems.LB_BROKEN_FP, "Broken Laser Blade");
        add(ModItems.LB_DISASSEMBLED, "Disassembled Laser Blade");
        add(ModItems.LB_DISASSEMBLED_FP, "Disassembled Laser Blade");
        add(ModItems.LB_BLUEPRINT, "Laser Blade Blueprint");
        add(ModItems.LB_BATTERY, "LB Energy Cell");
        add(ModItems.LB_MEDIUM, "Laser Medium");
        add(ModItems.LB_EMITTER, "Laser Blade Emitter");
        add(ModItems.LB_CASING, "Laser Blade Casing");
        add(ModItems.LB_CASING_FP, "Laser Blade Casing");

        // Enchantments
        add(ModEnchantments.LIGHT_ELEMENT, "Light Element");

        // Item tooltip
        add(LBBrandNewItem.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_1, "Hold in your hand and");
        add(LBBrandNewItem.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_2, "right-click to obtain");
        add(LBBrandNewItem.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_3, "Laser Blade");
        add(LaserBladeItemBase.KEY_TOOLTIP_REMOVE, "Remove %s");
        add(LaserBladeItemBase.KEY_TOOLTIP_MODEL, "Model %s");

        // Upgrade tooltip
        add(LaserBladeItemBase.KEY_TOOLTIP_ATTACK_DAMAGE, "Laser Power %s");
        add(LaserBladeItemBase.KEY_TOOLTIP_ATTACK_SPEED, "Recharge %s");
        add(LaserBladeItemBase.KEY_TOOLTIP_FIREPROOF, "HEAT RESISTANT 8000");

        // Advancements
        add("advancements.tolaserblade.main.root.title", "ToLaserBlade");
        add("advancements.tolaserblade.main.root.description", "Laser Blade is a tool used to cut through material, so any damage caused by using as a weapon is not covered by the warranty");
        add("advancements.tolaserblade.main.dx_laser_blade.title", "Laser Blade?");
        add("advancements.tolaserblade.main.dx_laser_blade.description", "Obtain a DX Laser B1ade. It could glow and sound—if you had some AAA batteries");
        add("advancements.tolaserblade.main.laser_blade.title", "Ancient Technology");
        add("advancements.tolaserblade.main.laser_blade.description", "Obtain a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_light_element_5.title", "Power of Light");
        add("advancements.tolaserblade.main.laser_blade_light_element_5.description", "Add Light Element V enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_light_element_10.title", "Unlimited Power");
        add("advancements.tolaserblade.main.laser_blade_light_element_10.description", "Add Light Element X enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_mending_1.title", "Life-time Support");
        add("advancements.tolaserblade.main.laser_blade_mending_1.description", "Add Mending enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_attack_10.title", "It's Over 9");
        add("advancements.tolaserblade.main.laser_blade_attack_10.description", "Upgrade attack damage of a Laser Blade three times");
        add("advancements.tolaserblade.main.laser_blade_attack_15.title", "Beyond the Limit");
        add("advancements.tolaserblade.main.laser_blade_attack_15.description", "Upgrade attack damage of a Laser Blade eight times");
        add("advancements.tolaserblade.main.laser_blade_looting_3.title", "Give Me Three");
        add("advancements.tolaserblade.main.laser_blade_looting_3.description", "Add Looting III enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.break_laser_blade.title", "Returns and Exchanges");
        add("advancements.tolaserblade.main.break_laser_blade.description", "Completely use up a Laser Blade, and then...");
        add("advancements.tolaserblade.main.laser_blade_fp.title", "Into The Core");
        add("advancements.tolaserblade.main.laser_blade_fp.description", "Upgrade a Laser Blade with a Netherite Ingot");

        // Configs
        add("tolaserblade.configgui.server.enableBlockingWithLaserBlade", "Enable Blocking with Laser Blade");
        add("tolaserblade.configgui.server.laserBladeEfficiency", "Laser Blade Mining Speed (0–128)");
        add("tolaserblade.configgui.server.maxAttackDamageUpgradeCount", "Max Count of Attack Damage Upgrade (0–39)");
        add("tolaserblade.configgui.server.enableLaserTrap", "Enable Laser Trap");
        add("tolaserblade.configgui.server.canLaserTrapAttackPlayer", "Can Laser Trap Attack Player");
        add("tolaserblade.configgui.server.canLaserTrapHeatUpFurnace", "Can Laser Trap Heat Up Furnace");

        add("tolaserblade.configgui.client.showUpdateMessage", "Show Update Message");
        add("tolaserblade.configgui.client.useFixedVertexBuffer", "Use Fixed Vertex Buffer");
        add("tolaserblade.configgui.client.useInternalModel", "Use Internal Model");
        add("tolaserblade.configgui.client.renderMultipleModels", "Render Multiple Models");
        add("tolaserblade.configgui.client.internalModelType", "Internal Model Type");
        add("tolaserblade.configgui.client.externalModelType", "External Model Type");

        // Update message
        add("tolaserblade.update.newVersion", "A new %s version is available");
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
