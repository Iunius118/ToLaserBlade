package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeManager;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Map;
import java.util.function.Consumer;

public class TLBRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public TLBRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // DX Laser Blade
        ShapedRecipeBuilder.shapedRecipe(ModItems.DX_LASER_BLADE)
                .patternLine("R")
                .patternLine("R")
                .patternLine("s")
                .key('R', Items.REDSTONE_TORCH)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.DX_LASER_BLADE.getRegistryName());

        // Laser Blade with Light Element I
        ShapedRecipeBuilder.shapedRecipe(ModItems.LB_BRAND_NEW_1)
                .patternLine("Gid")
                .patternLine("idi")
                .patternLine("riG")
                .key('G', Tags.Items.GLASS_COLORLESS)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.LB_BRAND_NEW_1.getRegistryName());

        // Laser Blade with Light Element II
        ShapedRecipeBuilder.shapedRecipe(ModItems.LB_BRAND_NEW_2)
                .patternLine("gid")
                .patternLine("idi")
                .patternLine("rig")
                .key('g', Tags.Items.DUSTS_GLOWSTONE)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.LB_BRAND_NEW_2.getRegistryName());

        // Netherite Laser Blade by using Smithing Table
        addSmithingRecipe(Ingredient.fromItems(ModItems.LASER_BLADE), Ingredient.fromItems(Items.NETHERITE_INGOT), ModItems.LASER_BLADE_FP, Items.NETHERITE_INGOT, consumer);

        // Upgrade Recipes
        addUpgradeRecipes(consumer);
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }

    private void addSmithingRecipe(Ingredient base, Ingredient addition, Item result, Item criterionItem, Consumer<IFinishedRecipe> consumer) {
        // TODO: func_240502_a_ = smithingRecipe, func_240503_a_ = addCriterion, func_240504_a_ = build
        SmithingRecipeBuilder.func_240502_a_(base, addition, result)
                .func_240503_a_("has_" + criterionItem.getRegistryName().getPath(), hasItem(criterionItem))
                .func_240504_a_(consumer, result.getRegistryName().toString() + "_smithing");
    }

    private void addUpgradeRecipes(Consumer<IFinishedRecipe> consumer) {
        Map<ResourceLocation, Upgrade> upgrades = UpgradeManager.getUpgrades();
        upgrades.forEach((id, upgrade) -> {
            UpgradeRecipeBuilder.upgradeRecipe(Ingredient.fromItems(ModItems.LASER_BLADE), upgrade.getIngredient(), id)
                    .addCriterion("has_laser_blade", hasItem(ModItems.LASER_BLADE))
                    .build(consumer, id.toString() + "_upgrade");
            UpgradeRecipeBuilder.upgradeRecipe(Ingredient.fromItems(ModItems.LASER_BLADE_FP), upgrade.getIngredient(), id)
                    .addCriterion("has_laser_blade_fp", hasItem(ModItems.LASER_BLADE_FP))
                    .build(consumer, id.toString() +  "_fp_upgrade");
        });
    }
}
