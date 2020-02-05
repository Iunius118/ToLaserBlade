package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.item.ToLaserBladeItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ToLaserBladeRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ToLaserBladeRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // dx_laser_blade
        ShapedRecipeBuilder.shapedRecipe(ToLaserBladeItems.DX_LASER_BLADE)
                .patternLine("r")
                .patternLine("r")
                .patternLine("s")
                .key('r', Items.REDSTONE_TORCH)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("has_dx_laser_blade", hasItem(ToLaserBladeItems.DX_LASER_BLADE))
                .build(consumer, ToLaserBladeItems.DX_LASER_BLADE.getRegistryName());
    }
}
