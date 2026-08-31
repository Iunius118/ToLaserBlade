package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new ModAdvancementGenerator()));
    }

    private static class ModAdvancementGenerator implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> output) {
            HolderLookup.RegistryLookup<Item> itemRegistryLookup = provider.lookupOrThrow(Registries.ITEM);

            // Main root
            AdvancementHolder root = Advancement.Builder.recipeAdvancement()
                    .display(ModItems.LASER_BLADE,
                            createTitle("main", "root"),
                            createDescription("main", "root"),
                            Identifier.withDefaultNamespace("block/polished_andesite"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("has_redstone", hasItems(Items.REDSTONE))
                    .addCriterion("has_laser_blade", hasItems(ModItems.LASER_BLADE))
                    .addCriterion("has_laser_blade_fp", hasItems(ModItems.LASER_BLADE_FP))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(output, createAdvancementId("main", "root"));

            // 1. Ancient Technology
            AdvancementHolder laserBlade = Advancement.Builder.recipeAdvancement()
                    .parent(root)
                    .display(new ItemStackTemplate(ModItems.LB_BLUEPRINT),
                            createTitle("main", "laser_blade"),
                            createDescription("main", "laser_blade"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .rewards(AdvancementRewards.Builder.loot(Constants.LootTables.REWARD_LB_BLUEPRINT))
                    .addCriterion("has_laser_blade", hasItems(ModItems.LASER_BLADE))
                    .addCriterion("has_laser_blade_fp", hasItems(ModItems.LASER_BLADE_FP))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(output, createAdvancementId("main", "laser_blade"));;

            // 1-1. It's Over 9
            AdvancementHolder laserBlade2 = addEnchantmentAdvancement(laserBlade,
                    new ItemStackTemplate(Items.DIAMOND_BLOCK), AdvancementType.TASK,
                    List.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP), provider,
                    Constants.Enchantments.LASER_BLADE, 2, "main", output);

            // 1-1-1. Beyond the Limit
            AdvancementHolder laserBlade5 = addEnchantmentAdvancement(laserBlade2,
                    new ItemStackTemplate(Items.DIAMOND_BLOCK), AdvancementType.TASK,
                    List.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP), provider,
                    Constants.Enchantments.LASER_BLADE, 5, "main", output);

            // 1-2. Power of Light
            AdvancementHolder lightElement2 = addEnchantmentAdvancement(laserBlade,
                    new ItemStackTemplate(Items.GLOWSTONE), AdvancementType.TASK,
                    List.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP), provider,
                    Constants.Enchantments.LIGHT_ELEMENT, 2, "main", output);

            // 1-2-1. Unlimited Power
            AdvancementHolder lightElement5 = addEnchantmentAdvancement(lightElement2,
                    new ItemStackTemplate(Items.GLOWSTONE), AdvancementType.TASK,
                    List.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP), provider,
                    Constants.Enchantments.LIGHT_ELEMENT, 5, "main", output);

            // 1-3. Give Me Three
            AdvancementHolder looting3 = addEnchantmentAdvancement(laserBlade,
                    new ItemStackTemplate(Items.NAUTILUS_SHELL), AdvancementType.TASK,
                    List.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP), provider,
                    Enchantments.LOOTING, 3, "main", output);

            // 1-4. Into The Core
            AdvancementHolder laserBladeFP = addItemAdvancement(laserBlade,
                    new ItemStackTemplate(Items.NETHERITE_INGOT), AdvancementType.TASK,
                    List.of(ModItems.LASER_BLADE_FP), "main", output);

            // 1-5. Life-time Support
            AdvancementHolder mending = addEnchantmentAdvancement(laserBlade,
                    new ItemStackTemplate(Items.ENCHANTED_BOOK), AdvancementType.GOAL,
                    List.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP), provider,
                    Enchantments.MENDING, 1, "main", output);

            // 1-6. Returns and Exchanges
            AdvancementHolder breakLaserBlade = Advancement.Builder.recipeAdvancement()
                    .parent(laserBlade)
                    .display(ModItems.LB_CASING,
                            createTitle("main", "break_laser_blade"),
                            createDescription("main", "break_laser_blade"),
                            null,
                            AdvancementType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("broke_laser_blade",
                            ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                    Optional.of(ItemPredicate.Builder.item()
                                            .of(itemRegistryLookup, ModItems.LASER_BLADE).build()),
                                    MinMaxBounds.Ints.atMost(0)))
                    .addCriterion("broke_laser_blade_fp",
                            ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                    Optional.of(ItemPredicate.Builder.item()
                                            .of(itemRegistryLookup, ModItems.LASER_BLADE_FP).build()),
                                    MinMaxBounds.Ints.atMost(0)))
                    .save(output, createAdvancementId("main", "break_laser_blade"));
        }

        private AdvancementHolder addItemAdvancement(AdvancementHolder parent,
                                                     ItemStackTemplate icon, AdvancementType advancementType,
                                                     List<Item> requirements,
                                                     String tab, String name, Consumer<AdvancementHolder> output) {
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            createTitle(tab, name),
                            createDescription(tab, name),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("has_" + itemName,  InventoryChangeTrigger.TriggerInstance.hasItems(item));
            }

            return builder.save(output, createAdvancementId(tab, name));
        }

        private AdvancementHolder addItemAdvancement(AdvancementHolder parent,
                                                     ItemStackTemplate icon, AdvancementType advancementType,
                                                     List<Item> requirements,
                                                     String tab, Consumer<AdvancementHolder> output) {
            return addItemAdvancement(parent, icon, advancementType, requirements,
                    tab, getItemId(requirements.getFirst()).getPath(), output);
        }

        private AdvancementHolder addEnchantmentAdvancement(AdvancementHolder parent,
                                                            ItemStackTemplate icon, AdvancementType advancementType,
                                                            List<Item> requirements,
                                                            HolderLookup.Provider provider,
                                                            ResourceKey<Enchantment> enchantment, int level,
                                                            String tab, Consumer<AdvancementHolder> output) {
            var items = provider.lookupOrThrow(Registries.ITEM);
            var enchantments = provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
            String name = "%s_%s_%d".formatted(
                    getItemId(requirements.getFirst()).getPath(), getEnchantmentId(enchantments).getPath(), level);
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            createTitle(tab, name),
                            createDescription(tab, name),
                            null,
                            advancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                        .of(items, item)
                        .withComponents(DataComponentMatchers.Builder.components()
                                .partial(DataComponentPredicates.ENCHANTMENTS,
                                        EnchantmentsPredicate.enchantments(List.of(
                                                        new EnchantmentPredicate(enchantments,
                                                                MinMaxBounds.Ints.atLeast(level))
                                                )
                                        )
                                )
                                .build())
                        .build();

                builder.addCriterion("has_" + itemName, InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
            }

            return builder.save(output, createAdvancementId(tab, name));
        }

        private Item getItem(Identifier id) {
            return BuiltInRegistries.ITEM.getValue(id);
        }

        private Identifier getItemId(Item item) {
            return BuiltInRegistries.ITEM.getKey(item);
        }

        private Identifier getEnchantmentId(Holder<Enchantment> enchantment) {
            return enchantment.unwrapKey().orElseThrow().identifier();
        }

        private Component createTitle(String tab, String name) {
            return Component.translatable("advancements.%s.%s.%s.title".formatted(Constants.MOD_ID, tab, name));
        }

        private Component createDescription(String tab, String name) {
            return Component.translatable("advancements.%s.%s.%s.description".formatted(Constants.MOD_ID, tab, name));
        }

        private Criterion<InventoryChangeTrigger.TriggerInstance> hasItems(ItemLike... items) {
            return InventoryChangeTrigger.TriggerInstance.hasItems(items);
        }

        private String createAdvancementId(String tab, String name) {
            return "%s:%s/%s".formatted(Constants.MOD_ID, tab, name);
        }
    }
}
