package com.github.iunius118.tolaserblade.data.recipe;

import com.github.iunius118.tolaserblade.item.crafting.*;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public abstract class BlueprintRecipeBuilder {
    private final HolderGetter<Item> items;
    private final RecipeCategory category;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

    private Recipe.CommonInfo commonInfo = new Recipe.CommonInfo(true);

    private BlueprintRecipeBuilder(HolderGetter<Item> items, RecipeCategory category) {
        this.items = items;
        this.category = category;
    }

    public static BlueprintRecipeBuilder blending(HolderGetter<Item> items, RecipeCategory category) {
        return new BlueprintRecipeBuilder(items, category) {

            @Override
            protected void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements) {
                List<Ingredient> ingredients = ingredients();

                if (ingredients.size() >= 2) {
                    var recipe = new BlendingRecipe(commonInfo(), ingredients.get(0), ingredients.get(1));
                    output.accept(id, recipe, advancements);
                } else {
                    throw new IllegalStateException("At least 2 ingredients are required.");
                }
            }
        };
    }

    public static BlueprintRecipeBuilder coloring(HolderGetter<Item> items, RecipeCategory category,
                                                  List<ColoringRecipe.PartColor> colors) {
        return new BlueprintRecipeBuilder(items, category) {

            @Override
            protected void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements) {
                List<Ingredient> ingredients = ingredients();

                if (ingredients.size() >= 2) {
                    var recipe = new ColoringRecipe(commonInfo(), ingredients.get(0), ingredients.get(1), colors);
                    output.accept(id, recipe, advancements);
                } else {
                    throw new IllegalStateException("At least 2 ingredients are required.");
                }
            }
        };
    }

    public static BlueprintRecipeBuilder crafting(HolderGetter<Item> items, RecipeCategory category,
                                                  ItemStackTemplate result) {
        return new BlueprintRecipeBuilder(items, category) {

            @Override
            protected void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements) {
                List<Ingredient> ingredients = ingredients();

                if (!ingredients.isEmpty()) {
                    var recipe = new CraftingRecipe(commonInfo(), ingredients, result);
                    output.accept(id, recipe, advancements);
                } else {
                    throw new IllegalStateException("At least 1 ingredient are required.");
                }
            }
        };
    }

    public static BlueprintRecipeBuilder enchantment(HolderGetter<Item> items, HolderGetter<Enchantment> enchantments,
                                                     RecipeCategory category, ResourceKey<Enchantment> enchantment) {
        return new BlueprintRecipeBuilder(items, category) {

            @Override
            protected void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements) {
                List<Ingredient> ingredients = ingredients();

                if (ingredients.size() >= 2) {
                    var recipe = new EnchantmentRecipe(commonInfo(), ingredients, enchantments.getOrThrow(enchantment));
                    output.accept(id, recipe, advancements);
                } else {
                    throw new IllegalStateException("At least 2 ingredients are required.");
                }
            }
        };
    }

    public static BlueprintRecipeBuilder remodel(HolderGetter<Item> items, RecipeCategory category,
                                                 int modelType) {
        return new BlueprintRecipeBuilder(items, category) {

            @Override
            protected void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements) {
                List<Ingredient> ingredients = ingredients();

                if (ingredients.size() >= 2) {
                    var recipe = new RemodelRecipe(commonInfo(), ingredients, modelType);
                    output.accept(id, recipe, advancements);
                } else {
                    throw new IllegalStateException("At least 2 ingredients are required.");
                }
            }
        };
    }

    public static BlueprintRecipeBuilder repair(HolderGetter<Item> items, RecipeCategory category,
                                                 float rate) {
        return new BlueprintRecipeBuilder(items, category) {

            @Override
            protected void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements) {
                List<Ingredient> ingredients = ingredients();

                if (ingredients.size() >= 2) {
                    var recipe = new RepairRecipe(commonInfo(), ingredients, rate);
                    output.accept(id, recipe, advancements);
                } else {
                    throw new IllegalStateException("At least 2 ingredients are required.");
                }
            }
        };
    }

    public BlueprintRecipeBuilder requires(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public BlueprintRecipeBuilder requires(TagKey<Item> tag) {
        return requires(Ingredient.of(items.getOrThrow(tag)));
    }

    public BlueprintRecipeBuilder requires(ItemLike item) {
        return requires(Ingredient.of(item));
    }

    public BlueprintRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public BlueprintRecipeBuilder showNotification(boolean showNotification) {
        this.commonInfo = new Recipe.CommonInfo(showNotification);
        return this;
    }

    protected abstract void save(RecipeOutput output, ResourceKey<Recipe<?>> id, AdvancementHolder advancements);

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        AdvancementHolder advancements = advancementBuilder.build(output, id, category);
        save(output, id, advancements);
    }

    public void save(RecipeOutput output, Identifier id) {
        save(output, ResourceKey.create(Registries.RECIPE, id));
    }

    public void save(RecipeOutput output, String id) {
        save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(id)));
    }

    protected List<Ingredient> ingredients() {
        return ingredients;
    }

    protected Recipe.CommonInfo commonInfo() {
        return commonInfo;
    }
}
