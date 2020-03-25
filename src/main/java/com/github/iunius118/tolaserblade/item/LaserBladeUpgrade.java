package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
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
        List<Triple<Tag<Item>, Type, ToIntFunction<ItemStack>>> tags = ModItemTags.getTags();

        for (Triple<Tag<Item>, Type, ToIntFunction<ItemStack>> tag : tags) {
            if (tag.getLeft().contains(right.getItem()) && laserBlade.canUpgrade(tag.getMiddle())) {
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
