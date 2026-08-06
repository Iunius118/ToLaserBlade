package com.github.iunius118.tolaserblade;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        public static final ResourceKey<Block> BL_BLUEPRINT = createKey("lb_blueprint");

        private static ResourceKey<Block> createKey(String path) {
            return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class BlockTypes {
        public static final ResourceKey<MapCodec<? extends Block>> BL_BLUEPRINT = createKey("lb_blueprint");

        private static ResourceKey<MapCodec<? extends Block>> createKey(String path) {
            return ResourceKey.create(Registries.BLOCK_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class Items {
        public static final ResourceKey<Item> BL_BLUEPRINT = createKey("lb_blueprint");
        public static final ResourceKey<Item> LASER_BLADE = createKey("laser_blade");
        public static final ResourceKey<Item> LASER_BLADE_FP = createKey("laser_blade_fp");
        public static final ResourceKey<Item> LB_CASING = createKey("lb_casing");
        public static final ResourceKey<Item> LB_CASING_FP = createKey("lb_casing_fp");
        public static final ResourceKey<Item> LB_BATTERY = createKey("lb_battery");
        public static final ResourceKey<Item> LB_MEDIUM = createKey("lb_medium");
        public static final ResourceKey<Item> LB_EMITTER = createKey("lb_emitter");

        private static ResourceKey<Item> createKey(String path) {
            return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class Attributes {
        public static final Identifier LASER_BLADE_ATTACK_DAMAGE =
                CommonClass.modLocation("laser_blade").withSuffix("/attack_damage");
        public static final Identifier LASER_BLADE_ATTACK_SPEED =
                CommonClass.modLocation("laser_blade").withSuffix("/attack_speed");
    }

    public static class DataComponents {
        public static final ResourceKey<DataComponentType<?>> MODEL = createKey("model");
        public static final ResourceKey<DataComponentType<?>> BLEND_MODES = createKey("blend_modes");

        private static ResourceKey<DataComponentType<?>> createKey(String path) {
            return ResourceKey.create(Registries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class RecipeTypes {
        public static final ResourceKey<RecipeType<?>> BLUEPRINT = createKey("blueprint");

        private static ResourceKey<RecipeType<?>> createKey(String path) {
            return ResourceKey.create(Registries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class RecipeSerializers {
        public static final ResourceKey<RecipeSerializer<?>> BLENDING = createKey("blending");
        public static final ResourceKey<RecipeSerializer<?>> COLORING = createKey("coloring");
        public static final ResourceKey<RecipeSerializer<?>> CRAFTING = createKey("crafting");
        public static final ResourceKey<RecipeSerializer<?>> ENCHANTMENT = createKey("enchantment");
        public static final ResourceKey<RecipeSerializer<?>> REMODEL = createKey("remodel");
        public static final ResourceKey<RecipeSerializer<?>> REPAIR = createKey("repair");

        private static ResourceKey<RecipeSerializer<?>> createKey(String path) {
            return ResourceKey.create(Registries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class RecipeBookCategories {
        public static final ResourceKey<RecipeBookCategory> BLUEPRINT = createKey("blueprint");

        private static ResourceKey<RecipeBookCategory> createKey(String path) {
            return ResourceKey.create(Registries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class Enchantments {
        public static final ResourceKey<Enchantment> LASER_BLADE = createKey("laser_blade");
        public static final ResourceKey<Enchantment> LIGHT_ELEMENT = createKey("light_element");
        public static final ResourceKey<Enchantment> REPULSIVE_FORCE = createKey("repulsive_force");

        private static ResourceKey<Enchantment> createKey(String path) {
            return ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class Menus {
        public static final ResourceKey<MenuType<?>> BLUEPRINT = createKey("blueprint");

        // Translation key
        public static final String BLUEPRINT_TITLE = "container.%s.blueprint".formatted(Constants.MOD_ID);

        private static ResourceKey<MenuType<?>> createKey(String path) {
            return ResourceKey.create(Registries.MENU, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class CreativeModeTabs {
        public static final ResourceKey<CreativeModeTab> MAIN = createKey("main");

        // Translation key
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);

        private static ResourceKey<CreativeModeTab> createKey(String path) {
            return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class SoundEvents {
        public static final ResourceKey<SoundEvent> ITEM_LASER_BLADE_SWING = createKey("item.laser_blade.swing");
        public static final ResourceKey<SoundEvent> ITEM_LASER_BLADE_HIT = createKey("item.laser_blade.hit");
        public static final ResourceKey<SoundEvent> ITEM_LASER_BLADE_BLOCK = createKey("item.laser_blade.block");
        public static final ResourceKey<SoundEvent> ITEM_LASER_BLADE_BREAK = createKey("item.laser_blade.break");

        private static ResourceKey<SoundEvent> createKey(String path) {
            return ResourceKey.create(Registries.SOUND_EVENT, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public record DataPacks(Identifier id, String nameKey, String descriptionKey) {
        public static final DataPacks REPULSIVE_FORCE = new DataPacks(CommonClass.modLocation("repulsive_force"));
        public static final DataPacks SAMPLE_SOUND_PACK = new DataPacks(CommonClass.modLocation("sample_sound_pack"));

        private DataPacks (Identifier id) {
            this(id, "dataPack.%s.name".formatted(id.toLanguageKey()),
                    "dataPack.%s.description".formatted(id.toLanguageKey()));
        }
    }
}
