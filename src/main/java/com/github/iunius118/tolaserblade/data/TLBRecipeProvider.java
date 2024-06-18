package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.laserblade.Color;
import com.github.iunius118.tolaserblade.laserblade.ColorPart;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.block.Blocks;
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
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        // DX Laser Blade
        ShapedRecipeBuilder.shaped(ModItems.DX_LASER_BLADE)
                .pattern("R")
                .pattern("R")
                .pattern("s")
                .define('R', Items.REDSTONE_TORCH)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, ModItems.DX_LASER_BLADE.getRegistryName());

        // Laser Blade with Light Element I
        ShapedRecipeBuilder.shaped(ModItems.LB_BRAND_NEW_1)
                .pattern("Gid")
                .pattern("idi")
                .pattern("riG")
                .define('G', Tags.Items.GLASS_COLORLESS)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, ModItems.LB_BRAND_NEW_1.getRegistryName());

        // Laser Blade with Light Element II
        ShapedRecipeBuilder.shaped(ModItems.LB_BRAND_NEW_2)
                .pattern("gid")
                .pattern("idi")
                .pattern("rig")
                .define('g', Tags.Items.DUSTS_GLOWSTONE)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, ModItems.LB_BRAND_NEW_2.getRegistryName());

        // Netherite Laser Blade by using Smithing Table
        addSmithingRecipe(Ingredient.of(ModItems.LASER_BLADE), Ingredient.of(Items.NETHERITE_INGOT), ModItems.LB_BRAND_NEW_FP, Items.NETHERITE_INGOT, consumer);

        // Repair recipes
        addSmithingRepairRecipe("lb", ModItems.LASER_BLADE, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW, ModItems.LASER_BLADE, consumer);
        addSmithingRepairRecipe("lbb", ModItems.LB_BROKEN, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW, ModItems.LB_BROKEN, consumer);
        addSmithingRepairRecipe("lbf", ModItems.LASER_BLADE_FP, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW_FP, ModItems.LASER_BLADE_FP, consumer);
        addSmithingRepairRecipe("lbbf", ModItems.LB_BROKEN_FP, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW_FP, ModItems.LB_BROKEN_FP, consumer);

        // Upgrade Recipes
        addUpgradeRecipes(consumer);

        // Color Recipes
        addColorRecipes(consumer);

        // Model Change Recipes
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.GLASS.asItem()), 0, "");
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.SAND.asItem()), 1, "");
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.SANDSTONE.asItem()), 2, "");
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.DIRT.asItem()), 526, "");
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.CRAFTING_TABLE.asItem()), -1, "");
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }

    private void addSmithingRecipe(Ingredient base, Ingredient addition, Item result, Item criterionItem, Consumer<IFinishedRecipe> consumer) {
        SmithingRecipeBuilder.smithing(base, addition, result)
                .unlocks("has_" + criterionItem.getRegistryName().getPath(), has(criterionItem))
                .save(consumer, result.getRegistryName().toString() + "_smithing");
    }

    private void addSmithingRepairRecipe(String shortName, Item base, Ingredient addition, Item result, Item criterionItem, Consumer<IFinishedRecipe> consumer) {
        SmithingRecipeBuilder.smithing(Ingredient.of(base), addition, result)
                .unlocks("has_" + criterionItem.getRegistryName().getPath(), has(criterionItem))
                .save(consumer, ToLaserBlade.MOD_ID + ":repair_" + shortName + "_smithing");
    }

    private void addUpgradeRecipes(Consumer<IFinishedRecipe> consumer) {
        Map<ResourceLocation, Upgrade> upgrades = UpgradeManager.getUpgrades();
        upgrades.forEach((id, upgrade) -> {
            UpgradeRecipeBuilder.upgradeRecipe(Ingredient.of(ModItems.LASER_BLADE), upgrade.getIngredient(), id)
                    .addCriterion("has_laser_blade", has(ModItems.LASER_BLADE))
                    .build(consumer, ToLaserBlade.MOD_ID + ":upgrade/lb_" + upgrade.getShortName());
            UpgradeRecipeBuilder.upgradeRecipe(Ingredient.of(ModItems.LASER_BLADE_FP), upgrade.getIngredient(), id)
                    .addCriterion("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                    .build(consumer, ToLaserBlade.MOD_ID + ":upgrade/lbf_" + upgrade.getShortName());
        });
    }

    private void addColorRecipes(Consumer<IFinishedRecipe> consumer) {
        addInnerColorRecipes(consumer);
        addOuterColorRecipes(consumer);
        addGripColorRecipes(consumer);
    }

    private void addInnerColorRecipes(Consumer<IFinishedRecipe> consumer) {
        ColorPart part = ColorPart.INNER_BLADE;
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_WHITE), part, Color.WHITE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_ORANGE), part, Color.ORANGE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_MAGENTA), part, Color.MAGENTA);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIGHT_BLUE), part, Color.LIGHT_BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_YELLOW), part, Color.YELLOW);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIME), part, Color.LIME);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_PINK), part, Color.PINK);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_GRAY), part, Color.GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIGHT_GRAY), part, Color.LIGHT_GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_CYAN), part, Color.CYAN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_PURPLE), part, Color.PURPLE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_BLUE), part, Color.BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_BROWN), part, Color.BROWN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_GREEN), part, Color.GREEN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_RED), part, Color.RED);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_BLACK), part, Color.BLACK);
    }

    private void addOuterColorRecipes(Consumer<IFinishedRecipe> consumer) {
        ColorPart part = ColorPart.OUTER_BLADE;
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_WHITE), part, Color.WHITE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_ORANGE), part, Color.ORANGE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_MAGENTA), part, Color.MAGENTA);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_LIGHT_BLUE), part, Color.LIGHT_BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_YELLOW), part, Color.YELLOW);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_LIME), part, Color.LIME);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PINK), part, Color.PINK);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_GRAY), part, Color.GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_LIGHT_GRAY), part, Color.LIGHT_GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_CYAN), part, Color.CYAN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PURPLE), part, Color.PURPLE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_BLUE), part, Color.BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_BROWN), part, Color.BROWN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_GREEN), part, Color.GREEN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_RED), part, Color.RED);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_BLACK), part, Color.BLACK);
    }

    private void addGripColorRecipes(Consumer<IFinishedRecipe> consumer) {
        ColorPart part = ColorPart.GRIP;
        addColorRecipe(consumer, Ingredient.of(Blocks.WHITE_CARPET), part, Color.WHITE);
        addColorRecipe(consumer, Ingredient.of(Blocks.ORANGE_CARPET), part, Color.ORANGE);
        addColorRecipe(consumer, Ingredient.of(Blocks.MAGENTA_CARPET), part, Color.MAGENTA);
        addColorRecipe(consumer, Ingredient.of(Blocks.LIGHT_BLUE_CARPET), part, Color.LIGHT_BLUE);
        addColorRecipe(consumer, Ingredient.of(Blocks.YELLOW_CARPET), part, Color.YELLOW);
        addColorRecipe(consumer, Ingredient.of(Blocks.LIME_CARPET), part, Color.LIME);
        addColorRecipe(consumer, Ingredient.of(Blocks.PINK_CARPET), part, Color.PINK);
        addColorRecipe(consumer, Ingredient.of(Blocks.GRAY_CARPET), part, Color.GRAY);
        addColorRecipe(consumer, Ingredient.of(Blocks.LIGHT_GRAY_CARPET), part, Color.LIGHT_GRAY);
        addColorRecipe(consumer, Ingredient.of(Blocks.CYAN_CARPET), part, Color.CYAN);
        addColorRecipe(consumer, Ingredient.of(Blocks.PURPLE_CARPET), part, Color.PURPLE);
        addColorRecipe(consumer, Ingredient.of(Blocks.BLUE_CARPET), part, Color.BLUE);
        addColorRecipe(consumer, Ingredient.of(Blocks.BROWN_CARPET), part, Color.BROWN);
        addColorRecipe(consumer, Ingredient.of(Blocks.GREEN_CARPET), part, Color.GREEN);
        addColorRecipe(consumer, Ingredient.of(Blocks.RED_CARPET), part, Color.RED);
        addColorRecipe(consumer, Ingredient.of(Blocks.BLACK_CARPET), part, Color.BLACK);
    }

    private void addColorRecipe(Consumer<IFinishedRecipe> consumer, Ingredient addition, ColorPart part, Color color) {
        boolean isBlade = (part == ColorPart.INNER_BLADE || part == ColorPart.OUTER_BLADE);
        int colorValue = isBlade ? color.getBladeColor() : color.getGripColor();

        ColorRecipeBuilder.colorRecipe(Ingredient.of(ModItems.LASER_BLADE), addition, part, colorValue)
                .addCriterion("has_laser_blade", has(ModItems.LASER_BLADE))
                .build(consumer, "tolaserblade:color/lb_" + part.getShortName() + "_" + color.getColorName());
        ColorRecipeBuilder.colorRecipe(Ingredient.of(ModItems.LASER_BLADE_FP), addition, part, colorValue)
                .addCriterion("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .build(consumer, ToLaserBlade.MOD_ID + ":color/lbf_" + part.getShortName() + "_" + color.getColorName());
    }

    private void addModelChangeRecipes(Consumer<IFinishedRecipe> consumer, Ingredient addition, int modelType, String suffixIn) {
        String suffix = (suffixIn == null) ? "" : suffixIn;

        ModelChangeRecipeBuilder.modelChangeRecipe(Ingredient.of(ModItems.LASER_BLADE), addition, modelType)
                .addCriterion("has_laser_blade", has(ModItems.LASER_BLADE))
                .build(consumer, "tolaserblade:model/lb_" + modelType + suffix);
        ModelChangeRecipeBuilder.modelChangeRecipe(Ingredient.of(ModItems.LASER_BLADE_FP), addition, modelType)
                .addCriterion("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .build(consumer, "tolaserblade:model/lbf_" + modelType + suffix);
    }
}
