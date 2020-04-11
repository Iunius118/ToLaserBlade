package com.github.iunius118.tolaserblade.item.upgrade;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LaserBladeUpgrade {
    public static void onAnvilRepair(AnvilRepairEvent event) {
        ItemStack right = event.getIngredientInput();

        if (right.isEmpty()) {
            ItemStack output = event.getItemResult();
            String name = output.getDisplayName().getString();

            // Use GIFT code
            if ("GIFT".equals(name) || /* "おたから" */ "\u304A\u305F\u304B\u3089".equals(name)) {
                boolean hasUpgraded = false;
                float atk = ModItems.LASER_BLADE.getLaserBladeATK(output);
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(output);
                int lightElementLevel = enchantments.getOrDefault(ModEnchantments.LIGHT_ELEMENT, 0);
                int smiteLevel = enchantments.getOrDefault(Enchantments.SMITE, 0);

                boolean isLightElementLessThan5 = lightElementLevel < LaserBladeItemBase.LVL_LIGHT_ELEMENT_5;

                if (ModItems.LASER_BLADE.getLaserBladeATK(output) < LaserBladeItemBase.MOD_ATK_CLASS_3) {
                    // Get attack damage 10
                    ModItems.LASER_BLADE.setLaserBladeATK(output, LaserBladeItemBase.MOD_ATK_CLASS_3);
                    hasUpgraded = true;
                }

                if (lightElementLevel < LaserBladeItemBase.LVL_LIGHT_ELEMENT_5) {
                    // Get Light Element V
                    lightElementLevel = LaserBladeItemBase.LVL_LIGHT_ELEMENT_5;
                    hasUpgraded = true;
                }

                if (lightElementLevel < ModEnchantments.LIGHT_ELEMENT.getMaxLevel() && lightElementLevel < smiteLevel) {
                    // Set Light Element from Smite level
                    lightElementLevel = Math.min(smiteLevel, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
                    hasUpgraded = true;
                }

                // Apply Enchantments to Laser Blade
                enchantments.put(ModEnchantments.LIGHT_ELEMENT, lightElementLevel);
                Map<Enchantment, Integer> newEnchantments = Maps.newLinkedHashMap();
                enchantments.forEach((e, lvl) -> {
                    if (e.isCompatibleWith(ModEnchantments.LIGHT_ELEMENT) || e.equals(ModEnchantments.LIGHT_ELEMENT))
                        newEnchantments.put(e, lvl);
                });
                EnchantmentHelper.setEnchantments(newEnchantments, output);

                if (hasUpgraded) {
                    output.clearCustomName();
                    ModItems.LASER_BLADE.setGripColor(output, LaserBladeItemBase.LBColor.BROWN.getGripColor());
                    ModItems.LASER_BLADE.setBladeInnerColor(output, LaserBladeItemBase.LBColor.WHITE.getBladeColor());
                    ModItems.LASER_BLADE.setBladeOuterColor(output, LaserBladeItemBase.LBColor.LIME.getBladeColor());
                    ModItems.LASER_BLADE.setBladeInnerSubColorFlag(output, false);
                    ModItems.LASER_BLADE.setBladeOuterSubColorFlag(output, false);
                }
            }
        }
    }

    public static void onAnvilUpdate(AnvilUpdateEvent event, LaserBladeItemBase laserBlade) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        Item rightItem = right.getItem();

        if (rightItem instanceof LaserBladeItemBase) {
            // Cannot mix Laser Blades
            event.setCanceled(true);
        }

        // Upgrade or Repair
        List<Triple<Tag<Item>, Type, Function<ItemStack, UpgradeResult>>> tags = ModItemTags.getTags();

        for (Triple<Tag<Item>, Type, Function<ItemStack, UpgradeResult>> tag : tags) {
            if (tag.getLeft().contains(rightItem) && laserBlade.canUpgrade(tag.getMiddle())) {
                // Upgrade Laser Blade or its parts
                UpgradeResult result = tag.getRight().apply(left.copy());
                ItemStack output = result.getItemStack();
                int cost = result.getCost();

                cost += changeDisplayName(output, event.getName());

                if (cost > 0) {
                    event.setCost(cost);
                    event.setMaterialCost(1);
                    event.setOutput(output);
                }

                return;
            }
        }

        // Tint
        if (rightItem instanceof BlockItem) {
            Block block = ((BlockItem)rightItem).getBlock();
            ItemStack output = ItemStack.EMPTY;
            int cost = 0;

            if (block instanceof StainedGlassBlock && laserBlade.canUpgrade(Type.MEDIUM)) {
                // Medium + StainedGlass -> BladeOuterColor
                int newColor = ModItems.LB_MEDIUM.getBladeColorFromTintIndex(((StainedGlassBlock)block).getColor().getId(), true);
                int oldColor = ModItems.LB_MEDIUM.getBladeOuterColor(left).getLeft();

                if (newColor != oldColor) {
                    output = left.copy();
                    ModItems.LB_MEDIUM.setBladeOuterColor(output, newColor);
                    cost = 1;
                }

            } else if (block instanceof StainedGlassPaneBlock && laserBlade.canUpgrade(Type.EMITTER)) {
                // Emitter + StainedGlassPane -> BladeInnerColor
                int newColor = ModItems.LB_EMITTER.getBladeColorFromTintIndex(((StainedGlassPaneBlock)block).getColor().getId(), false);
                int oldColor = ModItems.LB_EMITTER.getBladeInnerColor(left).getLeft();

                if (newColor != oldColor) {
                    output = left.copy();
                    ModItems.LB_MEDIUM.setBladeInnerColor(output, newColor);
                    cost = 1;
                }

            } else if (block instanceof CarpetBlock && laserBlade.canUpgrade(Type.CASING)) {
                // Casing + Carpet -> GripColor
                int newColor = ModItems.LB_CASING.getGripColorFromTintIndex(((CarpetBlock)block).getColor().getId());
                int oldColor = ModItems.LB_CASING.getGripColor(left);

                if (newColor != oldColor) {
                    output = left.copy();
                    ModItems.LB_CASING.setGripColor(output, newColor);
                    cost = 1;
                }
            }

            cost += changeDisplayName(output, event.getName());

            if (cost > 0) {
                event.setCost(cost);
                event.setMaterialCost(1);
                event.setOutput(output);
            }
        } else if (rightItem == Items.PAPER && right.hasDisplayName()) {
            String code = right.getDisplayName().getFormattedText();
            ItemStack output = ItemStack.EMPTY;
            int cost = 0;

            if ("FATE".equals(code) && laserBlade.canUpgrade(Type.MEDIUM)) {
                // Invert isBladeOuterSubColor
                output = left.copy();
                boolean flag = ModItems.LASER_BLADE.getBladeOuterColor(output).getRight();
                ModItems.LASER_BLADE.setBladeOuterSubColorFlag(output, !flag);
                cost = 1;

            } else if ("OGRE".equals(code) && laserBlade.canUpgrade(Type.EMITTER)) {
                // Invert isBladeInnerSubColor
                output = left.copy();
                boolean flag = ModItems.LASER_BLADE.getBladeInnerColor(output).getRight();
                ModItems.LASER_BLADE.setBladeInnerSubColorFlag(output, !flag);
                cost = 1;
            }

            cost += changeDisplayName(output, event.getName());

            if (cost > 0) {
                event.setCost(cost);
                event.setMaterialCost(1);
                event.setOutput(output);
            }
        }
    }

    private static int changeDisplayName(ItemStack stack, String name) {   // Returns renaming cost
        if (StringUtils.isBlank(name)) {
            if (stack.hasDisplayName()) {
                stack.clearCustomName();
                return 1;
            }

        } else {
            if (!name.equals(stack.getDisplayName().getString())) {
                stack.setDisplayName(new StringTextComponent(name));
                return 1;
            }
        }

        return 0;
    }

    public enum Type {
        BATTERY,
        MEDIUM,
        EMITTER,
        CASING,
        REPAIR,
        OTHER
    }
}
