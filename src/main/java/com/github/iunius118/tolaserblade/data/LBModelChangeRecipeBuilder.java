package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.crafting.LBModelChangeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedHashMap;
import java.util.Map;

public class LBModelChangeRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final int type;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public LBModelChangeRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, int type) {
        this.category = category;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    public static LBModelChangeRecipeBuilder modelChangeRecipe(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, int type) {
        return new LBModelChangeRecipeBuilder(template, base, addition, category, type);
    }

    public LBModelChangeRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    public void save(RecipeOutput consumer, String id) {
        save(consumer, new ResourceLocation(id));
    }

    public void save(RecipeOutput consumer, ResourceLocation id) {
        ensureValid(id);
        Advancement.Builder advancementBuilder = consumer.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancementBuilder::addCriterion);
        LBModelChangeRecipe recipe = new LBModelChangeRecipe(template, base, addition, type);
        consumer.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
