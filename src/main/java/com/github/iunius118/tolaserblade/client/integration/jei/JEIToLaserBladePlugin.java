package com.github.iunius118.tolaserblade.client.integration.jei;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.LaserBladeUpgrade;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
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
import java.util.function.ToIntFunction;

@JeiPlugin
public class JEIToLaserBladePlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(ToLaserBlade.MOD_ID, "main");


    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        registry.addRecipes(getUpgradeRecipes(registry.getVanillaRecipeFactory()), VanillaRecipeCategoryUid.ANVIL);
    }

    private List<Object> getUpgradeRecipes(IVanillaRecipeFactory factory) {
        List<Object> list = new ArrayList<>();

        for (Triple<Tag<Item>, LaserBladeUpgrade.Type, ToIntFunction<ItemStack>> tag : ModItemTags.getTags()) {
            list.add(getUpdateAnvilRecipe(factory, tag.getLeft(), tag.getRight()));
        }

        ItemStack inputForTint = new ItemStack(ModItems.LASER_BLADE);
        ModItems.LASER_BLADE.setBladeOuterColor(inputForTint, 0xFF333333);
        ModItems.LASER_BLADE.setBladeInnerColor(inputForTint, 0xFF666666);
        ModItems.LASER_BLADE.setGripColor(inputForTint, 0xFF666666);

        // + StainedGlass -> BladeOuterColor
        ItemStack output = inputForTint.copy();
        ModItems.LASER_BLADE.setBladeOuterColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
        list.add(factory.createAnvilRecipe(inputForTint.copy(), getUpgradeRecipes(Tags.Items.STAINED_GLASS), Collections.singletonList(output)));

        // + StainedGlassPane -> BladeInnerColor
        output = inputForTint.copy();
        ModItems.LASER_BLADE.setBladeInnerColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
        list.add(factory.createAnvilRecipe(inputForTint.copy(), getUpgradeRecipes(Tags.Items.STAINED_GLASS_PANES), Collections.singletonList(output)));

        // + Carpet -> GripColor
        output = inputForTint.copy();
        ModItems.LASER_BLADE.setGripColor(output, LaserBladeItemBase.LBColor.SPECIAL_GAMING.getBladeColor());
        list.add(factory.createAnvilRecipe(inputForTint.copy(), getUpgradeRecipes(ItemTags.CARPETS), Collections.singletonList(output)));

        return list;
    }

    private Object getUpdateAnvilRecipe(IVanillaRecipeFactory factory, Tag<Item> itemTag, ToIntFunction<ItemStack> upgradeFunc) {
        ItemStack left;
        List<ItemStack> right = getUpgradeRecipes(itemTag);
        ItemStack output;

        if (itemTag == ModItemTags.EFFICIENCY_REMOVER) {
            left = new ItemStack(ModItems.LASER_BLADE);
            left.addEnchantment(Enchantments.EFFICIENCY, 1);
            output = left.copy();
            upgradeFunc.applyAsInt(output);

        } else if (itemTag == ModItemTags.CASING_REPAIR) {
            left = new ItemStack(ModItems.LB_BROKEN);
            output = new ItemStack(ModItems.LASER_BLADE);

        } else {
            left = new ItemStack(ModItems.LASER_BLADE);
            output = left.copy();
            upgradeFunc.applyAsInt(output);
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
