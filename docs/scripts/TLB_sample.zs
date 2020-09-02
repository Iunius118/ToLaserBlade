// This is a sample script to add a crafting recipe of Laser Blade for CraftTweaker
// Compatible with ToLaserBlade 1.16.2-3.2.3.0+ and CraftTweaker 1.16.2-7+

import crafttweaker.api.item.IItemStack;

// Add a recipe of Laser Blade
craftingTable.addShapeless("laserblade_green", <item:tolaserblade:laser_blade>, [<item:tolaserblade:laser_blade>],  (usualOut as IItemStack, inputs as IItemStack[]) => {
    for recipeItem in inputs {
        if (<item:tolaserblade:laser_blade>.matches(recipeItem)) {
            var tag = recipeItem.tag.asMap();

            if (!recipeItem.hasTag) {
                tag = new crafttweaker.api.data.IData[string];
            }

            tag["ct"] = 1;  // Set flag to skip default enchanting and coloring
            tag["colorH"] = -16711936;  // Set outer blade color to green
            tag["colorC"] = -1; // Set inner blade color to white
            return recipeItem.withTag(tag); // Set tags to the Laser Blade and return it for the crafting result
        }
    }

    return usualOut;
});

// Add a recipe of fireproof Laser Blade
craftingTable.addShapeless("laserblade_fp_skyblue", <item:tolaserblade:laser_blade_fp>, [<item:tolaserblade:laser_blade_fp>],  (usualOut as IItemStack, inputs as IItemStack[]) => {
    for recipeItem in inputs {
        if (<item:tolaserblade:laser_blade_fp>.matches(recipeItem)) {
            var tag = recipeItem.tag.asMap();

            if (!recipeItem.hasTag) {
                tag = new crafttweaker.api.data.IData[string];
            }

            tag["ct"] = 1;  // Set flag to skip default enchanting and coloring
            tag["colorH"] = -16744449;  // Set outer blade color to skyblue
            tag["colorC"] = -1; // Set inner blade color to white
            return recipeItem.withTag(tag); // Set tags to the Laser Blade and return it for the crafting result
        }
    }

    return usualOut;
});
