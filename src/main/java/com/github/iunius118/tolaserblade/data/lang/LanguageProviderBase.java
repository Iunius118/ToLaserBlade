package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class LanguageProviderBase extends LanguageProvider {
	// Creative mode tab title
	public String mainItemGroup = ToLaserBlade.MOD_NAME;
	// Item names
	public String laserBlade;
	public String laserBladeFp;
	public String lbEnergyCell;
	public String laserMedium;
	public String laserBladeEmitter;
	public String lLaserBladeCasing;
	public String lLaserBladeCasingFp;
	// Enchantments
	public String laserBladeEnchantment;
	public String laserBladeEnchantmentDesc;
	public String lightElementEnchantment;
	public String lightElementEnchantmentDesc;
	public String repulsiveForceEnchantment;
	public String repulsiveForceEnchantmentDesc;


	public LanguageProviderBase(PackOutput output, String locale) {
		super(output, ToLaserBlade.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		// Creative mode tab title
		add("itemGroup.tolaserblade.main", mainItemGroup);

		// Item names
		add(ModItems.LASER_BLADE.get(), laserBlade);
		add(ModItems.LASER_BLADE_FP.get(), laserBladeFp);
		add(ModItems.LB_BATTERY.get(), lbEnergyCell);
		add(ModItems.LB_MEDIUM.get(), laserMedium);
		add(ModItems.LB_EMITTER.get(), laserBladeEmitter);
		add(ModItems.LB_CASING.get(), lLaserBladeCasing);
		add(ModItems.LB_CASING_FP.get(), lLaserBladeCasingFp);

		// Enchantments
		addEnchantment(ModEnchantments.LASER_BLADE, laserBladeEnchantment, laserBladeEnchantmentDesc);
		addEnchantment(ModEnchantments.LIGHT_ELEMENT, lightElementEnchantment, lightElementEnchantmentDesc);
		addEnchantment(ModEnchantments.REPULSIVE_FORCE, repulsiveForceEnchantment, repulsiveForceEnchantmentDesc);
	}

	public void addEnchantment(ResourceKey<Enchantment> enchantment, String name, String description) {
		// Register enchantment name
		String id = Util.makeDescriptionId("enchantment", enchantment.identifier());
		add(id, name);

		// Support for Enchantment Descriptions mod
		add(id + ".desc", description);
	}
}
