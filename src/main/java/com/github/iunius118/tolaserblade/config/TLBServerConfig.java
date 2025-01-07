package com.github.iunius118.tolaserblade.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class TLBServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER;
    private static final ForgeConfigSpec.BooleanValue IS_ENABLED_BLOCKING_WITH_LASER_BLADE;
    private static final ForgeConfigSpec.IntValue LASER_BLADE_EFFICIENCY;
    private static final ForgeConfigSpec.IntValue MAX_ATTACK_DAMAGE_UPGRADE_COUNT;
    private static final ForgeConfigSpec.BooleanValue IS_ENABLED_LASER_TRAP;
    private static final ForgeConfigSpec.BooleanValue CAN_LASER_TRAP_ATTACK_PLAYER;
    private static final ForgeConfigSpec.BooleanValue CAN_LASER_TRAP_HEAT_UP_FURNACE;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER = new ForgeConfigSpec.Builder()
                .comment("ToLaserBlade's game server side settings.").push("server");

        IS_ENABLED_BLOCKING_WITH_LASER_BLADE = BUILDER
                .comment("Enable blocking with Laser Blade.\n" +
                        "Default: false")
                .translation("tolaserblade.configgui.server.enableBlockingWithLaserBlade")
                .define("enableBlockingWithLaserBlade", false);
        LASER_BLADE_EFFICIENCY = BUILDER
                .comment("An integer value (0-128) that is a factor of mining speed of Laser Blade.\n" +
                        "Default: 12")
                .translation("tolaserblade.configgui.server.laserBladeEfficiency")
                .defineInRange("laserBladeEfficiency", 12, 0, 128);
        MAX_ATTACK_DAMAGE_UPGRADE_COUNT = BUILDER
                .comment("An integer value (0-39) that is maximum count of attack damage upgrade of Laser Blade.\n" +
                        "Note:\n" +
                        "  Making the advancement [It's Over 9] requires 3 or more.\n" +
                        "  Similarly, [Beyond the Limit] requires 8 or more.\n" +
                        "Default: 8")
                .translation("tolaserblade.configgui.server.maxAttackDamageUpgradeCount")
                .defineInRange("maxAttackDamageUpgradeCount", 8, 0, 39);
        IS_ENABLED_LASER_TRAP = BUILDER
                .comment("Enable to attack with Laser Blade in Dispenser when the dispenser is activated.\n" +
                        "Default: true")
                .translation("tolaserblade.configgui.server.enableLaserTrap")
                .define("enableLaserTrap", true);
        CAN_LASER_TRAP_ATTACK_PLAYER = BUILDER
                .comment("A boolean value represents whether laser trap can attack player or not. This setting is valid when enableLaserTrap is true.\n" +
                        "Default: true")
                .translation("tolaserblade.configgui.server.canLaserTrapAttackPlayer")
                .define("canLaserTrapAttackPlayer", true);
        CAN_LASER_TRAP_HEAT_UP_FURNACE = BUILDER
                .comment("A boolean value represents whether laser trap with fireproof Laser Blade can heat up furnace or not. This setting is valid when enableLaserTrap is true.\n" +
                        "Default: true")
                .translation("tolaserblade.configgui.server.canLaserTrapHeatUpFurnace")
                .define("canLaserTrapHeatUpFurnace", true);

        SPEC = BUILDER.pop().build();
    }
    
    public static boolean isEnabledBlockingWithLaserBlade = IS_ENABLED_BLOCKING_WITH_LASER_BLADE.getDefault();
    public static int laserBladeEfficiency = LASER_BLADE_EFFICIENCY.getDefault();
    public static int maxAttackDamageUpgradeCount = MAX_ATTACK_DAMAGE_UPGRADE_COUNT.getDefault();
    public static boolean isEnabledLaserTrap = IS_ENABLED_LASER_TRAP.getDefault();
    public static boolean canLaserTrapAttackPlayer = CAN_LASER_TRAP_ATTACK_PLAYER.getDefault();
    public static boolean canLaserTrapHeatUpFurnace = CAN_LASER_TRAP_HEAT_UP_FURNACE.getDefault();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        isEnabledBlockingWithLaserBlade = IS_ENABLED_BLOCKING_WITH_LASER_BLADE.get();
        laserBladeEfficiency = LASER_BLADE_EFFICIENCY.get();
        maxAttackDamageUpgradeCount = MAX_ATTACK_DAMAGE_UPGRADE_COUNT.get();
        isEnabledLaserTrap = IS_ENABLED_LASER_TRAP.get();
        canLaserTrapAttackPlayer = CAN_LASER_TRAP_ATTACK_PLAYER.get();
        canLaserTrapHeatUpFurnace = CAN_LASER_TRAP_HEAT_UP_FURNACE.get();
    }
}
