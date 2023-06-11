package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class LBModelChangeRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private ItemStack sample;

    public LBModelChangeRecipe(ResourceLocation recipeId, Ingredient template, Ingredient base, Ingredient addition, int type) {
        super(recipeId, template, base, addition, getResultItemStack(base));
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    private static ItemStack getResultItemStack(Ingredient base) {
        ItemStack[] matchingStacks = base.getItems();

        if (matchingStacks.length > 0 && matchingStacks[0] != null) {
            return matchingStacks[0].copy();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (super.matches(container, level)) {
            ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
            int baseType = LaserBlade.of(baseStack).getType();
            return type >= 0 && baseType != type;
        }

        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack itemstack = baseStack.copy();
        return getResult(itemstack);
    }

    private ItemStack getResult(ItemStack input) {
        LaserBlade.Writer.of(input).writeType(type);
        return input;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        if (sample != null) return sample;

        ItemStack output = super.getResultItem(registryAccess);

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getResult(output.copy());
        return sample;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MODEL_CHANGE;
    }

    public static class Serializer implements RecipeSerializer<LBModelChangeRecipe> {
        @Override
        public LBModelChangeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient template = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "template"));
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            JsonObject result = GsonHelper.getAsJsonObject(json, "result");
            JsonElement modelType = result.get("model_type");
            int type = modelType.getAsInt();
            return new LBModelChangeRecipe(recipeId, template, base, addition, type);
        }

        @Nullable
        @Override
        public LBModelChangeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient template = Ingredient.fromNetwork(buffer);
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            int type = buffer.readInt();
            return new LBModelChangeRecipe(recipeId, template, base, addition, type);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, LBModelChangeRecipe recipe) {
            recipe.template.toNetwork(buffer);
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeInt(recipe.type);
        }
    }
}
