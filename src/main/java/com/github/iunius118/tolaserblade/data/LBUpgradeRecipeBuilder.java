package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class LBUpgradeRecipeBuilder {
    private final RecipeSerializer<?> serializer;
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public LBUpgradeRecipeBuilder(RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        this.serializer = serializer;
        this.base = base;
        this.addition = addition;
        this.upgradeId = upgradeId;
    }

    public static LBUpgradeRecipeBuilder upgradeRecipe(Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        return new LBUpgradeRecipeBuilder(ModRecipeSerializers.UPGRADE, base, addition, upgradeId);
    }

    public LBUpgradeRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterion) {
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
        consumer.accept(new LBUpgradeRecipeBuilder.Result(id, serializer, base, addition, upgradeId, advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final RecipeSerializer<?> serializer;
        private final Ingredient base;
        private final Ingredient addition;
        private final ResourceLocation upgradeId;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, ResourceLocation upgradeId, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
            this.id = id;
            this.serializer = serializer;
            this.base = base;
            this.addition = addition;
            this.upgradeId = upgradeId;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("base", this.base.toJson());
            json.add("addition", this.addition.toJson());
            JsonObject result = new JsonObject();
            result.addProperty("type", upgradeId.toString());
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
