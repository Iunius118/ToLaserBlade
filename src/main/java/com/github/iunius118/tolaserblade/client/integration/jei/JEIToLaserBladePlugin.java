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
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
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
        registry.addRecipes(getColoringRecipes(registry.getVanillaRecipeFactory()), VanillaRecipeCategoryUid.ANVIL);
    }

    private List<Object> getUpgradeRecipes(IVanillaRecipeFactory factory) {
        List<Object> list = new ArrayList<>();

        for (Triple<ITag.INamedTag<Item>, LaserBladeUpgrade.Type, Function<ItemStack, UpgradeResult>> tag : ModItemTags.getTags()) {
            Object recipe = getUpdateAnvilRecipe(factory, ModItems.LASER_BLADE, ModItems.LB_BROKEN, tag.getLeft(), tag.getRight());
            Object recipeFP = getUpdateAnvilRecipe(factory, ModItems.LASER_BLADE_FP, ModItems.LB_BROKEN_FP, tag.getLeft(), tag.getRight());

            if (recipe != null) list.add(recipe);
            if (recipeFP != null) list.add(recipeFP);
        }

        return list;
    }

    private List<Object> getColoringRecipes(IVanillaRecipeFactory factory) {
        List<Object> list = new ArrayList<>();

        ItemStack tintedLB = new ItemStack(ModItems.LASER_BLADE);
        ModItems.LASER_BLADE.setBladeOuterColor(tintedLB, 0xFF333333);
        ModItems.LASER_BLADE.setBladeInnerColor(tintedLB, 0xFF666666);
        ModItems.LASER_BLADE.setGripColor(tintedLB, 0xFF666666);

        ItemStack tintedLBFP = new ItemStack(ModItems.LASER_BLADE_FP);
        ModItems.LASER_BLADE_FP.setBladeOuterColor(tintedLBFP, 0xFF333333);
        ModItems.LASER_BLADE_FP.setBladeInnerColor(tintedLBFP, 0xFF666666);
        ModItems.LASER_BLADE_FP.setGripColor(tintedLBFP, 0xFF666666);

        List<ItemStack> laserBlades = ImmutableList.of(tintedLB, tintedLBFP);
        List<ItemStack> stainedGlass = getUpgradeRecipes(Tags.Items.STAINED_GLASS);
        List<ItemStack> stainedGlassPanes = getUpgradeRecipes(Tags.Items.STAINED_GLASS_PANES);
        List<ItemStack> carpets = getUpgradeRecipes(ItemTags.CARPETS);
        ItemStack output;

        for (ItemStack laserBlade : laserBlades) {
            // + StainedGlass -> BladeOuterColor
            if (!stainedGlass.isEmpty()) {
                output = laserBlade.copy();
                ModItems.LASER_BLADE.setBladeOuterColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
                list.add(factory.createAnvilRecipe(laserBlade.copy(), stainedGlass, Collections.singletonList(output)));
            }

            // + StainedGlassPane -> BladeInnerColor
            if (!stainedGlassPanes.isEmpty()) {
                output = laserBlade.copy();
                ModItems.LASER_BLADE.setBladeInnerColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
                list.add(factory.createAnvilRecipe(laserBlade.copy(), stainedGlassPanes, Collections.singletonList(output)));
            }


            // + Carpet -> GripColor
            if (!carpets.isEmpty()) {
                output = laserBlade.copy();
                ModItems.LASER_BLADE.setGripColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
                list.add(factory.createAnvilRecipe(laserBlade.copy(), carpets, Collections.singletonList(output)));
            }
        }

        return list;
    }

    private Object getUpdateAnvilRecipe(IVanillaRecipeFactory factory, Item laserBlade, Item brokenLaserBlade, ITag.INamedTag<Item> itemTag, Function<ItemStack, UpgradeResult> upgradeFunc) {
        List<ItemStack> left;
        List<ItemStack> right = getUpgradeRecipes(itemTag);
        ItemStack output = new ItemStack(laserBlade);

        if (right.isEmpty()) {
            return null;
        }

        if (itemTag == ModItemTags.EFFICIENCY_REMOVER) {
            ItemStack efficiencyLB;
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            int maxLevel = Enchantments.EFFICIENCY.getMaxLevel();

            for (int level = Enchantments.EFFICIENCY.getMinLevel(); level <= maxLevel; level ++) {
                efficiencyLB = new ItemStack(laserBlade);
                efficiencyLB.addEnchantment(Enchantments.EFFICIENCY, level);
                builder.add(efficiencyLB);
            }

            left = builder.build();
            output = upgradeFunc.apply(new ItemStack(laserBlade)).getItemStack();

        } else if (itemTag == ModItemTags.CASING_REPAIR) {
            ItemStack damagedLB = new ItemStack(laserBlade);
            damagedLB.setDamage(laserBlade.getMaxDamage(damagedLB) - 1);
            left = ImmutableList.of(damagedLB, new ItemStack(brokenLaserBlade));

        } else if (itemTag == ModItemTags.FIREPROOF_UPGRADE && laserBlade == ModItems.LASER_BLADE_FP) {
            return null;

        } else {
            left = Collections.singletonList(new ItemStack(laserBlade));
            output = upgradeFunc.apply(new ItemStack(laserBlade)).getItemStack();

        }

        return factory.createAnvilRecipe(left, right, Collections.singletonList(output));
    }

    private List<ItemStack> getUpgradeRecipes(ITag.INamedTag<Item> itemTag) {
        List<ItemStack> list = new ArrayList<>();

        try {
            itemTag.getAllElements().forEach(item -> list.add(new ItemStack(item)));
        } catch (IllegalStateException e) {
            ToLaserBlade.LOGGER.debug("Caught java.lang.IllegalStateException: {}\n\tat com.github.iunius118.tolaserblade.client.integration.jei.JEIToLaserBladePlugin.getUpgradeRecipes", e.getMessage());
        }

        return list;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
