package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TLBOldRecipeProvider6 {
    public final static String PACK_PATH = "old_lb_recipes_6";
    public final static ResourceLocation PACK_ID = ToLaserBlade.makeId(PACK_PATH);
    public final static String PACK_TITLE = "old_lb_recipes_6";
    public final static String PACK_DESCRIPTION = "ToLaserBlade - revert laser blade recipes to version 6";

    private TLBOldRecipeProvider6() {}

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new TLBBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        final boolean includesServer = event.includeServer();
        var packGenerator = dataGenerator.getBuiltinDatapack(includesServer, PACK_PATH);

        packGenerator.addProvider(o -> PackMetadataGenerator.forFeaturePack(packOutput, Component.literal(PACK_DESCRIPTION)));
        packGenerator.addProvider(o -> new OldRecipeProvider.Runner(packOutput, lookupProvider));
        packGenerator.addProvider(o -> blockTagsProvider);
        packGenerator.addProvider(o -> new OldItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
    }

    public static void addPackFinders(final AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }

        var knownPack = new KnownPack(ToLaserBlade.MOD_ID, PACK_PATH, "1.0");
        var packInfo = new PackLocationInfo(PACK_ID.toString(), Component.literal(PACK_TITLE), PackSource.FEATURE, Optional.of(knownPack));
        var resourcePath = ModList.get().getModFileById(ToLaserBlade.MOD_ID).getFile().findResource(PACK_PATH);
        var packConfig = new PackSelectionConfig(false, Pack.Position.TOP, false);
        var pack = Pack.readMetaAndCreate(packInfo, new PathPackResources.PathResourcesSupplier(resourcePath), PackType.SERVER_DATA, packConfig);

        if (pack != null) {
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    private static class OldRecipeProvider extends VanillaRecipeProvider {
        public OldRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            super(registryLookup, output);
        }

        @Override
        protected void buildRecipes() {
            final HolderLookup.RegistryLookup<Item> holderGetter = registries.lookupOrThrow(Registries.ITEM);

            // Old Brand-new Laser Blade I
            ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_BRAND_NEW_1)
                    .pattern("Gid")
                    .pattern("idi")
                    .pattern("riG")
                    .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                    .define('i', Tags.Items.INGOTS_IRON)
                    .define('d', Tags.Items.GEMS_DIAMOND)
                    .define('r', Tags.Items.DUSTS_REDSTONE)
                    .unlockedBy("has_redstone", has(Items.REDSTONE))
                    .save(output, getItemId(ModItems.LB_BRAND_NEW_1) + "_v_6");

            // Old Brand-new Laser Blade II
            ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_BRAND_NEW_2)
                    .pattern("gid")
                    .pattern("idi")
                    .pattern("rig")
                    .define('g', Tags.Items.DUSTS_GLOWSTONE)
                    .define('i', Tags.Items.INGOTS_IRON)
                    .define('d', Tags.Items.GEMS_DIAMOND)
                    .define('r', Tags.Items.DUSTS_REDSTONE)
                    .unlockedBy("has_redstone", has(Items.REDSTONE))
                    .save(output, getItemId(ModItems.LB_BRAND_NEW_2) + "_v_6");
        }

        private ResourceLocation getItemId(Item item) {
            return ForgeRegistries.ITEMS.getKey(item);
        }

        public static class Runner extends RecipeProvider.Runner {
            public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
                super(output, registries);
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
                return new OldRecipeProvider(registryLookup, exporter);
            }

            @Override
            public String getName() {
                return "Recipes";
            }
        }
    }

    public static class OldItemTagsProvider extends ItemTagsProvider {
        private Set<ResourceLocation> filter = null;

        public OldItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
            super(packOutput, lookupProvider, blockTagProvider, ToLaserBlade.MOD_ID, existingFileHelper);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags(HolderLookup.Provider provider) {
            filter = new HashSet<>(this.builders.keySet());
            tag(ModItemTags.ATTACK_DAMAGE_UPGRADE).replace().addTags(Tags.Items.GEMS_DIAMOND);
        }

        @Override
        protected Path getPath(ResourceLocation id) {
            return (filter != null && filter.contains(id)) ? null : super.getPath(id);
        }
    }
}
