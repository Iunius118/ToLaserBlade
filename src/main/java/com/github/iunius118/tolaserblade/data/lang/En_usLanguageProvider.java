package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;

public class En_usLanguageProvider extends LanguageProvider {
    public En_usLanguageProvider(PackOutput packOutput) {
        super(packOutput, ToLaserBlade.MOD_ID, "en_us");
    }

    public En_usLanguageProvider(PackOutput packOutput, String modid, String locale) {
        super(packOutput, modid, locale);
    }

    @Override
    protected void addTranslations() {
        // Item group
        add(LaserBladeTextKey.KEY_ITEM_GROUP_GENERAL.getKey(), ToLaserBlade.MOD_NAME);

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
        addEnchantment(ModEnchantments.LIGHT_ELEMENT, "Light Element",
                "Increases damage from Laser Blade, and deals additional damage to undead mobs and illagers.");

        // Item tooltip
        add(LaserBladeTextKey.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_1.getKey(), "Hold in your hand and");
        add(LaserBladeTextKey.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_2.getKey(), "right-click to obtain");
        add(LaserBladeTextKey.KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_3.getKey(), "Laser Blade");
        add(LaserBladeTextKey.KEY_TOOLTIP_REMOVE.getKey(), "Remove %s");
        add(LaserBladeTextKey.KEY_TOOLTIP_MODEL.getKey(), "Model %s");

        // Upgrade tooltip
        add(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_DAMAGE.getKey(), "Laser Power %s");
        add(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_SPEED.getKey(), "Recharge %s");
        add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.getKey(), "HEAT RESISTANT 8000");

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
        add("tolaserblade.configgui.client.useOriginalModelType", "Use Original Model Type");
        add("tolaserblade.configgui.client.renderMultipleModels", "Render Multiple Models");
        add("tolaserblade.configgui.client.defaultModel", "Default Model");

        // Update message
        add("tolaserblade.update.newVersion", "A new %s version is available");
    }

    public void addEnchantment(Enchantment enchantment, String name, String description) {
        // Register enchantment name
        add(enchantment, name);

        // Support for Enchantment Descriptions mod
        String id = "tolaserblade.light_element";
        add("enchantment." + id + ".desc", description);
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
