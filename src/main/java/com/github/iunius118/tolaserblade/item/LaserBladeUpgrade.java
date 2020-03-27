package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.function.ToIntFunction;

public class LaserBladeUpgrade {
    public static void onAnvilUpdate(AnvilUpdateEvent event, LaserBladeItemBase laserBlade) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        Item rightItem = right.getItem();

        if (rightItem instanceof LaserBladeItemBase) {
            // Cannot mix Laser Blades
            event.setCanceled(true);
        }

        // Upgrade or Repair
        List<Triple<Tag<Item>, Type, ToIntFunction<ItemStack>>> tags = ModItemTags.getTags();

        for (Triple<Tag<Item>, Type, ToIntFunction<ItemStack>> tag : tags) {
            if (tag.getLeft().contains(rightItem) && laserBlade.canUpgrade(tag.getMiddle())) {
                ItemStack result;
                int cost;

                if (left.getItem() == ModItems.LB_BROKEN && tag.getMiddle() == Type.REPAIR) {
                    // Repair Broken Laser Blade
                    result = new ItemStack(ModItems.LASER_BLADE);
                    result.setTag(left.getOrCreateTag().copy());
                    result.setDamage(0);
                    cost = 1;

                } else {
                    // Upgrade Laser Blade or its parts
                    result = left.copy();
                    cost = tag.getRight().applyAsInt(result);
                }

                cost += changeDisplayName(result, event.getName());

                if (cost > 0) {
                    event.setCost(cost);
                    event.setMaterialCost(1);
                    event.setOutput(result);
                }

                return;
            }
        }

        // Tint
        if (rightItem instanceof BlockItem) {
            Block block = ((BlockItem)rightItem).getBlock();
            ItemStack result = ItemStack.EMPTY;
            int cost = 0;

            if (block instanceof StainedGlassBlock && laserBlade.canUpgrade(Type.MEDIUM)) {
                // Medium + StainedGlass -> BladeOuterColor
                int newColor = ModItems.LB_MEDIUM.getBladeColorFromTintIndex(((StainedGlassBlock)block).getColor().getId(), true);
                int oldColor = ModItems.LB_MEDIUM.getBladeOuterColor(left).getLeft();

                if (newColor != oldColor) {
                    result = left.copy();
                    ModItems.LB_MEDIUM.setBladeOuterColor(result, newColor);
                    cost = 1;
                }

            } else if (block instanceof StainedGlassPaneBlock && laserBlade.canUpgrade(Type.EMITTER)) {
                // Emitter + StainedGlassPane -> BladeInnerColor
                int newColor = ModItems.LB_EMITTER.getBladeColorFromTintIndex(((StainedGlassPaneBlock)block).getColor().getId(), false);
                int oldColor = ModItems.LB_EMITTER.getBladeInnerColor(left).getLeft();

                if (newColor != oldColor) {
                    result = left.copy();
                    ModItems.LB_MEDIUM.setBladeInnerColor(result, newColor);
                    cost = 1;
                }

            } else if (block instanceof CarpetBlock && laserBlade.canUpgrade(Type.CASING)) {
                // Casing + Carpet -> GripColor
                int newColor = ModItems.LB_CASING.getGripColorFromTintIndex(((CarpetBlock)block).getColor().getId());
                int oldColor = ModItems.LB_CASING.getGripColor(left);

                if (newColor != oldColor) {
                    result = left.copy();
                    ModItems.LB_CASING.setGripColor(result, newColor);
                    cost = 1;
                }
            }

            cost += changeDisplayName(result, event.getName());

            if (cost > 0) {
                event.setCost(cost);
                event.setMaterialCost(1);
                event.setOutput(result);
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
