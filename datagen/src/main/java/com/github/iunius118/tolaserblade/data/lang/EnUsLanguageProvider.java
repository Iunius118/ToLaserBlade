package com.github.iunius118.tolaserblade.data.lang;

import net.minecraft.data.PackOutput;

public class EnUsLanguageProvider extends LanguageProviderBase {

    public EnUsLanguageProvider(PackOutput output) {
        super(output, "en_us");

        // Block names
        laserBladeBlueprint = "Laser Blade Blueprint";

        // Item names
        laserBlade = "Laser Blade";
        laserBladeFp = "Laser Blade";
        lbEnergyCell = "LB Energy Cell";
        laserMedium = "Laser Medium";
        laserBladeEmitter = "Laser Blade Emitter";
        laserBladeCasing = "Laser Blade Casing";
        laserBladeCasingFp = "Laser Blade Casing";

        // Item tooltips
        tooltipFireResistant = "HEAT RESISTANT 8000";
        tooltipModel = "Model %s";

        // Tags
        tagLaserBlades = "Laser Blades";
        tagLaserBladeEnchantable = "Laser Blade Enchantable";
        tagBlueprintCanChangeColor = "Blueprint Can Change Color";
        tagLaserBladeUpgrade = "Laser Blade Upgrade";
        tagLightElementUpgrade = "Light Element Upgrade";
        tagRepulsiveForceUpgrade = "Repulsive Force Upgrade";
        tagLootingUpgrade = "Looting Upgrade";
        tagSensitiveToLaserBlade = "Sensitive to Laser Blade";

        // Enchantments
        laserBladeEnchantment = "Laser Blade";
        laserBladeEnchantmentDesc = "Increases attack damage and attack speed of the weapon.";
        lightElementEnchantment = "Light Element";
        lightElementEnchantmentDesc = "Increases damage against undead mobs and illagers.";
        repulsiveForceEnchantment = "Repulsive Force";
        repulsiveForceEnchantmentDesc =
                "Allows Laser Blades to block attacks and increases knockback strength at higher levels.";

        // Menus
        menuBlueprintTitle = "Laser Blade Blueprint";

        // Data Packs
        packRepulsiveForceName = "TLB Repulsive Force Pack";
        packRepulsiveForceDescription = "[ToLaserBlade] Add Repulsive Force enchantment";
    }
}
