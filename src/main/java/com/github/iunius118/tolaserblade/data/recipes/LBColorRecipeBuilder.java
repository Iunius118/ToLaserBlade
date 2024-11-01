package com.github.iunius118.tolaserblade.data.recipes;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.world.item.crafting.LBColorRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LBColorRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final LaserBladeColorPart part;
    private final int color;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public LBColorRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, LaserBladeColorPart colorPart, int color) {
        this.category = category;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.part = colorPart;
        this.color = color;
    }

    public static LBColorRecipeBuilder colorRecipe(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, LaserBladeColorPart colorPart, int color) {
        return new LBColorRecipeBuilder(template, base, addition, category, colorPart, color);
    }

    public LBColorRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    public void save(RecipeOutput output, String id) {
        save(output, ResourceKey.create(Registries.RECIPE, ResourceLocation.parse(id)));
    }

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        final ResourceLocation location = id.location();
        ensureValid(location);
        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancementBuilder::addCriterion);
        LBColorRecipe recipe = new LBColorRecipe(Optional.of(template), Optional.of(base), Optional.of(addition), part, color);
        output.accept(id, recipe, advancementBuilder.build(location.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
