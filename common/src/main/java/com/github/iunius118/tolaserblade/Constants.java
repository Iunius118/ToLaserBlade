package com.github.iunius118.tolaserblade;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        //public static final Identifier COLORIZER = CommonClass.modLocation("colorizer");
    }

    public static class Items {
        public static final Identifier LASER_BLADE = CommonClass.modLocation("laser_blade");
        public static final Identifier LASER_BLADE_FP = CommonClass.modLocation("laser_blade_fp");
        public static final Identifier LB_BATTERY = CommonClass.modLocation("lb_battery");
        public static final Identifier LB_MEDIUM = CommonClass.modLocation("lb_medium");
        public static final Identifier LB_EMITTER = CommonClass.modLocation("lb_emitter");
        public static final Identifier LB_CASING = CommonClass.modLocation("lb_casing");
        public static final Identifier LB_CASING_FP = CommonClass.modLocation("lb_casing_fp");
    }

    public static class MenuTypes {
        //public static final Identifier COLORIZER = CommonClass.modLocation("colorizer");
    }

    public static class CreativeModeTabs {
        public static final Identifier MAIN = CommonClass.modLocation("main");

        // Translation key
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);
    }
}
