package com.github.iunius118.tolaserblade.item.crafting.display;

import com.github.iunius118.tolaserblade.item.component.BlendModes;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import com.github.iunius118.tolaserblade.item.crafting.ColoringRecipe;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModSlotDisplay {

    public record ColoringSlotDemo(SlotDisplay target, List<ColoringRecipe.PartColor> partColors, int part)
            implements SlotDisplay {

        @Override
        public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
            if (!(builder instanceof DisplayContentsFactory.ForStacks<T> stacks)) {
                return Stream.empty();
            }

            List<Boolean> flags = new ArrayList<>();
            List<Integer> colors = new ArrayList<>();
            // Colors for items that do not support blend modes component
            List<Integer> colors2 = new ArrayList<>();
            List<Boolean> modes = new ArrayList<>();

            for (int i = 0; i < partColors.size(); i++) {
                ColoringRecipe.PartColor partColor = partColors.get(i);

                if (i == part) {
                    flags.add(true);
                    int newColor = partColor.color();
                    colors.add(newColor);

                    if (i > 0) {
                        if (partColor.blendMode() == ColoringRecipe.PartColor.BlendMode.SUBTRACTIVE) {
                            modes.add(true);
                            // Add inverted color
                            newColor = (~(newColor & 0xFFFFFF)) | (newColor & 0xFF000000);
                        } else {
                            modes.add(false);
                        }
                    }

                    colors2.add(newColor);
                } else {
                    flags.add(false);
                    colors.add(-1);
                    colors2.add(-1);

                    if (i > 0) {
                        modes.add(false);
                    }
                }
            }

            var customModelData = new CustomModelData(List.of(), flags, List.of(), colors);
            var customModelData2 = new CustomModelData(List.of(), flags, List.of(), colors2);
            var blendModes = new BlendModes(modes);
            List<ItemStack> targetItems = target.resolveForStacks(context);
            return targetItems.stream()
                    .filter(Predicate.not(ItemStack::isEmpty))
                    .map(ItemStack::copy)
                    .peek(s -> {
                        if (s.is(ModTags.Items.LASER_BLADES)) {
                            s.set(DataComponents.CUSTOM_MODEL_DATA, customModelData);
                            s.set(ModDataComponents.BLEND_MODES, blendModes);
                        } else {
                            s.set(DataComponents.CUSTOM_MODEL_DATA, customModelData2);
                        }
                    })
                    .map(stacks::forStack);
        }

        @Override
        public SlotDisplay.Type<SlotDisplay.DyedSlotDemo> type() {
            return null;
        }
    }

    public record BlendingSlotDemo(SlotDisplay target, int part)
            implements SlotDisplay {

        @Override
        public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
            if (!(builder instanceof DisplayContentsFactory.ForStacks<T> stacks)) {
                return Stream.empty();
            }

            List<Boolean> modes = IntStream.rangeClosed(0, part)
                    .mapToObj(i -> i == part)
                    .toList();
            List<ItemStack> targetItems = target.resolveForStacks(context);
            return targetItems.stream()
                    .filter(Predicate.not(ItemStack::isEmpty))
                    .map(ItemStack::copy)
                    .peek(s -> s.set(ModDataComponents.BLEND_MODES, new BlendModes(modes)))
                    .map(stacks::forStack);
        }

        @Override
        public Type<? extends SlotDisplay> type() {
            return null;
        }
    }

    public record EnchantmentSlotDemo(SlotDisplay target, Holder<Enchantment> enchantment)
            implements SlotDisplay {

        @Override
        public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
            if (!(builder instanceof DisplayContentsFactory.ForStacks<T> stacks)) {
                return Stream.empty();
            }

            List<ItemStack> targetItems = target.resolveForStacks(context);
            return targetItems.stream()
                    .filter(Predicate.not(ItemStack::isEmpty))
                    .map(ItemStack::copy)
                    .peek(s -> s.enchant(enchantment, 1))
                    .map(stacks::forStack);
        }

        @Override
        public Type<? extends SlotDisplay> type() {
            return null;
        }
    }

    public record RemodelSlotDemo(SlotDisplay target, int modelType)
            implements SlotDisplay {

        @Override
        public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
            if (!(builder instanceof DisplayContentsFactory.ForStacks<T> stacks)) {
                return Stream.empty();
            }

            List<ItemStack> targetItems = target.resolveForStacks(context);
            return targetItems.stream()
                    .filter(Predicate.not(ItemStack::isEmpty))
                    .map(ItemStack::copy)
                    .peek(s -> s.set(ModDataComponents.MODEL, modelType))
                    .map(stacks::forStack);
        }

        @Override
        public Type<? extends SlotDisplay> type() {
            return null;
        }
    }

    public record RepairSlotDemo(SlotDisplay target, float remainingRate)
            implements SlotDisplay {

        @Override
        public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
            if (!(builder instanceof DisplayContentsFactory.ForStacks<T> stacks)) {
                return Stream.empty();
            }

            List<ItemStack> targetItems = target.resolveForStacks(context);
            return targetItems.stream()
                    .filter(Predicate.not(ItemStack::isEmpty))
                    .map(ItemStack::copy)
                    .peek(s -> {
                        if (s.isDamageableItem()) {
                            s.setDamageValue((int) (s.getMaxDamage() * (1 - remainingRate)));
                        }
                    })
                    .map(stacks::forStack);
        }

        @Override
        public Type<? extends SlotDisplay> type() {
            return null;
        }
    }
}
