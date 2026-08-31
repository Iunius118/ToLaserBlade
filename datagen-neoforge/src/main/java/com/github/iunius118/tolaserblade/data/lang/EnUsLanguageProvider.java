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
        laserBladeEnchantment = new NameAndDescription(
                "Laser Blade",
                "Increases attack damage and attack speed of the weapon.");
        lightElementEnchantment = new NameAndDescription(
                "Light Element",
                "Increases damage against undead mobs and illagers.");
        repulsiveForceEnchantment = new NameAndDescription(
                "Repulsive Force",
                "Allows Laser Blades to block attacks and increases knockback strength at higher levels.");

        // Menus
        menuBlueprintTitle = "Laser Blade Blueprint";

        // Advancements
        mainRootAdvancement = new NameAndDescription(
                "ToLaserBlade",
                "Laser Blade is a tool used to cut through material, " +
                        "so any damage caused by using as a weapon is not covered by the warranty");
        laserBladeAdvancement = new NameAndDescription(
                "Ancient Technology",
                "Obtain a Laser Blade");
        laserBlade2Advancement = new NameAndDescription(
                "It's Over 9",
                "Add Laser Blade II enchantment to a Laser Blade");
        laserBlade5Advancement = new NameAndDescription(
                "Beyond the Limit",
                "Add Laser Blade V enchantment to a Laser Blade");
        lightElement2Advancement = new NameAndDescription(
                "Power of Light",
                "Add Light Element II enchantment to a Laser Blade");
        lightElement5Advancement = new NameAndDescription(
                "Unlimited Power",
                "Add Light Element V enchantment to a Laser Blade");
        looting3Advancement = new NameAndDescription(
                "Give Me Three",
                "Add Looting III enchantment to a Laser Blade");
        laserBladeFPAdvancement = new NameAndDescription(
                "Into the Core",
                "Upgrade a Laser Blade to a fire-resistant Laser Blade");
        mendingAdvancement = new NameAndDescription(
                "Life-time Support",
                "Add Mending enchantment to a Laser Blade");
        breakLaserBladeAdvancement = new NameAndDescription(
                "Returns and Exchanges",
                "Completely use up a Laser Blade, and then...");

        // Data Packs
        packRepulsiveForce = new NameAndDescription(
                "TLB Repulsive Force Pack",
                "[ToLaserBlade] Add Repulsive Force enchantment");

        // Resource Packs
        packSampleSound = new NameAndDescription(
                "TLB Sample Sound Pack",
                "[ToLaserBlade] Sample sound pack for laser blade");
    }
}
