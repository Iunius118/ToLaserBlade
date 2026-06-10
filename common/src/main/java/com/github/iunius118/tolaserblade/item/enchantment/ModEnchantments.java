package com.github.iunius118.tolaserblade.item.enchantment;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> LASER_BLADE =
            ResourceKey.create(Registries.ENCHANTMENT, CommonClass.modLocation("laser_blade"));
    public static final ResourceKey<Enchantment> LIGHT_ELEMENT =
            ResourceKey.create(Registries.ENCHANTMENT, CommonClass.modLocation("light_element"));
    public static final ResourceKey<Enchantment> REPULSIVE_FORCE =
            ResourceKey.create(Registries.ENCHANTMENT, CommonClass.modLocation("repulsive_force"));


    public static void bootstrap(BootstrapContext<Enchantment> ctx) {
        HolderGetter<Item> items = ctx.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = ctx.lookup(Registries.ENCHANTMENT);
        HolderGetter<EntityType<?>> entityTypes = ctx.lookup(Registries.ENTITY_TYPE);

        // Define and register enchantments
        ctx.register(LASER_BLADE,
                Enchantment
                        .enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ModTags.Items.LASER_BLADE_ENCHANTABLE),
                                        10,
                                        5,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(21, 11),
                                        1,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        CommonClass.modLocation("laser_blade").withSuffix("/attack_damage"),
                                        Attributes.ATTACK_DAMAGE,
                                        LevelBasedValue.perLevel(2.4F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        CommonClass.modLocation("laser_blade").withSuffix("/attack_speed"),
                                        Attributes.ATTACK_SPEED,
                                        LevelBasedValue.perLevel(0.2F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .build(LASER_BLADE.identifier())
        );
        ctx.register(LIGHT_ELEMENT,
                Enchantment
                        .enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ModTags.Items.LASER_BLADE_ENCHANTABLE),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(25, 8),
                                        2,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(4F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes,
                                                ModTags.EntityTypes.SENSITIVE_TO_LASER_BLADE))
                                )
                        )
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .build(LIGHT_ELEMENT.identifier())
        );
    }

    public static void bootstrapOptional(BootstrapContext<Enchantment> ctx) {
        HolderGetter<Item> items = ctx.lookup(Registries.ITEM);

        // Define and register optional enchantments
        ctx.register(REPULSIVE_FORCE,
                Enchantment
                        .enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ModTags.Items.LASER_BLADE_ENCHANTABLE),
                                        5,
                                        3,
                                        Enchantment.dynamicCost(5, 20),
                                        Enchantment.dynamicCost(55, 20),
                                        2,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.KNOCKBACK,
                                new AddValue(LevelBasedValue.perLevel(0F, 1F))
                        )
                        .build(REPULSIVE_FORCE.identifier())
        );
    }
}
