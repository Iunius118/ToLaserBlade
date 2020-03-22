package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.item.ModItems;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class TLBAdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public TLBAdvancementProvider(DataGenerator generatorIn) {
        generator = generatorIn;
    }

    protected void registerAdvancements(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.builder()
                .withDisplay(ModItems.LASER_BLADE,
                        new TranslationTextComponent("advancements.tolaserblade.main.root.title"),
                        new TranslationTextComponent("advancements.tolaserblade.main.root.description"),
                        new ResourceLocation("textures/block/polished_andesite.png"),
                        FrameType.TASK, false, false, false)
                .withCriterion("has_redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
                .withCriterion("has_dx_laser_blade", InventoryChangeTrigger.Instance.forItems(ModItems.DX_LASER_BLADE))
                .withCriterion("has_laser_blade", InventoryChangeTrigger.Instance.forItems(ModItems.LASER_BLADE))
                .withRequirementsStrategy(IRequirementsStrategy.OR)
                .register(consumer, "tolaserblade:main/root");
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path advancementPath = getPath(path, advancement);

                try {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), advancementPath);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", advancementPath, ioexception);
                }

            }
        };

        registerAdvancements(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "ToLaserBlade Advancements";
    }
}
