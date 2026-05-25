package com.github.iunius118.tolaserblade.item;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ModToolMaterials {
    private static final int LB_MAX_USES = 32000;
    private static final float LB_DEFAULT_SPEED = 12.0F;
    private static final float LB_DAMAGE_BONUS = 3.0F;
    private static final float LB_FP_DAMAGE_BONUS = 4.0F;
    private static final int LB_ENCHANTMENT_VALUE = 15;

    public static final ToolMaterial LASER_BLADE = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            LB_MAX_USES, LB_DEFAULT_SPEED, LB_DAMAGE_BONUS, LB_ENCHANTMENT_VALUE, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial LASER_BLADE_FP = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            LB_MAX_USES, LB_DEFAULT_SPEED, LB_FP_DAMAGE_BONUS, LB_ENCHANTMENT_VALUE, ItemTags.NETHERITE_TOOL_MATERIALS);

    public static Item.Properties applyLaserBladeProperties(Item.Properties properties, boolean fireResistant,
                                                            float attackDamageBaseline, float attackSpeedBaseline,
                                                            float disableBlockingSeconds) {
        ToolMaterial material = LASER_BLADE;

        if (fireResistant) {
            properties.fireResistant();
            material = LASER_BLADE_FP;
        }

        Function<TagKey<Block>, HolderSet<Block>> blocks = tag ->
                BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(tag);
        return properties
                .durability(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(DataComponents.TOOL,
                        new Tool(List.of(
                                // Define minable blocks and their corresponding mining speeds
                                Tool.Rule.deniesDrops(blocks.apply(material.incorrectBlocksForDrops())),
                                Tool.Rule.overrideSpeed(blocks.apply(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE)
                        ), material.speed(), 1, true))
                .attributes(createAttributeModifiers(material, attackDamageBaseline, attackSpeedBaseline))
                .component(DataComponents.WEAPON, new Weapon(1, disableBlockingSeconds))
                .delayedComponent(DataComponents.BLOCKS_ATTACKS, createBlocksAttacks(fireResistant));
    }

    private static ItemAttributeModifiers createAttributeModifiers(ToolMaterial material,
                                                                   float attackDamageBaseline,
                                                                   float attackSpeedBaseline) {
        // Define item attribute modifiers for attack damage and attack speed
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                attackDamageBaseline + material.attackDamageBonus(),
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                attackSpeedBaseline,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    private static DataComponentInitializers.SingleComponentInitializer<BlocksAttacks> createBlocksAttacks(
            boolean fireResistant) {
        // Define ability to block attacks
        return context -> new BlocksAttacks(
                0.25F,
                1.0F,
                List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                Optional.of(SoundEvents.SHIELD_BLOCK),
                Optional.of(SoundEvents.SHIELD_BREAK)
        );
    }
}
