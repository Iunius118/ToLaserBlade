package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class TLBAdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public TLBAdvancementProvider(DataGenerator generatorIn) {
        generator = generatorIn;
    }

    protected void registerAdvancements(Consumer<Advancement> consumer) {
        // Main root
        Advancement root = Advancement.Builder.builder()
                .withDisplay(ModItems.LASER_BLADE.setGripColor(new ItemStack(ModItems.LASER_BLADE), LaserBladeItemBase.LBColor.GRAY.getGripColor()),
                        new TranslationTextComponent("advancements.tolaserblade.main.root.title"),
                        new TranslationTextComponent("advancements.tolaserblade.main.root.description"),
                        new ResourceLocation("textures/block/polished_andesite.png"),
                        FrameType.TASK, false, false, false)
                .withCriterion("has_redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
                .withCriterion("has_dx_laser_blade", InventoryChangeTrigger.Instance.forItems(ModItems.DX_LASER_BLADE))
                .withCriterion("has_laser_blade", InventoryChangeTrigger.Instance.forItems(ModItems.LASER_BLADE))
                .withRequirementsStrategy(IRequirementsStrategy.OR)
                .register(consumer, "tolaserblade:main/root");

        // 1. Laser Blade?
        Advancement dxLaserBlade = registerItemAdvancement(root, ModItems.DX_LASER_BLADE, FrameType.TASK,
                ModItems.DX_LASER_BLADE, consumer);

        // 1-1. Ancient Technology
        Advancement laserBlade = registerItemAdvancement(dxLaserBlade, ModItems.LASER_BLADE, FrameType.TASK,
                ModItems.LASER_BLADE, consumer);

        // 1-1-1. Power of Light
        Advancement lightElement5 = registerEnchantmentAdvancement(laserBlade, Items.GLOWSTONE, FrameType.TASK,
                ModItems.LASER_BLADE, ModEnchantments.LIGHT_ELEMENT, 5, consumer);

        // 1-1-1-1. Unlimited Power
        Advancement lightElement10 = registerEnchantmentAdvancement(lightElement5, Items.GLOWSTONE, FrameType.TASK,
                ModItems.LASER_BLADE, ModEnchantments.LIGHT_ELEMENT, 10, consumer);

        // 1-1-2. It's Over 9
        Advancement attack10 = registerAttackUpgradeAdvancement(laserBlade, Items.DIAMOND, FrameType.TASK,
                ModItems.LASER_BLADE, 10, (int)ModItems.LASER_BLADE.getAttackDamage() + 1, consumer);

        // 1-1-2-1. Beyond the Limit
        Advancement attack15 = registerAttackUpgradeAdvancement(attack10, Items.DIAMOND, FrameType.TASK,
                ModItems.LASER_BLADE, 15, (int)ModItems.LASER_BLADE.getAttackDamage() + 1, consumer);

        // 1-1-3. Give Me Three
        Advancement looting3 = registerEnchantmentAdvancement(laserBlade, Items.NAUTILUS_SHELL, FrameType.TASK,
                ModItems.LASER_BLADE, Enchantments.LOOTING, 3, consumer);

        // 1-1-4. Returns and Exchanges
        JsonObject jsonMaxZero = new JsonObject();
        jsonMaxZero.add("max", new JsonPrimitive(0));
        Advancement breakLaserBlade = Advancement.Builder.builder()
                .withParent(laserBlade)
                .withDisplay(ModItems.LB_CASING,
                        new TranslationTextComponent("advancements.tolaserblade.main.break_laser_blade.title"),
                        new TranslationTextComponent("advancements.tolaserblade.main.break_laser_blade.description"),
                        null,
                        FrameType.CHALLENGE, true, true, false)
                .withRewards(AdvancementRewards.Builder.experience(1000))
                .withCriterion("broke_laser_blade", ItemDurabilityTrigger.Instance.forItemDamage(ItemPredicate.Builder.create().item(ModItems.LASER_BLADE).build(), MinMaxBounds.IntBound.fromJson(jsonMaxZero)))
                .register(consumer, "tolaserblade:main/break_laser_blade");

        // 1-1-5. Life-time Support
        Advancement mending = registerEnchantmentAdvancement(laserBlade, Items.NETHER_STAR, FrameType.GOAL,
                ModItems.LASER_BLADE, Enchantments.MENDING, 1, consumer);
    }

    private Advancement registerItemAdvancement(Advancement parent, Item icon, FrameType frameType, Item requirement, Consumer<Advancement> consumer) {
        String name = requirement.getRegistryName().getPath();

        return Advancement.Builder.builder()
                .withParent(parent)
                .withDisplay(icon,
                        new TranslationTextComponent("advancements.tolaserblade.main." + name + ".title"),
                        new TranslationTextComponent("advancements.tolaserblade.main." + name + ".description"),
                        null,
                        frameType, true, true, false)
                .withCriterion("has_" + name,  InventoryChangeTrigger.Instance.forItems(requirement))
                .register(consumer, "tolaserblade:main/" + name);
    }

    private Advancement registerEnchantmentAdvancement(Advancement parent, Item icon, FrameType frameType, Item requirement, Enchantment enchantment, int level, Consumer<Advancement> consumer) {
        String name = requirement.getRegistryName().getPath() + "_" + enchantment.getRegistryName().getPath() + "_" + level;
        ItemPredicate itemPredicate = ItemPredicate.Builder.create()
                .item(requirement)
                .enchantment(new EnchantmentPredicate(enchantment, MinMaxBounds.IntBound.atLeast(level)))
                .build();

        return Advancement.Builder.builder()
                .withParent(parent)
                .withDisplay(icon,
                        new TranslationTextComponent("advancements.tolaserblade.main." + name + ".title"),
                        new TranslationTextComponent("advancements.tolaserblade.main." + name + ".description"),
                        null,
                        frameType, true, true, false)
                .withCriterion("has_" + name, InventoryChangeTrigger.Instance.forItems(itemPredicate))
                .register(consumer, "tolaserblade:main/" + name);
    }

    private Advancement registerAttackUpgradeAdvancement(Advancement parent, Item icon, FrameType frameType, Item requirement, int attackDamage, int baseDamage, Consumer<Advancement> consumer) {
        String name = requirement.getRegistryName().getPath() + "_attack_" + attackDamage;
        Advancement.Builder builder = Advancement.Builder.builder()
                .withParent(parent)
                .withDisplay(icon,
                        new TranslationTextComponent("advancements.tolaserblade.main." + name + ".title"),
                        new TranslationTextComponent("advancements.tolaserblade.main." + name + ".description"),
                        null,
                        frameType, true, true, false)
                .withRequirementsStrategy(IRequirementsStrategy.OR);

        for (int i = attackDamage - baseDamage; i <= LaserBladeItemBase.MOD_ATK_CLASS_5; i++) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putFloat(LaserBladeItemBase.KEY_ATK, (float)i);
            ItemPredicate itemPredicate = ItemPredicate.Builder.create()
                    .item(requirement)
                    .nbt(nbt)
                    .build();
            builder.withCriterion(requirement.getRegistryName().getPath() + "_attack_" + (i + baseDamage), InventoryChangeTrigger.Instance.forItems(itemPredicate));
        }

        return builder.register(consumer, "tolaserblade:main/" + name);
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path advancementPath = getPath(path, advancement);

                try {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), advancementPath);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", advancementPath, ioexception);
                }

            }
        };

        registerAdvancements(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "ToLaserBlade Advancements";
    }
}
