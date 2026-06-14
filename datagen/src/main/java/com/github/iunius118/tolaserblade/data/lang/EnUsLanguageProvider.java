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

        // Tags
        tagLaserBlades = "Laser Blades";
        tagLaserBladeEnchantable = "Laser Blade Enchantable";
        tagSensitiveToLaserBlade = "Sensitive to Laser Blade";

		// Enchantments
		laserBladeEnchantment = "Laser Blade";
		laserBladeEnchantmentDesc = "Increases attack damage and attack speed of the weapon.";
		lightElementEnchantment = "Light Element";
		lightElementEnchantmentDesc = "Increases damage against undead mobs and illagers.";
		repulsiveForceEnchantment = "Repulsive Force";
		repulsiveForceEnchantmentDesc =
				"Allows Laser Blades to block attacks and increases knockback strength at higher levels.";

        // Data Packs
        packRepulsiveForceName = "TLB Repulsive Force Pack";
        packRepulsiveForceDescription = "[ToLaserBlade] Add Repulsive Force enchantment";
	}
}
