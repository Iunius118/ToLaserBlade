package com.github.iunius118.tolaserblade;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        public static final Identifier BL_BLUEPRINT = CommonClass.modLocation("lb_blueprint");
    }

    public static class Items {
        public static final Identifier LASER_BLADE = CommonClass.modLocation("laser_blade");
        public static final Identifier LASER_BLADE_FP = CommonClass.modLocation("laser_blade_fp");
        public static final Identifier LB_CASING = CommonClass.modLocation("lb_casing");
        public static final Identifier LB_CASING_FP = CommonClass.modLocation("lb_casing_fp");
        public static final Identifier LB_BATTERY = CommonClass.modLocation("lb_battery");
        public static final Identifier LB_MEDIUM = CommonClass.modLocation("lb_medium");
        public static final Identifier LB_EMITTER = CommonClass.modLocation("lb_emitter");
    }

    public static class Attributes {
        public static final Identifier LASER_BLADE_ATTACK_DAMAGE =
                CommonClass.modLocation("laser_blade").withSuffix("/attack_damage");
        public static final Identifier LASER_BLADE_ATTACK_SPEED =
                CommonClass.modLocation("laser_blade").withSuffix("/attack_speed");
    }

    public static class DataComponents {
        public static final Identifier MODEL = CommonClass.modLocation("model");
        public static final Identifier BLEND_MODES = CommonClass.modLocation("blend_modes");
    }

    public static class RecipeTypes {
        public static final Identifier BLUEPRINT = CommonClass.modLocation("blueprint");
    }

    public static class RecipeSerializers {
        public static final Identifier BLENDING = CommonClass.modLocation("blending");
        public static final Identifier COLORING = CommonClass.modLocation("coloring");
        public static final Identifier CRAFTING = CommonClass.modLocation("crafting");
        public static final Identifier ENCHANTMENT = CommonClass.modLocation("enchantment");
        public static final Identifier REMODEL = CommonClass.modLocation("remodel");
        public static final Identifier REPAIR = CommonClass.modLocation("repair");
    }

    public static class RecipeBookCategories {
        public static final Identifier BLUEPRINT = CommonClass.modLocation("blueprint");
    }

    public static class Enchantments {
        public static final Identifier LASER_BLADE = CommonClass.modLocation("laser_blade");
        public static final Identifier LIGHT_ELEMENT = CommonClass.modLocation("light_element");
        public static final Identifier REPULSIVE_FORCE = CommonClass.modLocation("repulsive_force");
    }

    public static class Menus {
        public static final Identifier BLUEPRINT = CommonClass.modLocation("blueprint");

        // Translation key
        public static final String BLUEPRINT_TITLE = "container.%s.blueprint".formatted(Constants.MOD_ID);
    }

    public static class CreativeModeTabs {
        public static final Identifier MAIN = CommonClass.modLocation("main");

        // Translation key
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);
    }

    public record DataPacks(Identifier id, String nameKey, String descriptionKey) {
        public static final DataPacks REPULSIVE_FORCE = new DataPacks(CommonClass.modLocation("repulsive_force"));

        private DataPacks (Identifier id) {
            this(id, "dataPack.%s.name".formatted(id.toLanguageKey()),
                    "dataPack.%s.description".formatted(id.toLanguageKey()));
        }
    }
}
