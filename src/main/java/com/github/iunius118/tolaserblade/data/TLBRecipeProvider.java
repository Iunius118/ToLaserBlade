package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class TLBRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public TLBRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // DX Laser Blade
        ShapedRecipeBuilder.shapedRecipe(ModItems.DX_LASER_BLADE)
                .patternLine("R")
                .patternLine("R")
                .patternLine("s")
                .key('R', Items.REDSTONE_TORCH)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.DX_LASER_BLADE.getRegistryName());

        // Laser Blade with Light Element I
        ShapedRecipeBuilder.shapedRecipe(ModItems.LASER_BLADE)
                .patternLine("Gid")
                .patternLine("idi")
                .patternLine("riG")
                .key('G', Tags.Items.GLASS_COLORLESS)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.LASER_BLADE.getRegistryName());

        // Laser Blade with Light Element II
        ShapedRecipeBuilder.shapedRecipe(ModItems.LASER_BLADE)
                .patternLine("gid")
                .patternLine("idi")
                .patternLine("rig")
                .key('g', Tags.Items.DUSTS_GLOWSTONE)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.LASER_BLADE.getRegistryName() + "_1");

        // Laser Blade from parts
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.LASER_BLADE)
                .addIngredient(ModItems.LB_BATTERY)
                .addIngredient(ModItems.LB_MEDIUM)
                .addIngredient(ModItems.LB_EMITTER)
                .addIngredient(ModItems.LB_CASING)
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, ModItems.LASER_BLADE.getRegistryName() + "_2");
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
