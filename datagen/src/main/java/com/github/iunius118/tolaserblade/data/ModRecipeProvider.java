package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.item.LaserBladeColor;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.component.BlendModes;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends VanillaRecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        // Laser Blade
        this.shaped(RecipeCategory.TOOLS, ModItems.LASER_BLADE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("G#D")
                .pattern("#D#")
                .pattern("R#G")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(output);
        // Laser Blade (Fire-Resistant)
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(ModItems.LASER_BLADE), this.tag(ItemTags.NETHERITE_TOOL_MATERIALS),
                        RecipeCategory.TOOLS, ModItems.LASER_BLADE_FP)
                .unlocks("has_laser_blade", this.has(ModItems.LASER_BLADE))
                .save(this.output, getItemId(ModItems.LASER_BLADE_FP) + "_smithing");
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    public static class ItemStackTemplateBuilder {
        private final HolderLookup.Provider registries;
        private final DataComponentPatch.Builder dataComponentBuilder = DataComponentPatch.builder();
        private final Object2IntOpenHashMap<Holder<Enchantment>> enchantments = new Object2IntOpenHashMap<>();

        public ItemStackTemplateBuilder(HolderLookup.Provider registries) {
            this.registries = registries;
        }

        public static ItemStackTemplateBuilder builder(HolderLookup.Provider registries) {
            return new ItemStackTemplateBuilder(registries);
        }

        public <T> ItemStackTemplateBuilder component(DataComponentType<T> type, T value) {
            dataComponentBuilder.set(type, value);
            return this;
        }

        public ItemStackTemplateBuilder repair() {
            dataComponentBuilder.set(DataComponents.DAMAGE, 0);
            return this;
        }

        public ItemStackTemplateBuilder enchant(ResourceKey<Enchantment> enchantmentKey, int level) {
            var enchantment = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantmentKey);
            enchantments.put(enchantment, level);
            return this;
        }

        public ItemStackTemplateBuilder color(LaserBladeColor color) {
            dataComponentBuilder.set(DataComponents.CUSTOM_MODEL_DATA, color.getCustomModelData());
            return this;
        }

        public ItemStackTemplateBuilder switchBlendMode() {
            dataComponentBuilder.set(ModDataComponents.BLEND_MODES, BlendModes.DEFAULT);
            return this;
        }

        public ItemStackTemplateBuilder model(int model) {
            dataComponentBuilder.set(ModDataComponents.MODEL, model);
            return this;
        }

        public ItemStackTemplate build(Item item) {
            if (!enchantments.isEmpty()) {
                // Add enchantments to the data component patch, if enchantments are present
                var itemEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                enchantments.forEach(itemEnchantments::set);
                dataComponentBuilder.set(DataComponents.ENCHANTMENTS, itemEnchantments.toImmutable());
            }

            var dataComponentPatch = dataComponentBuilder.build();
            return new ItemStackTemplate(item, dataComponentPatch);
        }
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            return new ModRecipeProvider(registryLookup, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
