package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class UpgradeRecipeBuilder {
    private final IRecipeSerializer<?> serializer;
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public UpgradeRecipeBuilder(IRecipeSerializer<?> serializer, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        this.serializer = serializer;
        this.base = base;
        this.addition = addition;
        this.upgradeId = upgradeId;
    }

    public static UpgradeRecipeBuilder upgradeRecipe(Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        return new UpgradeRecipeBuilder(ModRecipeSerializers.UPGRADE, base, addition, upgradeId);
    }

    public UpgradeRecipeBuilder addCriterion(String name, ICriterionInstance criterion) {
        advancementBuilder.addCriterion(name, criterion);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer, String id) {
        build(consumer, new ResourceLocation(id));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        if (advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }

        advancementBuilder
                .parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(IRequirementsStrategy.OR);
        consumer.accept(new UpgradeRecipeBuilder.Result(id, serializer, base, addition, upgradeId, advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final IRecipeSerializer<?> serializer;
        private final Ingredient base;
        private final Ingredient addition;
        private final ResourceLocation upgradeId;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, IRecipeSerializer<?> serializer, Ingredient base, Ingredient addition, ResourceLocation upgradeId, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
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
