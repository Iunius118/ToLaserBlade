package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.item.crafting.ModRecipeSerializers;
import com.github.iunius118.tolaserblade.laserblade.ColorPart;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.DyeColor;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ColorRecipeBuilder {
    private final IRecipeSerializer<?> serializer;
    private final Ingredient base;
    private final Ingredient addition;
    private final String part;
    private final String color;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();

    public ColorRecipeBuilder(IRecipeSerializer<?> serializer, Ingredient base, Ingredient addition, ColorPart colorPart, DyeColor dyeColor) {
        this.serializer = serializer;
        this.base = base;
        this.addition = addition;
        this.part = colorPart.getPartName();
        this.color = dyeColor.getTranslationKey();
    }

    public static ColorRecipeBuilder colorRecipe(Ingredient base, Ingredient addition, ColorPart colorPart, DyeColor dyeColor) {
        return new ColorRecipeBuilder(ModRecipeSerializers.COLOR, base, addition, colorPart, dyeColor);
    }

    public ColorRecipeBuilder addCriterion(String name, ICriterionInstance criterion) {
        advancementBuilder.withCriterion(name, criterion);
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
                .withParentId(new ResourceLocation("recipes/root"))
                .withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id))
                .withRewards(AdvancementRewards.Builder.recipe(id))
                .withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new ColorRecipeBuilder.Result(id, serializer, base, addition, part, color, advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final IRecipeSerializer<?> serializer;
        private final Ingredient base;
        private final Ingredient addition;
        private final String part;
        private final String color;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, IRecipeSerializer<?> serializer, Ingredient base, Ingredient addition, String part, String color, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
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
        public void serialize(JsonObject json) {
            json.add("base", this.base.serialize());
            json.add("addition", this.addition.serialize());
            JsonObject result = new JsonObject();
            result.addProperty("part", part);
            result.addProperty("color", color);
            json.add("result", result);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return serializer;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return advancementBuilder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return advancementId;
        }
    }
}
