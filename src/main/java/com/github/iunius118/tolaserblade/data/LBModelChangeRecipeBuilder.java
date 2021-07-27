package com.github.iunius118.tolaserblade.data;

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

public class LBModelChangeRecipeBuilder {
    private final RecipeSerializer<?> serializer;
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public LBModelChangeRecipeBuilder(RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, int type) {
        this.serializer = serializer;
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    public static LBModelChangeRecipeBuilder modelChangeRecipe(Ingredient base, Ingredient addition, int type) {
        return new LBModelChangeRecipeBuilder(ModRecipeSerializers.MODEL_CHANGE, base, addition, type);
    }

    public LBModelChangeRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterion) {
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
        consumer.accept(new LBModelChangeRecipeBuilder.Result(id, serializer, base, addition, type, advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final RecipeSerializer<?> serializer;
        private final Ingredient base;
        private final Ingredient addition;
        private final int type;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, int type, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
            this.id = id;
            this.serializer = serializer;
            this.base = base;
            this.addition = addition;
            this.type = type;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("base", this.base.toJson());
            json.add("addition", this.addition.toJson());
            JsonObject result = new JsonObject();
            result.addProperty("model_type", type);
            json.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
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
