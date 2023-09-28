package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class LBModelChangeRecipeBuilder {
    private final RecipeSerializer<?> serializer;
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public LBModelChangeRecipeBuilder(RecipeSerializer<?> serializer, Ingredient template, Ingredient base, Ingredient addition, int type) {
        this.serializer = serializer;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    public static LBModelChangeRecipeBuilder modelChangeRecipe(Ingredient template, Ingredient base, Ingredient addition, int type) {
        return new LBModelChangeRecipeBuilder(ModRecipeSerializers.MODEL_CHANGE, template, base, addition, type);
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
        consumer.accept(new Result(id, serializer, template, base, addition, type, advancementBuilder.build(new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath()))));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final RecipeSerializer<?> serializer;
        private final Ingredient template;
        private final Ingredient base;
        private final Ingredient addition;
        private final int type;
        private final AdvancementHolder advancement;


        public Result(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient template, Ingredient base, Ingredient addition, int type, AdvancementHolder advancement) {
            this.id = id;
            this.serializer = serializer;
            this.template = template;
            this.base = base;
            this.addition = addition;
            this.type = type;
            this.advancement = advancement;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("template", this.template.toJson(true));
            json.add("base", this.base.toJson(true));
            json.add("addition", this.addition.toJson(true));
            JsonObject result = new JsonObject();
            result.addProperty("model_type", type);
            json.add("result", result);
        }

        @Override
        public ResourceLocation id() {
            return id;
        }

        @Override
        public RecipeSerializer<?> type() {
            return serializer;
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return advancement;
        }
    }
}
