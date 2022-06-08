package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemStack;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class TLBAdvancementProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public TLBAdvancementProvider(DataGenerator generatorIn) {
        generator = generatorIn;
    }

    protected void registerAdvancements(Consumer<Advancement> consumer) {
        // Main root
        Advancement root = Advancement.Builder.advancement()
                .display(LaserBladeItemStack.ICON.getCopy(),
                        Component.translatable("advancements.tolaserblade.main.root.title"),
                        Component.translatable("advancements.tolaserblade.main.root.description"),
                        new ResourceLocation("textures/block/polished_andesite.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("has_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                .addCriterion("has_dx_laser_blade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DX_LASER_BLADE))
                .addCriterion("has_laser_blade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LASER_BLADE))
                .addCriterion("has_laser_blade_fp", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LASER_BLADE_FP))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, "tolaserblade:main/root");

        // 1. Laser Blade?
        Advancement dxLaserBlade = registerItemAdvancement(root, ModItems.DX_LASER_BLADE, FrameType.TASK,
                new Item[]{ModItems.DX_LASER_BLADE}, consumer);

        // 1-1. Ancient Technology
        Advancement laserBlade = registerItemAdvancement(dxLaserBlade, ModItems.LASER_BLADE, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, consumer);

        // 1-1-1. Power of Light
        Advancement lightElement5 = registerEnchantmentAdvancement(laserBlade, Items.GLOWSTONE, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, ModEnchantments.LIGHT_ELEMENT, 5, consumer);

        // 1-1-1-1. Unlimited Power
        Advancement lightElement10 = registerEnchantmentAdvancement(lightElement5, Items.GLOWSTONE, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, ModEnchantments.LIGHT_ELEMENT, 10, consumer);

        // 1-1-2. It's Over 9
        Advancement attack10 = registerAttackUpgradeAdvancement(laserBlade, Items.DIAMOND, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, 10, consumer);

        // 1-1-2-1. Beyond the Limit
        Advancement attack15 = registerAttackUpgradeAdvancement(attack10, Items.DIAMOND, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, 15, consumer);

        // 1-1-3. Give Me Three
        Advancement looting3 = registerEnchantmentAdvancement(laserBlade, Items.NAUTILUS_SHELL, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, Enchantments.MOB_LOOTING, 3, consumer);

        // 1-1-4. Returns and Exchanges
        JsonObject jsonMaxZero = new JsonObject();
        jsonMaxZero.add("max", new JsonPrimitive(0));
        Advancement breakLaserBlade = Advancement.Builder.advancement()
                .parent(laserBlade)
                .display(ModItems.LB_CASING,
                        Component.translatable("advancements.tolaserblade.main.break_laser_blade.title"),
                        Component.translatable("advancements.tolaserblade.main.break_laser_blade.description"),
                        null,
                        FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(1000))
                .requirements(RequirementsStrategy.OR)
                .addCriterion("broke_laser_blade",
                        ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                ItemPredicate.Builder.item().of(ModItems.LASER_BLADE).build(), MinMaxBounds.Ints.fromJson(jsonMaxZero)))
                .addCriterion("broke_laser_blade_fp",
                        ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                ItemPredicate.Builder.item().of(ModItems.LASER_BLADE_FP).build(), MinMaxBounds.Ints.fromJson(jsonMaxZero)))
                .save(consumer, "tolaserblade:main/break_laser_blade");

        // 1-1-5. Life-time Support
        Advancement mending = registerEnchantmentAdvancement(laserBlade, Items.NETHER_STAR, FrameType.GOAL,
                new Item[]{ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP}, Enchantments.MENDING, 1, consumer);

        // 1-1-6. Into The Core
        Advancement laserBladeFP = registerItemAdvancement(laserBlade, Items.NETHERITE_INGOT, FrameType.TASK,
                new Item[]{ModItems.LASER_BLADE_FP}, consumer);
    }

    private Advancement registerItemAdvancement(Advancement parent, Item icon, FrameType frameType, Item[] requirements, Consumer<Advancement> consumer) {
        String name = getItemId(requirements[0]).getPath();
        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        Component.translatable("advancements.tolaserblade.main." + name + ".title"),
                        Component.translatable("advancements.tolaserblade.main." + name + ".description"),
                        null,
                        frameType, true, true, false)
                .requirements(RequirementsStrategy.OR);

        for (Item item : requirements) {
            String itemName = getItemId(item).getPath();
            builder.addCriterion("has_" + itemName,  InventoryChangeTrigger.TriggerInstance.hasItems(item));
        }

        return builder.save(consumer, "tolaserblade:main/" + name);
    }

    private Advancement registerEnchantmentAdvancement(Advancement parent, Item icon, FrameType frameType, Item[] requirements, Enchantment enchantment, int level, Consumer<Advancement> consumer) {
        String name = getItemId(requirements[0]).getPath() + "_" + getEnchantmentId(enchantment).getPath() + "_" + level;
        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        Component.translatable("advancements.tolaserblade.main." + name + ".title"),
                        Component.translatable("advancements.tolaserblade.main." + name + ".description"),
                        null,
                        frameType, true, true, false)
                .requirements(RequirementsStrategy.OR);

        for (Item item : requirements) {
            String itemName = getItemId(item).getPath();
            ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                    .of(item)
                    .hasEnchantment(new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.atLeast(level)))
                    .build();

            builder.addCriterion("has_" + itemName, InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
        }

        return builder.save(consumer, "tolaserblade:main/" + name);
    }

    private Advancement registerAttackUpgradeAdvancement(Advancement parent, Item icon, FrameType frameType, Item[] requirements, int attackDamage, Consumer<Advancement> consumer) {
        String name = getItemId(requirements[0]).getPath() + "_attack_" + attackDamage;
        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        Component.translatable("advancements.tolaserblade.main." + name + ".title"),
                        Component.translatable("advancements.tolaserblade.main." + name + ".description"),
                        null,
                        frameType, true, true, false)
                .requirements(RequirementsStrategy.OR);
        int maxAtk = (int)LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS;
        String tagAtk = LaserBladePerformance.AttackPerformance.KEY_ATK;

        for (Item item : requirements) {
            String itemName = getItemId(item).getPath();
            int baseDamage = 1;

            if (item instanceof SwordItem swordItem) {
                baseDamage += swordItem.getDamage();
            }

            for (int i = attackDamage - baseDamage; i >= 0 && i <= maxAtk; i++) {
                CompoundTag compound = new CompoundTag();
                compound.putFloat(tagAtk, (float) i);
                ItemPredicate itemPredicate = ItemPredicate.Builder.item()
                        .of(item)
                        .hasNbt(compound)
                        .build();
                builder.addCriterion(itemName + "_attack_" + (i + baseDamage), InventoryChangeTrigger.TriggerInstance.hasItems(itemPredicate));
            }
        }

        return builder.save(consumer, "tolaserblade:main/" + name);
    }



    @Override
    public void run(CachedOutput cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path advancementPath = getPath(path, advancement);

                try {
                    DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), advancementPath);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", advancementPath, ioexception);
                }

            }
        };

        registerAdvancements(consumer);
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    private ResourceLocation getEnchantmentId(Enchantment enchantment) {
        return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "ToLaserBlade Advancements";
    }
}
