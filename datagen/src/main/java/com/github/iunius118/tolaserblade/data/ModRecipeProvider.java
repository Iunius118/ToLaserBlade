package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.data.recipe.BlueprintRecipeBuilder;
import com.github.iunius118.tolaserblade.item.LaserBladeColor;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.crafting.ColoringRecipe;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ModRecipeProvider extends VanillaRecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        // Laser Blade Blueprint
        this.shaped(RecipeCategory.MISC, ModItems.BL_BLUEPRINT)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('B', Tags.Items.DYES_BLUE)
                .define('P', Items.PAPER)
                .pattern("B#D")
                .pattern("#G#")
                .pattern("R#P")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(output);
        // Laser Blade (Fire-Resistant)
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(ModItems.LASER_BLADE), this.tag(ItemTags.NETHERITE_TOOL_MATERIALS),
                        RecipeCategory.TOOLS, ModItems.LASER_BLADE_FP)
                .unlocks("has_laser_blade", this.has(ModItems.LASER_BLADE))
                .save(this.output, getItemId(ModItems.LASER_BLADE_FP) + "_smithing");

        /* Blueprint Recipes */

        // Crafting
        blueprintCrafting(RecipeCategory.MISC, new ItemStackTemplate(ModItems.BL_BLUEPRINT))
                .requires(Items.PAPER)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/lb_blueprint"));
        blueprintCrafting(RecipeCategory.MISC, new ItemStackTemplate(ModItems.LB_CASING))
                .requires(Tags.Items.INGOTS_IRON)
                .requires(Tags.Items.INGOTS_IRON)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/lb_casing"));
        blueprintCrafting(RecipeCategory.MISC, new ItemStackTemplate(ModItems.LB_CASING_FP))
                .requires(Tags.Items.INGOTS_IRON)
                .requires(Tags.Items.INGOTS_NETHERITE)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/lb_casing_fp"));
        blueprintCrafting(RecipeCategory.MISC, new ItemStackTemplate(ModItems.LB_BATTERY))
                .requires(Tags.Items.INGOTS_IRON)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/lb_battery"));
        blueprintCrafting(RecipeCategory.MISC, new ItemStackTemplate(ModItems.LB_MEDIUM))
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/lb_medium"));
        blueprintCrafting(RecipeCategory.MISC, new ItemStackTemplate(ModItems.LB_EMITTER))
                .requires(Tags.Items.INGOTS_IRON)
                .requires(Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/lb_emitter"));
        blueprintCrafting(RecipeCategory.TOOLS, new ItemStackTemplate(ModItems.LASER_BLADE))
                .requires(ModItems.LB_CASING)
                .requires(ModItems.LB_BATTERY)
                .requires(ModItems.LB_MEDIUM)
                .requires(ModItems.LB_EMITTER)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/laser_blade"));
        blueprintCrafting(RecipeCategory.TOOLS, new ItemStackTemplate(ModItems.LASER_BLADE_FP))
                .requires(ModItems.LB_CASING_FP)
                .requires(ModItems.LB_BATTERY)
                .requires(ModItems.LB_MEDIUM)
                .requires(ModItems.LB_EMITTER)
                .unlockedBy("has_lb_blueprint", has(ModItems.BL_BLUEPRINT))
                .save(output, CommonClass.modLocation("blueprint/crafting/laser_blade_fp"));

        // Blending
        blueprintBlending(RecipeCategory.MISC)
                .requires(ModTags.Items.LASER_BLADES)
                .requires(Items.TINTED_GLASS)
                .unlockedBy("has_laser_blade", this.has(ModItems.LASER_BLADE))
                .save(output, CommonClass.modLocation("blueprint/blending/tinted_glass"));

        // Coloring
        buildColoringRecipes();

        // Enchantments
        blueprintEnchantment(RecipeCategory.MISC, ModEnchantments.LASER_BLADE)
                .requires(ModTags.Items.LASER_BLADES)
                .requires(ModTags.Items.LASER_BLADE_UPGRADE)
                .unlockedBy("has_laser_blade", this.has(ModItems.LASER_BLADE))
                .save(output, CommonClass.modLocation("blueprint/enchantment/laser_blade"));
        blueprintEnchantment(RecipeCategory.MISC, ModEnchantments.LIGHT_ELEMENT)
                .requires(ModTags.Items.LASER_BLADES)
                .requires(ModTags.Items.LIGHT_ELEMENT_UPGRADE)
                .unlockedBy("has_laser_blade", this.has(ModItems.LASER_BLADE))
                .save(output, CommonClass.modLocation("blueprint/enchantment/light_element"));
        blueprintEnchantment(RecipeCategory.MISC, Enchantments.LOOTING)
                .requires(ModTags.Items.LASER_BLADES)
                .requires(ModTags.Items.LOOTING_UPGRADE)
                .unlockedBy("has_laser_blade", this.has(ModItems.LASER_BLADE))
                .save(output, CommonClass.modLocation("blueprint/enchantment/looting"));

        // Remodel
        buildRemodelRecipes();
    }

    private void buildColoringRecipes() {
        Function<TagKey<Item>, Ingredient> tagToIngredient = tag -> Ingredient.of(items.getOrThrow(tag));

        // Dyes
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_WHITE), LaserBladeColor.WHITE);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_ORANGE), LaserBladeColor.ORANGE);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_MAGENTA), LaserBladeColor.MAGENTA);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_LIGHT_BLUE), LaserBladeColor.LIGHT_BLUE);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_YELLOW), LaserBladeColor.YELLOW);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_LIME), LaserBladeColor.LIME);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_PINK), LaserBladeColor.PINK);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_GRAY), LaserBladeColor.GRAY);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_LIGHT_GRAY), LaserBladeColor.LIGHT_GRAY);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_CYAN), LaserBladeColor.CYAN);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_PURPLE), LaserBladeColor.PURPLE);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_BLUE), LaserBladeColor.BLUE);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_BROWN), LaserBladeColor.BROWN);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_GREEN), LaserBladeColor.GREEN);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_RED), LaserBladeColor.RED);
        buildColoringRecipe(tagToIngredient.apply(Tags.Items.DYES_BLACK), LaserBladeColor.BLACK);

        // Biomes
        buildColoringRecipe(Ingredient.of(Items.SCULK), LaserBladeColor.BIOME_DEEP_DARK);
        buildColoringRecipe(Ingredient.of(Items.CHERRY_LOG), LaserBladeColor.BIOME_CHERRY_GROVE);
        buildColoringRecipe(Ingredient.of(Items.PALE_OAK_LOG), LaserBladeColor.BIOME_PALE_GARDEN);
    }

    private void buildColoringRecipe(Ingredient ingredient, LaserBladeColor color) {
        blueprintColoring(RecipeCategory.MISC, color)
                .requires(ModTags.Items.BLUEPRINT_CAN_CHANGE_COLOR)
                .requires(ingredient)
                .unlockedBy("has_laser_blade", this.has(ModTags.Items.LASER_BLADES))
                .save(output, CommonClass.modLocation("blueprint/coloring/" + color.colorName()));
    }

    private void buildRemodelRecipes() {
        buildRemodelRecipe(Ingredient.of(Items.GLASS), 0);
        buildRemodelRecipe(Ingredient.of(Items.SAND), 1);
        buildRemodelRecipe(Ingredient.of(Items.SANDSTONE), 2);
        buildRemodelRecipe(Ingredient.of(Items.DIRT), 526);
    }

    private void buildRemodelRecipe(Ingredient ingredient, int modelType) {
        blueprintRemodel(RecipeCategory.MISC, modelType)
                .requires(ModTags.Items.LASER_BLADES)
                .requires(ingredient)
                .unlockedBy("has_laser_blade", this.has(ModTags.Items.LASER_BLADES))
                .save(output, CommonClass.modLocation("blueprint/remodel/model_" + modelType));
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    private BlueprintRecipeBuilder blueprintBlending(RecipeCategory category) {
        return BlueprintRecipeBuilder.blending(this.items, category);
    }

    private BlueprintRecipeBuilder blueprintColoring(RecipeCategory category, LaserBladeColor color) {
        var colors = List.of(
                new ColoringRecipe.PartColor(color.gripColor(), ColoringRecipe.PartColor.BlendMode.UNCHANGED),
                new ColoringRecipe.PartColor(color.outerBladeColor(),
                        color.isOuterBladeSubColor() ? ColoringRecipe.PartColor.BlendMode.SUBTRACTIVE
                                : ColoringRecipe.PartColor.BlendMode.UNCHANGED),
                new ColoringRecipe.PartColor(color.innerBladeColor(),
                        color.isInnerBladeSubColor() ? ColoringRecipe.PartColor.BlendMode.SUBTRACTIVE
                                : ColoringRecipe.PartColor.BlendMode.UNCHANGED)
        );
        return BlueprintRecipeBuilder.coloring(this.items, category, colors);
    }

    private BlueprintRecipeBuilder blueprintCrafting(RecipeCategory category, ItemStackTemplate result) {
        return BlueprintRecipeBuilder.crafting(this.items, category, result);
    }

    private BlueprintRecipeBuilder blueprintEnchantment(RecipeCategory category, ResourceKey<Enchantment> enchantment) {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return BlueprintRecipeBuilder.enchantment(this.items, enchantments, category, enchantment);
    }

    private BlueprintRecipeBuilder blueprintRemodel(RecipeCategory category, int modelType) {
        return BlueprintRecipeBuilder.remodel(this.items, category, modelType);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            return new ModRecipeProvider(registryLookup, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
