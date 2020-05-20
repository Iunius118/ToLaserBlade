package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
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
        add(ModItems.LB_BROKEN, "Broken Laser Blade");
        add(ModItems.LB_DISASSEMBLED, "Disassembled Laser Blade");
        add(ModItems.LB_BLUEPRINT, "Laser Blade Blueprint");
        add(ModItems.LB_BATTERY, "LB Energy Cell");
        add(ModItems.LB_MEDIUM, "Laser Medium");
        add(ModItems.LB_EMITTER, "Laser Blade Emitter");
        add(ModItems.LB_CASING, "Laser Blade Casing");

        // Enchantments
        add(ModEnchantments.LIGHT_ELEMENT, "Light Element");

        // Upgrade tooltip
        add(LaserBladeItemBase.KEY_TOOLTIP_ATTACK_DAMAGE, "Laser Power %s");
        add(LaserBladeItemBase.KEY_TOOLTIP_ATTACK_SPEED, "Recharge %s");

        // Advancements
        add("advancements.tolaserblade.main.root.title", "ToLaserBlade");
        add("advancements.tolaserblade.main.root.description", "Laser Blade is a tool used to cut through material, so any damage caused by using as a weapon is not covered by the warranty");
        add("advancements.tolaserblade.main.dx_laser_blade.title", "Laser Blade?");
        add("advancements.tolaserblade.main.dx_laser_blade.description", "Obtain a DX Laser B1ade.\nIt could glow and sound—if you had some AAA batteries");
        add("advancements.tolaserblade.main.laser_blade.title", "Ancient Technology");
        add("advancements.tolaserblade.main.laser_blade.description", "Obtain a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_light_element_5.title", "Power of Light");
        add("advancements.tolaserblade.main.laser_blade_light_element_5.description", "Add Light Element V enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_light_element_10.title", "Unlimited Power");
        add("advancements.tolaserblade.main.laser_blade_light_element_10.description", "Add Light Element X enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_mending_1.title", "Life-time Support");
        add("advancements.tolaserblade.main.laser_blade_mending_1.description", "Add Mending enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.laser_blade_attack_10.title", "It's Over 9");
        add("advancements.tolaserblade.main.laser_blade_attack_10.description", "Increase attack damage of a Laser Blade to 10 or more");
        add("advancements.tolaserblade.main.laser_blade_attack_15.title", "Beyond the Limit");
        add("advancements.tolaserblade.main.laser_blade_attack_15.description", "Increase attack damage of a Laser Blade to 15");
        add("advancements.tolaserblade.main.laser_blade_looting_3.title", "Give Me Three");
        add("advancements.tolaserblade.main.laser_blade_looting_3.description", "Add Looting III enchantment to a Laser Blade");
        add("advancements.tolaserblade.main.break_laser_blade.title", "Returns and Exchanges");
        add("advancements.tolaserblade.main.break_laser_blade.description", "Completely use up a Laser Blade, and then...");

        // Configs
        add("tolaserblade.configgui.common.enableBlockingWithLaserBlade", "Enable Blocking with Laser Blade");
        add("tolaserblade.configgui.common.laserBladeEfficiency", "Laser Blade Mining Speed (0–128)");
        add("tolaserblade.configgui.common.maxAttackDamageUpgradeCount", "Max Count of Attack Damage Upgrade (0–39)");
        add("tolaserblade.configgui.common.enableLaserTrap", "Enable Laser Trap");
        add("tolaserblade.configgui.common.canLaserTrapAttackPlayer", "Can Laser Trap Attack Player");

        add("tolaserblade.configgui.client.enableLaserBlade3DModel", "Enable Laser Blade 3D Model");
        add("tolaserblade.configgui.client.laserBladeRenderingMode", "Laser Blade Rendering mode (0–2)");

        // Update message
        add("tolaserblade.update.newVersion", "A new %s version is available");
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
