package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.world.item.crafting.LBColorRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedHashMap;
import java.util.Map;

public class LBColorRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final LaserBladeColorPart part;
    private final int color;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public LBColorRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, LaserBladeColorPart colorPart, int color) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.part = colorPart;
        this.color = color;
    }

    public static LBColorRecipeBuilder colorRecipe(Ingredient template, Ingredient base, Ingredient addition, LaserBladeColorPart colorPart, int color) {
        return new LBColorRecipeBuilder(template, base, addition, colorPart, color);
    }

    public LBColorRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
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
        consumer.accept(id, new LBColorRecipe(template, base, addition, part, color), advancementBuilder.build(new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
