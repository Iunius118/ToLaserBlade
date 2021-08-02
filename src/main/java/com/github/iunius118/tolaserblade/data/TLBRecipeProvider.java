package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Map;
import java.util.function.Consumer;

public class TLBRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public TLBRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
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
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.DIRT.asItem()), 526, "_sample");
        addModelChangeRecipes(consumer, Ingredient.of(Blocks.CRAFTING_TABLE.asItem()), LaserBladeVisual.MODEL_TYPE_NO_MODEL, "");
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }

    private void addSmithingRecipe(Ingredient base, Ingredient addition, Item result, Item criterionItem, Consumer<FinishedRecipe> consumer) {
        UpgradeRecipeBuilder.smithing(base, addition, result)
                .unlocks("has_" + criterionItem.getRegistryName().getPath(), has(criterionItem))
                .save(consumer, result.getRegistryName().toString() + "_smithing");
    }

    private void addSmithingRepairRecipe(String shortName, Item base, Ingredient addition, Item result, Item criterionItem, Consumer<FinishedRecipe> consumer) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), addition, result)
                .unlocks("has_" + criterionItem.getRegistryName().getPath(), has(criterionItem))
                .save(consumer, ToLaserBlade.MOD_ID + ":repair_" + shortName + "_smithing");
    }

    private void addUpgradeRecipes(Consumer<FinishedRecipe> consumer) {
        Map<ResourceLocation, Upgrade> upgrades = UpgradeManager.getUpgrades();
        upgrades.forEach((id, upgrade) -> {
            LBUpgradeRecipeBuilder.upgradeRecipe(Ingredient.of(ModItems.LASER_BLADE), upgrade.getIngredient(), id)
                    .addCriterion("has_laser_blade", has(ModItems.LASER_BLADE))
                    .build(consumer, ToLaserBlade.MOD_ID + ":upgrade/lb_" + upgrade.getShortName());
            LBUpgradeRecipeBuilder.upgradeRecipe(Ingredient.of(ModItems.LASER_BLADE_FP), upgrade.getIngredient(), id)
                    .addCriterion("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                    .build(consumer, ToLaserBlade.MOD_ID + ":upgrade/lbf_" + upgrade.getShortName());
        });
    }

    private void addColorRecipes(Consumer<FinishedRecipe> consumer) {
        addInnerColorRecipes(consumer);
        addOuterColorRecipes(consumer);
        addGripColorRecipes(consumer);
    }

    private void addInnerColorRecipes(Consumer<FinishedRecipe> consumer) {
        LaserBladeColorPart part = LaserBladeColorPart.INNER_BLADE;
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_WHITE), part, LaserBladeColor.WHITE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_ORANGE), part, LaserBladeColor.ORANGE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_MAGENTA), part, LaserBladeColor.MAGENTA);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIGHT_BLUE), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_YELLOW), part, LaserBladeColor.YELLOW);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIME), part, LaserBladeColor.LIME);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_PINK), part, LaserBladeColor.PINK);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_GRAY), part, LaserBladeColor.GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIGHT_GRAY), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_CYAN), part, LaserBladeColor.CYAN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_PURPLE), part, LaserBladeColor.PURPLE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_BLUE), part, LaserBladeColor.BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_BROWN), part, LaserBladeColor.BROWN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_GREEN), part, LaserBladeColor.GREEN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_RED), part, LaserBladeColor.RED);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PANES_BLACK), part, LaserBladeColor.BLACK);

        addColorRecipe(consumer, Ingredient.of(Blocks.AMETHYST_BLOCK), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addOuterColorRecipes(Consumer<FinishedRecipe> consumer) {
        LaserBladeColorPart part = LaserBladeColorPart.OUTER_BLADE;
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_WHITE), part, LaserBladeColor.WHITE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_ORANGE), part, LaserBladeColor.ORANGE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_MAGENTA), part, LaserBladeColor.MAGENTA);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_LIGHT_BLUE), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_YELLOW), part, LaserBladeColor.YELLOW);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_LIME), part, LaserBladeColor.LIME);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PINK), part, LaserBladeColor.PINK);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_GRAY), part, LaserBladeColor.GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_LIGHT_GRAY), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_CYAN), part, LaserBladeColor.CYAN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_PURPLE), part, LaserBladeColor.PURPLE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_BLUE), part, LaserBladeColor.BLUE);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_BROWN), part, LaserBladeColor.BROWN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_GREEN), part, LaserBladeColor.GREEN);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_RED), part, LaserBladeColor.RED);
        addColorRecipe(consumer, Ingredient.of(Tags.Items.GLASS_BLACK), part, LaserBladeColor.BLACK);

        addColorRecipe(consumer, Ingredient.of(Blocks.TINTED_GLASS), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addGripColorRecipes(Consumer<FinishedRecipe> consumer) {
        LaserBladeColorPart part = LaserBladeColorPart.GRIP;
        addColorRecipe(consumer, Ingredient.of(Blocks.WHITE_CARPET), part, LaserBladeColor.WHITE);
        addColorRecipe(consumer, Ingredient.of(Blocks.ORANGE_CARPET), part, LaserBladeColor.ORANGE);
        addColorRecipe(consumer, Ingredient.of(Blocks.MAGENTA_CARPET), part, LaserBladeColor.MAGENTA);
        addColorRecipe(consumer, Ingredient.of(Blocks.LIGHT_BLUE_CARPET), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(consumer, Ingredient.of(Blocks.YELLOW_CARPET), part, LaserBladeColor.YELLOW);
        addColorRecipe(consumer, Ingredient.of(Blocks.LIME_CARPET), part, LaserBladeColor.LIME);
        addColorRecipe(consumer, Ingredient.of(Blocks.PINK_CARPET), part, LaserBladeColor.PINK);
        addColorRecipe(consumer, Ingredient.of(Blocks.GRAY_CARPET), part, LaserBladeColor.GRAY);
        addColorRecipe(consumer, Ingredient.of(Blocks.LIGHT_GRAY_CARPET), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(consumer, Ingredient.of(Blocks.CYAN_CARPET), part, LaserBladeColor.CYAN);
        addColorRecipe(consumer, Ingredient.of(Blocks.PURPLE_CARPET), part, LaserBladeColor.PURPLE);
        addColorRecipe(consumer, Ingredient.of(Blocks.BLUE_CARPET), part, LaserBladeColor.BLUE);
        addColorRecipe(consumer, Ingredient.of(Blocks.BROWN_CARPET), part, LaserBladeColor.BROWN);
        addColorRecipe(consumer, Ingredient.of(Blocks.GREEN_CARPET), part, LaserBladeColor.GREEN);
        addColorRecipe(consumer, Ingredient.of(Blocks.RED_CARPET), part, LaserBladeColor.RED);
        addColorRecipe(consumer, Ingredient.of(Blocks.BLACK_CARPET), part, LaserBladeColor.BLACK);
    }

    private void addColorRecipe(Consumer<FinishedRecipe> consumer, Ingredient addition, LaserBladeColorPart part, LaserBladeColor color) {
        boolean isBlade = (part == LaserBladeColorPart.INNER_BLADE || part == LaserBladeColorPart.OUTER_BLADE);
        int colorValue = isBlade ? color.getBladeColor() : color.getGripColor();

        LBColorRecipeBuilder.colorRecipe(Ingredient.of(ModItems.LASER_BLADE), addition, part, colorValue)
                .addCriterion("has_laser_blade", has(ModItems.LASER_BLADE))
                .build(consumer, "tolaserblade:color/lb_" + part.getShortName() + "_" + color.getColorName());
        LBColorRecipeBuilder.colorRecipe(Ingredient.of(ModItems.LASER_BLADE_FP), addition, part, colorValue)
                .addCriterion("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .build(consumer, ToLaserBlade.MOD_ID + ":color/lbf_" + part.getShortName() + "_" + color.getColorName());
    }

    private void addModelChangeRecipes(Consumer<FinishedRecipe> consumer, Ingredient addition, int modelType, String suffixIn) {
        String suffix = (suffixIn == null) ? "" : suffixIn;

        LBModelChangeRecipeBuilder.modelChangeRecipe(Ingredient.of(ModItems.LASER_BLADE), addition, modelType)
                .addCriterion("has_laser_blade", has(ModItems.LASER_BLADE))
                .build(consumer, "tolaserblade:model/lb_" + modelType + suffix);
        LBModelChangeRecipeBuilder.modelChangeRecipe(Ingredient.of(ModItems.LASER_BLADE_FP), addition, modelType)
                .addCriterion("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .build(consumer, "tolaserblade:model/lbf_" + modelType + suffix);
    }
}
