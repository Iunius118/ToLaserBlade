package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeID;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.data.recipes.LBColorRecipeBuilder;
import com.github.iunius118.tolaserblade.data.recipes.LBModelChangeRecipeBuilder;
import com.github.iunius118.tolaserblade.data.recipes.LBUpgradeRecipeBuilder;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TLBRecipeProvider extends VanillaRecipeProvider {
    public TLBRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
        super(registryLookup, output);
    }

    @Override
    public void buildRecipes() {
        buildCraftingRecipes();
        buildSmithingRecipes();
    }

    public void buildCraftingRecipes() {
        final HolderLookup.RegistryLookup<Item> holderGetter = registries.lookupOrThrow(Registries.ITEM);

        // DX Laser Blade
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.COMBAT, ModItems.DX_LASER_BLADE)
                .pattern("R")
                .pattern("R")
                .pattern("s")
                .define('R', Items.REDSTONE_TORCH)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(output);
        // Laser Blade Blueprint
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.MISC, ModItems.LB_BLUEPRINT)
                .requires(ModItems.DX_LASER_BLADE)
                .requires(Items.PAPER)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(output);

        // LB Energy Cell
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_BATTERY)
                .pattern("#")
                .pattern("i")
                .pattern("r")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Laser Medium
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_MEDIUM)
                .pattern(" # ")
                .pattern("gdg")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('g', Tags.Items.DUSTS_GLOWSTONE)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Laser Blade Emitter
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_EMITTER)
                .pattern("#")
                .pattern("d")
                .pattern("i")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Laser Blade Casing
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_CASING)
                .pattern("#")
                .pattern("i")
                .pattern("i")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Brand-new Laser Blade I
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.TOOLS, ModItems.LB_BRAND_NEW_1)
                .requires(ModItems.LB_BLUEPRINT)
                .requires(ModItems.LB_BATTERY)
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(Tags.Items.GLASS_BLOCKS_COLORLESS)
                .requires(ModItems.LB_EMITTER)
                .requires(ModItems.LB_CASING)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Brand-new Laser Blade II
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.TOOLS, ModItems.LB_BRAND_NEW_2)
                .requires(ModItems.LB_BLUEPRINT)
                .requires(ModItems.LB_BATTERY)
                .requires(ModItems.LB_MEDIUM)
                .requires(ModItems.LB_EMITTER)
                .requires(ModItems.LB_CASING)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);
    }

    public void buildSmithingRecipes() {
        Ingredient template = Ingredient.of(ModItems.LB_BLUEPRINT);

        // Repair recipes
        addSmithingRepairRecipe("lbb", template, ModItems.LB_BROKEN, getIngredient(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW, ModItems.LB_BROKEN);
        addSmithingRepairRecipe("lbbf", template, ModItems.LB_BROKEN_FP, getIngredient(ModItemTags.CASING_REPAIR), ModItems.LB_BRAND_NEW_FP, ModItems.LB_BROKEN_FP);

        // Upgrade Recipes
        addUpgradeRecipes(template);

        // Color Recipes
        addColorRecipes(template);

        // Model Change Recipes
        addModelChangeRecipes(template, Ingredient.of(Blocks.GLASS.asItem()), 0, "");
        addModelChangeRecipes(template, Ingredient.of(Blocks.SAND.asItem()), 1, "");
        addModelChangeRecipes(template, Ingredient.of(Blocks.SANDSTONE.asItem()), 2, "");
        addModelChangeRecipes(template, Ingredient.of(Blocks.DIRT.asItem()), 526, "");
    }

    private void addSmithingTransformRecipe(Ingredient template, Ingredient base, Ingredient addition, Item result, Item criterionItem) {
        SmithingTransformRecipeBuilder.smithing(template, base, addition, RecipeCategory.TOOLS, result)
                .unlocks("has_" + getItemId(criterionItem).getPath(), has(criterionItem))
                .save(output, getItemId(result).toString() + "_smithing");
    }

    private void addSmithingRepairRecipe(String shortName, Ingredient template, Item base, Ingredient addition, Item result, Item criterionItem) {
        SmithingTransformRecipeBuilder.smithing(template, Ingredient.of(base), addition, RecipeCategory.TOOLS, result)
                .unlocks("has_" + getItemId(criterionItem).getPath(), has(criterionItem))
                .save(output, ToLaserBlade.MOD_ID + ":repair_" + shortName + "_smithing");
    }

    private void addUpgradeRecipes(Ingredient template) {
        Map<ResourceLocation, Upgrade> upgrades = UpgradeManager.getUpgrades();
        upgrades.forEach((id, upgrade) -> {
            TagKey<Item> tag = upgrade.ingredientItemTag();
            var ingredient = Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag));

            LBUpgradeRecipeBuilder.upgradeRecipe(template, Ingredient.of(ModItems.LASER_BLADE), ingredient, RecipeCategory.TOOLS, id)
                    .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                    .save(output, ToLaserBlade.MOD_ID + ":upgrade/lb_" + upgrade.shortName());

            if (upgrade == UpgradeManager.getUpgrade(UpgradeID.FIREPROOF_UPGRADE)) {
                return; // Fireproof upgrade is only available for not fireproof laser blade
            }

            LBUpgradeRecipeBuilder.upgradeRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), ingredient, RecipeCategory.TOOLS, id)
                    .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                    .save(output, ToLaserBlade.MOD_ID + ":upgrade/lbf_" + upgrade.shortName());
        });
    }

    private void addColorRecipes(Ingredient template) {
        addInnerColorRecipes(template);
        addOuterColorRecipes(template);
        addGripColorRecipes(template);
    }

    private void addInnerColorRecipes(Ingredient template) {
        LaserBladeColorPart part = LaserBladeColorPart.INNER_BLADE;
        addColorRecipe(template, Ingredient.of(Items.WHITE_STAINED_GLASS_PANE), part, LaserBladeColor.WHITE);
        addColorRecipe(template, Ingredient.of(Items.ORANGE_STAINED_GLASS_PANE), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, Ingredient.of(Items.MAGENTA_STAINED_GLASS_PANE), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_BLUE_STAINED_GLASS_PANE), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, Ingredient.of(Items.YELLOW_STAINED_GLASS_PANE), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, Ingredient.of(Items.LIME_STAINED_GLASS_PANE), part, LaserBladeColor.LIME);
        addColorRecipe(template, Ingredient.of(Items.PINK_STAINED_GLASS_PANE), part, LaserBladeColor.PINK);
        addColorRecipe(template, Ingredient.of(Items.GRAY_STAINED_GLASS_PANE), part, LaserBladeColor.GRAY);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_GRAY_STAINED_GLASS_PANE), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, Ingredient.of(Items.CYAN_STAINED_GLASS_PANE), part, LaserBladeColor.CYAN);
        addColorRecipe(template, Ingredient.of(Items.PURPLE_STAINED_GLASS_PANE), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, Ingredient.of(Items.BLUE_STAINED_GLASS_PANE), part, LaserBladeColor.BLUE);
        addColorRecipe(template, Ingredient.of(Items.BROWN_STAINED_GLASS_PANE), part, LaserBladeColor.BROWN);
        addColorRecipe(template, Ingredient.of(Items.GREEN_STAINED_GLASS_PANE), part, LaserBladeColor.GREEN);
        addColorRecipe(template, Ingredient.of(Items.RED_STAINED_GLASS_PANE), part, LaserBladeColor.RED);
        addColorRecipe(template, Ingredient.of(Items.BLACK_STAINED_GLASS_PANE), part, LaserBladeColor.BLACK);

        addColorRecipe(template, Ingredient.of(Blocks.AMETHYST_BLOCK), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addOuterColorRecipes(Ingredient template) {
        LaserBladeColorPart part = LaserBladeColorPart.OUTER_BLADE;
        addColorRecipe(template, Ingredient.of(Items.WHITE_STAINED_GLASS), part, LaserBladeColor.WHITE);
        addColorRecipe(template, Ingredient.of(Items.ORANGE_STAINED_GLASS), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, Ingredient.of(Items.MAGENTA_STAINED_GLASS), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_BLUE_STAINED_GLASS), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, Ingredient.of(Items.YELLOW_STAINED_GLASS), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, Ingredient.of(Items.LIME_STAINED_GLASS), part, LaserBladeColor.LIME);
        addColorRecipe(template, Ingredient.of(Items.PINK_STAINED_GLASS), part, LaserBladeColor.PINK);
        addColorRecipe(template, Ingredient.of(Items.GRAY_STAINED_GLASS), part, LaserBladeColor.GRAY);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_GRAY_STAINED_GLASS), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, Ingredient.of(Items.CYAN_STAINED_GLASS), part, LaserBladeColor.CYAN);
        addColorRecipe(template, Ingredient.of(Items.PURPLE_STAINED_GLASS), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, Ingredient.of(Items.BLUE_STAINED_GLASS), part, LaserBladeColor.BLUE);
        addColorRecipe(template, Ingredient.of(Items.BROWN_STAINED_GLASS), part, LaserBladeColor.BROWN);
        addColorRecipe(template, Ingredient.of(Items.GREEN_STAINED_GLASS), part, LaserBladeColor.GREEN);
        addColorRecipe(template, Ingredient.of(Items.RED_STAINED_GLASS), part, LaserBladeColor.RED);
        addColorRecipe(template, Ingredient.of(Items.BLACK_STAINED_GLASS), part, LaserBladeColor.BLACK);

        addColorRecipe(template, Ingredient.of(Blocks.TINTED_GLASS), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addGripColorRecipes(Ingredient template) {
        LaserBladeColorPart part = LaserBladeColorPart.GRIP;
        addColorRecipe(template, Ingredient.of(Blocks.WHITE_CARPET), part, LaserBladeColor.WHITE);
        addColorRecipe(template, Ingredient.of(Blocks.ORANGE_CARPET), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, Ingredient.of(Blocks.MAGENTA_CARPET), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, Ingredient.of(Blocks.LIGHT_BLUE_CARPET), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, Ingredient.of(Blocks.YELLOW_CARPET), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, Ingredient.of(Blocks.LIME_CARPET), part, LaserBladeColor.LIME);
        addColorRecipe(template, Ingredient.of(Blocks.PINK_CARPET), part, LaserBladeColor.PINK);
        addColorRecipe(template, Ingredient.of(Blocks.GRAY_CARPET), part, LaserBladeColor.GRAY);
        addColorRecipe(template, Ingredient.of(Blocks.LIGHT_GRAY_CARPET), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, Ingredient.of(Blocks.CYAN_CARPET), part, LaserBladeColor.CYAN);
        addColorRecipe(template, Ingredient.of(Blocks.PURPLE_CARPET), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, Ingredient.of(Blocks.BLUE_CARPET), part, LaserBladeColor.BLUE);
        addColorRecipe(template, Ingredient.of(Blocks.BROWN_CARPET), part, LaserBladeColor.BROWN);
        addColorRecipe(template, Ingredient.of(Blocks.GREEN_CARPET), part, LaserBladeColor.GREEN);
        addColorRecipe(template, Ingredient.of(Blocks.RED_CARPET), part, LaserBladeColor.RED);
        addColorRecipe(template, Ingredient.of(Blocks.BLACK_CARPET), part, LaserBladeColor.BLACK);
    }

    private void addColorRecipe(Ingredient template, Ingredient addition, LaserBladeColorPart part, LaserBladeColor color) {
        int colorValue;

        switch(part) {
            case OUTER_BLADE -> colorValue = color.getOuterColor();
            case INNER_BLADE -> colorValue = color.getInnerColor();
            default -> colorValue = color.getGripColor();
        }

        LBColorRecipeBuilder.colorRecipe(template, Ingredient.of(ModItems.LASER_BLADE), addition, RecipeCategory.TOOLS, part, colorValue)
                .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                .save(output, "tolaserblade:color/lb_" + part.getShortName() + "_" + color.getColorName());
        LBColorRecipeBuilder.colorRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), addition, RecipeCategory.TOOLS, part, colorValue)
                .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .save(output, ToLaserBlade.MOD_ID + ":color/lbf_" + part.getShortName() + "_" + color.getColorName());
    }

    private void addModelChangeRecipes(Ingredient template, Ingredient addition, int modelType, String suffixIn) {
        String suffix = (suffixIn == null) ? "" : suffixIn;

        LBModelChangeRecipeBuilder.modelChangeRecipe(template, Ingredient.of(ModItems.LASER_BLADE), addition, RecipeCategory.TOOLS, modelType)
                .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                .save(output, "tolaserblade:model/lb_" + modelType + suffix);
        LBModelChangeRecipeBuilder.modelChangeRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), addition, RecipeCategory.TOOLS, modelType)
                .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .save(output, "tolaserblade:model/lbf_" + modelType + suffix);
    }

    private ResourceLocation getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    private Ingredient getIngredient(TagKey<Item> tag) {
        return Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            return new TLBRecipeProvider(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
