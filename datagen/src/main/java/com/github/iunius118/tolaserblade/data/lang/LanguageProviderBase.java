package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.block.ModBlocks;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class LanguageProviderBase extends LanguageProvider {
	// Creative mode tab title
	public String mainItemGroup = Constants.MOD_NAME;
    // Block names
    public String laserBladeBlueprint;
	// Item names
	public String laserBlade;
	public String laserBladeFp;
	public String lbEnergyCell;
	public String laserMedium;
	public String laserBladeEmitter;
	public String laserBladeCasing;
	public String laserBladeCasingFp;
    // Item tooltips
    public String tooltipFireResistant;
    // Tags
    public String tagLaserBlades;
    public String tagLaserBladeEnchantable;
    public String tagSensitiveToLaserBlade;
	// Enchantments
	public String laserBladeEnchantment;
	public String laserBladeEnchantmentDesc;
	public String lightElementEnchantment;
	public String lightElementEnchantmentDesc;
	public String repulsiveForceEnchantment;
	public String repulsiveForceEnchantmentDesc;
    // Data Packs
    public String packRepulsiveForceName;
    public String packRepulsiveForceDescription;


	public LanguageProviderBase(PackOutput output, String locale) {
		super(output, Constants.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		// Creative mode tab title
		add("itemGroup.tolaserblade.main", mainItemGroup);

        // Block names
        add(ModBlocks.BL_BLUEPRINT, laserBladeBlueprint);

		// Item names
		add(ModItems.LASER_BLADE, laserBlade);
		add(ModItems.LASER_BLADE_FP, laserBladeFp);
		add(ModItems.LB_BATTERY, lbEnergyCell);
		add(ModItems.LB_MEDIUM, laserMedium);
		add(ModItems.LB_EMITTER, laserBladeEmitter);
		add(ModItems.LB_CASING, laserBladeCasing);
		add(ModItems.LB_CASING_FP, laserBladeCasingFp);

        // Item tooltips
        add("tooltip.tolaserblade.fire_resistant", tooltipFireResistant);

        // Tags
        add(ModTags.Items.LASER_BLADES, tagLaserBlades);
        add(ModTags.Items.LASER_BLADE_ENCHANTABLE, tagLaserBladeEnchantable);
        add(ModTags.EntityTypes.SENSITIVE_TO_LASER_BLADE, tagSensitiveToLaserBlade);

		// Enchantments
		addEnchantment(ModEnchantments.LASER_BLADE, laserBladeEnchantment, laserBladeEnchantmentDesc);
		addEnchantment(ModEnchantments.LIGHT_ELEMENT, lightElementEnchantment, lightElementEnchantmentDesc);
		addEnchantment(ModEnchantments.REPULSIVE_FORCE, repulsiveForceEnchantment, repulsiveForceEnchantmentDesc);

        // Data Packs
        add(Constants.DataPacks.REPULSIVE_FORCE.nameKey(), packRepulsiveForceName);
        add(Constants.DataPacks.REPULSIVE_FORCE.descriptionKey(), packRepulsiveForceDescription);
	}

	public void addEnchantment(ResourceKey<Enchantment> enchantment, String name, String description) {
		// Register enchantment name
		String id = Util.makeDescriptionId("enchantment", enchantment.identifier());
		add(id, name);

		// Support for Enchantment Descriptions mod
		add(id + ".desc", description);
	}
}
