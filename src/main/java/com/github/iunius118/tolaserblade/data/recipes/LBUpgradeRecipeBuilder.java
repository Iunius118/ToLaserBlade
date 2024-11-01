package com.github.iunius118.tolaserblade.data.recipes;

import com.github.iunius118.tolaserblade.world.item.crafting.LBUpgradeRecipe;
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

public class LBUpgradeRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final ResourceLocation upgradeId;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public LBUpgradeRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, ResourceLocation upgradeId) {
        this.category = category;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.upgradeId = upgradeId;
    }

    public static LBUpgradeRecipeBuilder upgradeRecipe(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, ResourceLocation upgradeId) {
        return new LBUpgradeRecipeBuilder(template, base, addition, category, upgradeId);
    }

    public LBUpgradeRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
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
        LBUpgradeRecipe recipe = new LBUpgradeRecipe(Optional.of(template), Optional.of(base), Optional.of(addition), upgradeId);
        output.accept(id, recipe, advancementBuilder.build(location.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
