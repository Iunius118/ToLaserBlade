package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class LBColorRecipeBuilder {
    private final RecipeSerializer<?> serializer;
    private final Ingredient base;
    private final Ingredient addition;
    private final String part;
    private final int color;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public LBColorRecipeBuilder(RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, LaserBladeColorPart colorPart, int color) {
        this.serializer = serializer;
        this.base = base;
        this.addition = addition;
        this.part = colorPart.getPartName();
        this.color = color;
    }

    public static LBColorRecipeBuilder colorRecipe(Ingredient base, Ingredient addition, LaserBladeColorPart colorPart, int color) {
        return new LBColorRecipeBuilder(ModRecipeSerializers.COLOR, base, addition, colorPart, color);
    }

    public LBColorRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterion) {
        advancementBuilder.addCriterion(name, criterion);
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer, String id) {
        build(consumer, new ResourceLocation(id));
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        if (advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }

        advancementBuilder
                .parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        consumer.accept(new LBColorRecipeBuilder.Result(id, serializer, base, addition, part, color, advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final RecipeSerializer<?> serializer;
        private final Ingredient base;
        private final Ingredient addition;
        private final String part;
        private final int color;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, String part, int color, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
            this.id = id;
            this.serializer = serializer;
            this.base = base;
            this.addition = addition;
            this.part = part;
            this.color = color;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("base", this.base.toJson());
            json.add("addition", this.addition.toJson());
            JsonObject result = new JsonObject();
            result.addProperty("part", part);
            result.addProperty("color", color);
            json.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return serializer;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancementBuilder.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
