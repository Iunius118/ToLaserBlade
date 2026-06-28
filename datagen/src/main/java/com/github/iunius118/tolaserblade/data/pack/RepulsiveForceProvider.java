package com.github.iunius118.tolaserblade.data.pack;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.data.recipe.BlueprintRecipeBuilder;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RepulsiveForceProvider {
    // Add `resourcepacks/` prefix to pack path for fabric's resource loader
    private final static String PACK_PATH = "resourcepacks/" + Constants.DataPacks.REPULSIVE_FORCE.id().getPath();
    public final static String PACK_DESCRIPTION = Constants.DataPacks.REPULSIVE_FORCE.descriptionKey();

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var packGenerator = dataGenerator.getBuiltinDatapack(true, PACK_PATH);

        var packMetadataSection = new PackMetadataSection(Component.translatable(PACK_DESCRIPTION),
                DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());
        packGenerator.addProvider(o ->
                new PackMetadataGenerator(packOutput).add(PackMetadataSection.SERVER_TYPE, packMetadataSection));

        var builder = new RegistrySetBuilder().add(Registries.ENCHANTMENT, ModEnchantments::bootstrapOptional);
        var builtinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), builder,
                Set.of(Constants.MOD_ID));
        packGenerator.addProvider(o -> builtinEntriesProvider);
        var lookupProvider = builtinEntriesProvider.getRegistryProvider();
        packGenerator.addProvider(o -> new PackItemTagsProvider(packOutput, lookupProvider));
        packGenerator.addProvider(o -> new PackRecipeProvider.Runner(packOutput, lookupProvider));
    }

    public static class PackItemTagsProvider extends ItemTagsProvider {

        public PackItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, Constants.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            this.tag(ModTags.Items.REPULSIVE_FORCE_UPGRADE).addTag(Tags.Items.STORAGE_BLOCKS_GOLD);
        }
    }

    public static class PackRecipeProvider extends VanillaRecipeProvider {

        public PackRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            super(provider, output);
        }

        @Override
        protected void buildRecipes() {
            var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            BlueprintRecipeBuilder.enchantment(this.items, enchantments, RecipeCategory.MISC,
                            ModEnchantments.REPULSIVE_FORCE)
                    .requires(ModTags.Items.LASER_BLADES)
                    .requires(Tags.Items.INGOTS_IRON)
                    .requires(Tags.Items.GEMS_DIAMOND)
                    .requires(ModTags.Items.REPULSIVE_FORCE_UPGRADE)
                    .unlockedBy("has_laser_blade", this.has(ModItems.LASER_BLADE))
                    .save(output, CommonClass.modLocation("blueprint/enchantment/repulsive_force"));
        }

        public static class Runner extends RecipeProvider.Runner {
            public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
                super(packOutput, registries);
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
                return new PackRecipeProvider(registryLookup, output);
            }

            @Override
            public String getName() {
                return "Recipes";
            }
        }
    }
}
