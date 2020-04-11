package com.github.iunius118.tolaserblade.client.integration.jei;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.upgrade.LaserBladeUpgrade;
import com.github.iunius118.tolaserblade.item.upgrade.UpgradeResult;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@JeiPlugin
public class JEIToLaserBladePlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(ToLaserBlade.MOD_ID, "main");


    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        registry.addRecipes(getUpgradeRecipes(registry.getVanillaRecipeFactory()), VanillaRecipeCategoryUid.ANVIL);
    }

    private List<Object> getUpgradeRecipes(IVanillaRecipeFactory factory) {
        List<Object> list = new ArrayList<>();

        for (Triple<Tag<Item>, LaserBladeUpgrade.Type, Function<ItemStack, UpgradeResult>> tag : ModItemTags.getTags()) {
            Object recipe = getUpdateAnvilRecipe(factory, tag.getLeft(), tag.getRight());

            if (recipe != null) {
                list.add(recipe);
            }
        }

        ItemStack tintedLB = new ItemStack(ModItems.LASER_BLADE);
        ModItems.LASER_BLADE.setBladeOuterColor(tintedLB, 0xFF333333);
        ModItems.LASER_BLADE.setBladeInnerColor(tintedLB, 0xFF666666);
        ModItems.LASER_BLADE.setGripColor(tintedLB, 0xFF666666);
        List<ItemStack> right;
        ItemStack output;

        // + StainedGlass -> BladeOuterColor
        right = getUpgradeRecipes(Tags.Items.STAINED_GLASS);

        if (right.size() > 0) {
            output = tintedLB.copy();
            ModItems.LASER_BLADE.setBladeOuterColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
            list.add(factory.createAnvilRecipe(tintedLB.copy(), right, Collections.singletonList(output)));
        }

        // + StainedGlassPane -> BladeInnerColor
        right = getUpgradeRecipes(Tags.Items.STAINED_GLASS_PANES);

        if (right.size() > 0) {
            output = tintedLB.copy();
            ModItems.LASER_BLADE.setBladeInnerColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
            list.add(factory.createAnvilRecipe(tintedLB.copy(), right, Collections.singletonList(output)));
        }


        // + Carpet -> GripColor
        right = getUpgradeRecipes(ItemTags.CARPETS);

        if (right.size() > 0) {
            output = tintedLB.copy();
            ModItems.LASER_BLADE.setGripColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
            list.add(factory.createAnvilRecipe(tintedLB.copy(), right, Collections.singletonList(output)));
        }

        return list;
    }

    private Object getUpdateAnvilRecipe(IVanillaRecipeFactory factory, Tag<Item> itemTag, Function<ItemStack, UpgradeResult> upgradeFunc) {
        List<ItemStack> left;
        List<ItemStack> right = getUpgradeRecipes(itemTag);
        ItemStack output = new ItemStack(ModItems.LASER_BLADE);
        UpgradeResult result;

        if (right.isEmpty()) {
            return null;
        }

        if (itemTag == ModItemTags.EFFICIENCY_REMOVER) {
            ItemStack efficiencyLB;
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            int maxLevel = Enchantments.EFFICIENCY.getMaxLevel();

            for (int level = Enchantments.EFFICIENCY.getMinLevel(); level <= maxLevel; level ++) {
                efficiencyLB = new ItemStack(ModItems.LASER_BLADE);
                efficiencyLB.addEnchantment(Enchantments.EFFICIENCY, level);
                builder.add(efficiencyLB);
            }

            left = builder.build();
            output = upgradeFunc.apply(new ItemStack(ModItems.LASER_BLADE)).getItemStack();

        } else if (itemTag == ModItemTags.CASING_REPAIR) {
            ItemStack damagedLB = new ItemStack(ModItems.LASER_BLADE);
            damagedLB.setDamage(ModItems.LASER_BLADE.getMaxDamage(damagedLB) - 1);
            left = ImmutableList.of(damagedLB, new ItemStack(ModItems.LB_BROKEN));

        } else {
            left = Collections.singletonList(new ItemStack(ModItems.LASER_BLADE));
            output = upgradeFunc.apply(new ItemStack(ModItems.LASER_BLADE)).getItemStack();
        }

        return factory.createAnvilRecipe(left, right, Collections.singletonList(output));
    }

    private List<ItemStack> getUpgradeRecipes(Tag<Item> itemTag) {
        List<ItemStack> list = new ArrayList<>();
        itemTag.getAllElements().forEach(item -> list.add(new ItemStack(item)));
        return list;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
