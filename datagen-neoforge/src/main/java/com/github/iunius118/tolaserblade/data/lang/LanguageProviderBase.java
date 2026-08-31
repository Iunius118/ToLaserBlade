package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.block.ModBlocks;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
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
    public String tooltipModel;
    // Tags
    public String tagLaserBlades;
    public String tagLaserBladeEnchantable;
    public String tagBlueprintCanChangeColor;
    public String tagLaserBladeUpgrade;
    public String tagLightElementUpgrade;
    public String tagRepulsiveForceUpgrade;
    public String tagLootingUpgrade;
    public String tagSensitiveToLaserBlade;
    // Enchantments
    public NameAndDescription laserBladeEnchantment;
    public NameAndDescription lightElementEnchantment;
    public NameAndDescription repulsiveForceEnchantment;
    // Menus
    public String menuBlueprintTitle;
    // Advancements
    public NameAndDescription mainRootAdvancement;
    public NameAndDescription laserBladeAdvancement;
    public NameAndDescription laserBlade2Advancement;
    public NameAndDescription laserBlade5Advancement;
    public NameAndDescription lightElement2Advancement;
    public NameAndDescription lightElement5Advancement;
    public NameAndDescription looting3Advancement;
    public NameAndDescription laserBladeFPAdvancement;
    public NameAndDescription mendingAdvancement;
    public NameAndDescription breakLaserBladeAdvancement;
    // Data Packs
    public NameAndDescription packRepulsiveForce;
    // Resource Packs
    public NameAndDescription packSampleSound;

    public LanguageProviderBase(PackOutput output, String locale) {
        super(output, Constants.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // Creative mode tab title
        add(Constants.CreativeModeTabs.TITLE_MOD_MAIN, mainItemGroup);

        // Block names
        add(ModBlocks.LB_BLUEPRINT, laserBladeBlueprint);

        // Item names
        add(ModItems.LASER_BLADE, laserBlade);
        add(ModItems.LASER_BLADE_FP, laserBladeFp);
        add(ModItems.LB_CASING, laserBladeCasing);
        add(ModItems.LB_CASING_FP, laserBladeCasingFp);
        add(ModItems.LB_BATTERY, lbEnergyCell);
        add(ModItems.LB_MEDIUM, laserMedium);
        add(ModItems.LB_EMITTER, laserBladeEmitter);

        // Item tooltips
        add("tooltip.tolaserblade.fire_resistant", tooltipFireResistant);
        add("tooltip.tolaserblade.model", tooltipModel);

        // Tags
        add(ModTags.Items.LASER_BLADES, tagLaserBlades);
        add(ModTags.Items.LASER_BLADE_ENCHANTABLE, tagLaserBladeEnchantable);
        add(ModTags.Items.BLUEPRINT_CAN_CHANGE_COLOR, tagBlueprintCanChangeColor);
        add(ModTags.Items.LASER_BLADE_UPGRADE, tagLaserBladeUpgrade);
        add(ModTags.Items.LIGHT_ELEMENT_UPGRADE, tagLightElementUpgrade);
        add(ModTags.Items.REPULSIVE_FORCE_UPGRADE, tagRepulsiveForceUpgrade);
        add(ModTags.Items.LOOTING_UPGRADE, tagLootingUpgrade);
        add(ModTags.EntityTypes.SENSITIVE_TO_LASER_BLADE, tagSensitiveToLaserBlade);

        // Enchantments
        add(Constants.Enchantments.LASER_BLADE, laserBladeEnchantment);
        add(Constants.Enchantments.LIGHT_ELEMENT, lightElementEnchantment);
        add(Constants.Enchantments.REPULSIVE_FORCE, repulsiveForceEnchantment);

        // Menus
        add(Constants.Menus.BLUEPRINT_TITLE, menuBlueprintTitle);

        // Advancements
        add("main", "root", mainRootAdvancement);
        add("main", "laser_blade", laserBladeAdvancement);
        add("main", "laser_blade_laser_blade_2", laserBlade2Advancement);
        add("main", "laser_blade_laser_blade_5", laserBlade5Advancement);
        add("main", "laser_blade_light_element_2", lightElement2Advancement);
        add("main", "laser_blade_light_element_5", lightElement5Advancement);
        add("main", "laser_blade_looting_3", looting3Advancement);
        add("main", "laser_blade_fp", laserBladeFPAdvancement);
        add("main", "laser_blade_mending_1", mendingAdvancement);
        add("main", "break_laser_blade", breakLaserBladeAdvancement);

        // Data Packs
        add(Constants.DataPacks.REPULSIVE_FORCE, packRepulsiveForce);

        // Resource Packs
        add(Constants.DataPacks.SAMPLE_SOUND_PACK, packSampleSound);
    }

    @Override
    public void add(Block key, String name) {
        if (name != null) super.add(key, name);
    }

    @Override
    public void add(Item key, String name) {
        if (name != null) super.add(key, name);
    }

    @Override
    public void add(String key, String value) {
        if (value != null) super.add(key, value);
    }

    @Override
    public void add(TagKey<?> tagKey, String name) {
        if (name != null) super.add(tagKey, name);
    }

    public void add(ResourceKey<Enchantment> enchantment, NameAndDescription translation) {
        if (translation != null) {
            // Register enchantment name
            String id = Util.makeDescriptionId("enchantment", enchantment.identifier());
            add(id, translation.name());
            // Support for Enchantment Descriptions mod
            add(id + ".desc", translation.description());
        }
    }

    public void add(String tab, String name, NameAndDescription translation) {
        if (translation != null) {
            add("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name), translation.title());
            add("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name), translation.description());
        }
    }

    public void add(Constants.DataPacks pack, NameAndDescription translation) {
        if (translation != null) {
            add(pack.nameKey(), translation.title());
            add(pack.descriptionKey(), translation.description());
        }
    }

    public record NameAndDescription(String name, String description) {

        public String title() {
            return name;
        }
    }
}
