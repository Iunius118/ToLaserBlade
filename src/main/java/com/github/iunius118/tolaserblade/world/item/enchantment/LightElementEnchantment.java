package com.github.iunius118.tolaserblade.world.item.enchantment;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModEntityTypeTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class LightElementEnchantment  {
    public static final ResourceLocation ID = ToLaserBlade.makeId("light_element");
    private static final int MAX_LEVEL = 10;
    private static final float BASE_DAMAGE_PER_LEVEL = 0.4F;
    private static final float SPECIAL_DAMAGE_PER_LEVEL = 2.4F;
    private static final TagKey<EntityType<?>> TARGETS = ModEntityTypeTags.SENSITIVE_TO_LIGHT_ELEMENT;

    public static Enchantment get(HolderLookup.Provider provider) {
        return get(provider.lookupOrThrow(Registries.ITEM), provider.lookupOrThrow(Registries.ENCHANTMENT));
    }

    public static Enchantment get(HolderGetter<Item> ItemHolderGetter, HolderGetter<Enchantment> EnchantmentHolderGetter) {
        return Enchantment
                .enchantment(
                        Enchantment.definition(
                                ItemHolderGetter.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                ItemHolderGetter.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                5,
                                MAX_LEVEL,
                                Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(25, 8), 2,
                                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(EnchantmentHolderGetter.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(
                        EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(BASE_DAMAGE_PER_LEVEL)))
                .withEffect(
                        EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(SPECIAL_DAMAGE_PER_LEVEL)),
                        LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(TARGETS))
                        )
                )
                .build(ID);
    }

    private LightElementEnchantment() {}
}
