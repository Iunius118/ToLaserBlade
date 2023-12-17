package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TLBRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public TLBRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        buildCraftingRecipes(consumer);
        buildSmithingRecipes(consumer);
    }

    public void buildCraftingRecipes(RecipeOutput consumer) {
        // DX Laser Blade
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DX_LASER_BLADE)
                .pattern("R")
                .pattern("R")
                .pattern("s")
                .define('R', Items.REDSTONE_TORCH)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, getItemId(ModItems.DX_LASER_BLADE));

        // Laser Blade Blueprint
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LB_BLUEPRINT)
                .requires(ModItems.DX_LASER_BLADE)
                .requires(Items.PAPER)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, getItemId(ModItems.LB_BLUEPRINT));

        // LB Energy Cell
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LB_BATTERY)
                .pattern("#")
                .pattern("i")
                .pattern("r")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(consumer, getItemId(ModItems.LB_BATTERY));

        // Laser Medium
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LB_MEDIUM)
                .pattern(" # ")
                .pattern("gdg")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('g', Tags.Items.DUSTS_GLOWSTONE)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(consumer, getItemId(ModItems.LB_MEDIUM));

        // Laser Blade Emitter
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LB_EMITTER)
                .pattern("#")
                .pattern("d")
                .pattern("i")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(consumer, getItemId(ModItems.LB_EMITTER));

        // Laser Blade Casing
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LB_CASING)
                .pattern("#")
                .pattern("i")
                .pattern("i")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(consumer, getItemId(ModItems.LB_CASING));

        // Brand-new Laser Blade I
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LB_BRAND_NEW_1)
                .requires(ModItems.LB_BLUEPRINT)
                .requires(ModItems.LB_BATTERY)
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(Tags.Items.GLASS_COLORLESS)
                .requires(ModItems.LB_EMITTER)
                .requires(ModItems.LB_CASING)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(consumer, getItemId(ModItems.LB_BRAND_NEW_1));

        // Brand-new Laser Blade II
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LB_BRAND_NEW_2)
                .requires(ModItems.LB_BLUEPRINT)
                .requires(ModItems.LB_BATTERY)
                .requires(ModItems.LB_MEDIUM)
                .requires(ModItems.LB_EMITTER)
                .requires(ModItems.LB_CASING)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(consumer, getItemId(ModItems.LB_BRAND_NEW_2));
    }

    public void buildSmithingRecipes(RecipeOutput consumer) {
        Ingredient template = Ingredient.of(ModItems.LB_BLUEPRINT);

        // Netherite Laser Blade by using Smithing Table
        addSmithingTransformRecipe(template, Ingredient.of(ModItems.LASER_BLADE), Ingredient.of(Items.NETHERITE_INGOT), ModItems.LB_BRAND_NEW_FP, Items.NETHERITE_INGOT, consumer);

        // Repair recipes
        addSmithingRepairRecipe("lb", template, ModItems.LASER_BLADE, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW, ModItems.LASER_BLADE, consumer);
        addSmithingRepairRecipe("lbb", template, ModItems.LB_BROKEN, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW, ModItems.LB_BROKEN, consumer);
        addSmithingRepairRecipe("lbf", template, ModItems.LASER_BLADE_FP, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW_FP, ModItems.LASER_BLADE_FP, consumer);
        addSmithingRepairRecipe("lbbf", template, ModItems.LB_BROKEN_FP, Ingredient.of(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW_FP, ModItems.LB_BROKEN_FP, consumer);

        // Upgrade Recipes
        addUpgradeRecipes(template, consumer);

        // Color Recipes
        addColorRecipes(template, consumer);

        // Model Change Recipes
        addModelChangeRecipes(template, consumer, Ingredient.of(Blocks.GLASS.asItem()), 0, "");
        addModelChangeRecipes(template, consumer, Ingredient.of(Blocks.SAND.asItem()), 1, "");
        addModelChangeRecipes(template, consumer, Ingredient.of(Blocks.SANDSTONE.asItem()), 2, "");
        addModelChangeRecipes(template, consumer, Ingredient.of(Blocks.DIRT.asItem()), 526, "");
    }

    private ResourceLocation getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    private void addSmithingTransformRecipe(Ingredient template, Ingredient base, Ingredient addition, Item result, Item criterionItem, RecipeOutput consumer) {
        SmithingTransformRecipeBuilder.smithing(template, base, addition, RecipeCategory.MISC, result)
                .unlocks("has_" + getItemId(criterionItem).getPath(), has(criterionItem))
                .save(consumer, getItemId(result).toString() + "_smithing");
    }

    private void addSmithingRepairRecipe(String shortName, Ingredient template, Item base, Ingredient addition, Item result, Item criterionItem, RecipeOutput consumer) {
        SmithingTransformRecipeBuilder.smithing(template, Ingredient.of(base), addition, RecipeCategory.MISC, result)
                .unlocks("has_" + getItemId(criterionItem).getPath(), has(criterionItem))
                .save(consumer, ToLaserBlade.MOD_ID + ":repair_" + shortName + "_smithing");
    }

    private void addUpgradeRecipes(Ingredient template, RecipeOutput consumer) {
        Map<ResourceLocation, Upgrade> upgrades = UpgradeManager.getUpgrades();
        upgrades.forEach((id, upgrade) -> {
            LBUpgradeRecipeBuilder.upgradeRecipe(template, Ingredient.of(ModItems.LASER_BLADE), upgrade.getIngredient(), id)
                    .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                    .save(consumer, ToLaserBlade.MOD_ID + ":upgrade/lb_" + upgrade.shortName());
            LBUpgradeRecipeBuilder.upgradeRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), upgrade.getIngredient(), id)
                    .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                    .save(consumer, ToLaserBlade.MOD_ID + ":upgrade/lbf_" + upgrade.shortName());
        });
    }

    private void addColorRecipes(Ingredient template, RecipeOutput consumer) {
        addInnerColorRecipes(template, consumer);
        addOuterColorRecipes(template, consumer);
        addGripColorRecipes(template, consumer);
    }

    private void addInnerColorRecipes(Ingredient template, RecipeOutput consumer) {
        LaserBladeColorPart part = LaserBladeColorPart.INNER_BLADE;
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_WHITE), part, LaserBladeColor.WHITE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_ORANGE), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_MAGENTA), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIGHT_BLUE), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_YELLOW), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIME), part, LaserBladeColor.LIME);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_PINK), part, LaserBladeColor.PINK);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_GRAY), part, LaserBladeColor.GRAY);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_LIGHT_GRAY), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_CYAN), part, LaserBladeColor.CYAN);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_PURPLE), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_BLUE), part, LaserBladeColor.BLUE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_BROWN), part, LaserBladeColor.BROWN);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_GREEN), part, LaserBladeColor.GREEN);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_RED), part, LaserBladeColor.RED);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PANES_BLACK), part, LaserBladeColor.BLACK);

        addColorRecipe(template, consumer, Ingredient.of(Blocks.AMETHYST_BLOCK), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addOuterColorRecipes(Ingredient template, RecipeOutput consumer) {
        LaserBladeColorPart part = LaserBladeColorPart.OUTER_BLADE;
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_WHITE), part, LaserBladeColor.WHITE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_ORANGE), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_MAGENTA), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_LIGHT_BLUE), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_YELLOW), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_LIME), part, LaserBladeColor.LIME);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PINK), part, LaserBladeColor.PINK);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_GRAY), part, LaserBladeColor.GRAY);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_LIGHT_GRAY), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_CYAN), part, LaserBladeColor.CYAN);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_PURPLE), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_BLUE), part, LaserBladeColor.BLUE);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_BROWN), part, LaserBladeColor.BROWN);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_GREEN), part, LaserBladeColor.GREEN);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_RED), part, LaserBladeColor.RED);
        addColorRecipe(template, consumer, Ingredient.of(Tags.Items.GLASS_BLACK), part, LaserBladeColor.BLACK);

        addColorRecipe(template, consumer, Ingredient.of(Blocks.TINTED_GLASS), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addGripColorRecipes(Ingredient template, RecipeOutput consumer) {
        LaserBladeColorPart part = LaserBladeColorPart.GRIP;
        addColorRecipe(template, consumer, Ingredient.of(Blocks.WHITE_CARPET), part, LaserBladeColor.WHITE);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.ORANGE_CARPET), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.MAGENTA_CARPET), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.LIGHT_BLUE_CARPET), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.YELLOW_CARPET), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.LIME_CARPET), part, LaserBladeColor.LIME);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.PINK_CARPET), part, LaserBladeColor.PINK);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.GRAY_CARPET), part, LaserBladeColor.GRAY);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.LIGHT_GRAY_CARPET), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.CYAN_CARPET), part, LaserBladeColor.CYAN);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.PURPLE_CARPET), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.BLUE_CARPET), part, LaserBladeColor.BLUE);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.BROWN_CARPET), part, LaserBladeColor.BROWN);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.GREEN_CARPET), part, LaserBladeColor.GREEN);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.RED_CARPET), part, LaserBladeColor.RED);
        addColorRecipe(template, consumer, Ingredient.of(Blocks.BLACK_CARPET), part, LaserBladeColor.BLACK);
    }

    private void addColorRecipe(Ingredient template, RecipeOutput consumer, Ingredient addition, LaserBladeColorPart part, LaserBladeColor color) {
        boolean isBlade = (part == LaserBladeColorPart.INNER_BLADE || part == LaserBladeColorPart.OUTER_BLADE);
        int colorValue = isBlade ? color.getBladeColor() : color.getGripColor();

        LBColorRecipeBuilder.colorRecipe(template, Ingredient.of(ModItems.LASER_BLADE), addition, part, colorValue)
                .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                .save(consumer, "tolaserblade:color/lb_" + part.getShortName() + "_" + color.getColorName());
        LBColorRecipeBuilder.colorRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), addition, part, colorValue)
                .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .save(consumer, ToLaserBlade.MOD_ID + ":color/lbf_" + part.getShortName() + "_" + color.getColorName());
    }

    private void addModelChangeRecipes(Ingredient template, RecipeOutput consumer, Ingredient addition, int modelType, String suffixIn) {
        String suffix = (suffixIn == null) ? "" : suffixIn;

        LBModelChangeRecipeBuilder.modelChangeRecipe(template, Ingredient.of(ModItems.LASER_BLADE), addition, modelType)
                .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                .save(consumer, "tolaserblade:model/lb_" + modelType + suffix);
        LBModelChangeRecipeBuilder.modelChangeRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), addition, modelType)
                .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .save(consumer, "tolaserblade:model/lbf_" + modelType + suffix);
    }
}
