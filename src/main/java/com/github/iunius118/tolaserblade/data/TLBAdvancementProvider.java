package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemStack;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TLBAdvancementProvider extends ForgeAdvancementProvider {
    public TLBAdvancementProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, existingFileHelper, List.of(new TLBAdvancementGenerator()));
    }

    private static class TLBAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            // Main root
            AdvancementHolder root = Advancement.Builder.recipeAdvancement()
                    .display(LaserBladeItemStack.ICON.getCopy(),
                            Component.translatable("advancements.tolaserblade.main.root.title"),
                            Component.translatable("advancements.tolaserblade.main.root.description"),
                            new ResourceLocation("textures/block/polished_andesite.png"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("has_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                    .addCriterion("has_dx_laser_blade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DX_LASER_BLADE))
                    .addCriterion("has_laser_blade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LASER_BLADE))
                    .addCriterion("has_laser_blade_fp", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LASER_BLADE_FP))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(consumer, "tolaserblade:main/root");

            // 1. Laser Blade?
            AdvancementHolder dxLaserBlade = registerItemAdvancement(root, ModItems.DX_LASER_BLADE, AdvancementType.TASK,
                    new Item[]{ModItems.DX_LASER_BLADE}, consumer);

            // 1-1. Ancient Technology
            AdvancementHolder laserBlade = registerItemAdvancement(dxLaserBlade, ModItems.LASER_BLADE, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, consumer);

            // 1-1-1. Power of Light
            AdvancementHolder lightElement5 = registerEnchantmentAdvancement(laserBlade, Items.GLOWSTONE, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, ModEnchantments.LIGHT_ELEMENT, 5, consumer);

            // 1-1-1-1. Unlimited Power
            AdvancementHolder lightElement10 = registerEnchantmentAdvancement(lightElement5, Items.GLOWSTONE, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, ModEnchantments.LIGHT_ELEMENT, 10, consumer);

            // 1-1-2. It's Over 9
            AdvancementHolder attack10 = registerAttackUpgradeAdvancement(laserBlade, Items.DIAMOND, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, 10, consumer);

            // 1-1-2-1. Beyond the Limit
            AdvancementHolder attack15 = registerAttackUpgradeAdvancement(attack10, Items.DIAMOND, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, 15, consumer);

            // 1-1-3. Give Me Three
            AdvancementHolder looting3 = registerEnchantmentAdvancement(laserBlade, Items.NAUTILUS_SHELL, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, Enchantments.LOOTING, 3, consumer);

            // 1-1-4. Returns and Exchanges
            AdvancementHolder breakLaserBlade = Advancement.Builder.recipeAdvancement()
                    .parent(laserBlade)
                    .display(ModItems.LB_CASING,
                            Component.translatable("advancements.tolaserblade.main.break_laser_blade.title"),
                            Component.translatable("advancements.tolaserblade.main.break_laser_blade.description"),
                            null,
                            AdvancementType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("broke_laser_blade",
                            ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                    Optional.of(ItemPredicate.Builder.item().of(ModItems.LASER_BLADE).build()),
                                    MinMaxBounds.Ints.atMost(0)))
                    .addCriterion("broke_laser_blade_fp",
                            ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                    Optional.of(ItemPredicate.Builder.item().of(ModItems.LASER_BLADE_FP).build()),
                                    MinMaxBounds.Ints.atMost(0)))
                    .save(consumer, "tolaserblade:main/break_laser_blade");

            // 1-1-5. Life-time Support
            AdvancementHolder mending = registerEnchantmentAdvancement(laserBlade, Items.NETHER_STAR, AdvancementType.GOAL,
                    new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, Enchantments.MENDING, 1, consumer);

            // 1-1-6. Into The Core
            AdvancementHolder laserBladeFP = registerItemAdvancement(laserBlade, Items.NETHERITE_INGOT, AdvancementType.TASK,
                    new Item[]{ModItems.LASER_BLADE_FP}, consumer);
        }

        private AdvancementHolder registerItemAdvancement(AdvancementHolder parent, Item icon, AdvancementType AdvancementType, Item[] requirements, Consumer<AdvancementHolder> consumer) {
            String name = getItemId(requirements[0]).getPath();
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            Component.translatable("advancements.tolaserblade.main." + name + ".title"),
                            Component.translatable("advancements.tolaserblade.main." + name + ".description"),
                            null,
                            AdvancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                builder.addCriterion("has_" + itemName,  InventoryChangeTrigger.TriggerInstance.hasItems(item));
            }

            return builder.save(consumer, "tolaserblade:main/" + name);
        }

        private AdvancementHolder registerEnchantmentAdvancement(AdvancementHolder parent, Item icon, AdvancementType AdvancementType, Item[] requirements, Enchantment enchantment, int level, Consumer<AdvancementHolder> consumer) {
            String name = getItemId(requirements[0]).getPath() + "_" + getEnchantmentId(enchantment).getPath() + "_" + level;
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            Component.translatable("advancements.tolaserblade.main." + name + ".title"),
                            Component.translatable("advancements.tolaserblade.main." + name + ".description"),
                            null,
                            AdvancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                        .of(item)
                        .withSubPredicate(ItemSubPredicates.ENCHANTMENTS,
                                ItemEnchantmentsPredicate.enchantments(List.of(
                                        new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.atLeast(level)))))
                        .build();
                builder.addCriterion("has_" + itemName, InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
            }

            return builder.save(consumer, "tolaserblade:main/" + name);
        }

        private AdvancementHolder registerAttackUpgradeAdvancement(AdvancementHolder parent, Item icon, AdvancementType AdvancementType, Item[] requirements, int attackDamage, Consumer<AdvancementHolder> consumer) {
            String name = getItemId(requirements[0]).getPath() + "_attack_" + attackDamage;
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                    .parent(parent)
                    .display(icon,
                            Component.translatable("advancements.tolaserblade.main." + name + ".title"),
                            Component.translatable("advancements.tolaserblade.main." + name + ".description"),
                            null,
                            AdvancementType, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR);
            int maxAtk = (int) LaserBlade.MOD_ATK_CRITICAL_BONUS;

            for (Item item : requirements) {
                String itemName = getItemId(item).getPath();
                int baseDamage = 1;

                if (item instanceof LBSwordItem laserBlase) {
                    baseDamage += (int) (laserBlase.getTier().getAttackDamageBonus() + LaserBlade.BASE_ATTACK);
                }

                for (int i = attackDamage - baseDamage; i >= 0 && i <= maxAtk; i++) {
                    ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                            .of(item)
                            .hasComponents(DataComponentPredicate.builder().expect(ModDataComponents.LASER_BLADE_ATTACK, (float) i).build())
                            .build();
                    builder.addCriterion(itemName + "_attack_" + (i + baseDamage), InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
                }
            }

            return builder.save(consumer, "tolaserblade:main/" + name);
        }

        private ResourceLocation getItemId(Item item) {
            return ForgeRegistries.ITEMS.getKey(item);
        }

        private ResourceLocation getEnchantmentId(Enchantment enchantment) {
            return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
        }
    }
}
