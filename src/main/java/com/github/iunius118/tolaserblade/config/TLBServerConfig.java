package com.github.iunius118.tolaserblade.config;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TLBServerConfig {
    private static final ModConfigSpec.Builder BUILDER;
    private static final ModConfigSpec.BooleanValue IS_ENABLED_BLOCKING_WITH_LASER_BLADE;
    private static final ModConfigSpec.IntValue LASER_BLADE_EFFICIENCY;
    private static final ModConfigSpec.DoubleValue LASER_BLADE_BASE_DAMAGE;
    private static final ModConfigSpec.DoubleValue LASER_BLADE_BASE_SPEED;
    private static final ModConfigSpec.IntValue MAX_ATTACK_DAMAGE_UPGRADE_COUNT;
    private static final ModConfigSpec.IntValue ATTACK_DAMAGE_UPGRADE_MULTIPLIER;
    private static final ModConfigSpec.BooleanValue IS_ENABLED_LASER_TRAP;
    private static final ModConfigSpec.BooleanValue CAN_LASER_TRAP_ATTACK_PLAYER;
    private static final ModConfigSpec.BooleanValue CAN_LASER_TRAP_HEAT_UP_FURNACE;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER = new ModConfigSpec.Builder()
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
        LASER_BLADE_BASE_DAMAGE = BUILDER
                .comment("A real number value (0.0-2046.0) that is the base attack damage of Laser Blade.\n" +
                        "Default: 6.0")
                .translation("tolaserblade.configgui.server.laserBladeBaseDamage")
                .defineInRange("laserBladeBaseDamage", 6.0, 0.0, 2046.0);
        LASER_BLADE_BASE_SPEED = BUILDER
                .comment("A real number value (0.8-2.8) that is the base attack speed of Laser Blade.\n" +
                        "Default: 2.8")
                .translation("tolaserblade.configgui.server.laserBladeBaseSpeed")
                .defineInRange("laserBladeBaseSpeed", 2.8, 0.8, 2.8);
        MAX_ATTACK_DAMAGE_UPGRADE_COUNT = BUILDER
                .comment("An integer value (0-2047) that is maximum count of attack damage upgrade of Laser Blade.\n" +
                        "Note:\n" +
                        "  Making the advancement [It's Over 9] requires 3 or more.\n" +
                        "  Similarly, [Beyond the Limit] requires 8 or more.\n" +
                        "Default: 8")
                .translation("tolaserblade.configgui.server.maxAttackDamageUpgradeCount")
                .defineInRange("maxAttackDamageUpgradeCount", 8, 0, 2047);
        ATTACK_DAMAGE_UPGRADE_MULTIPLIER = BUILDER
                .comment("An integer value (1-2048) that is the damage multiplier for attack damage upgrade of Laser Blade.\n" +
                        "Default: 1")
                .translation("tolaserblade.configgui.server.attackDamageUpgradeMultiplier")
                .defineInRange("attackDamageUpgradeMultiplier", 1, 1, 2048);
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
    public static float laserBladeBaseDamage = calcAttackDamage(LASER_BLADE_BASE_DAMAGE.getDefault());
    public static float laserBladeBaseSpeed = calcAttackSpeed(LASER_BLADE_BASE_SPEED.getDefault());
    public static int maxAttackDamageUpgradeCount = MAX_ATTACK_DAMAGE_UPGRADE_COUNT.getDefault();
    public static float attackDamageUpgradeMultiplier = ATTACK_DAMAGE_UPGRADE_MULTIPLIER.getDefault().floatValue();
    public static boolean isEnabledLaserTrap = IS_ENABLED_LASER_TRAP.getDefault();
    public static boolean canLaserTrapAttackPlayer = CAN_LASER_TRAP_ATTACK_PLAYER.getDefault();
    public static boolean canLaserTrapHeatUpFurnace = CAN_LASER_TRAP_HEAT_UP_FURNACE.getDefault();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        isEnabledBlockingWithLaserBlade = IS_ENABLED_BLOCKING_WITH_LASER_BLADE.get();
        laserBladeEfficiency = LASER_BLADE_EFFICIENCY.get();
        laserBladeBaseDamage = calcAttackDamage(LASER_BLADE_BASE_DAMAGE.get());
        laserBladeBaseSpeed = calcAttackSpeed(LASER_BLADE_BASE_SPEED.get());
        maxAttackDamageUpgradeCount = MAX_ATTACK_DAMAGE_UPGRADE_COUNT.get();
        attackDamageUpgradeMultiplier = ATTACK_DAMAGE_UPGRADE_MULTIPLIER.get().floatValue();
        isEnabledLaserTrap = IS_ENABLED_LASER_TRAP.get();
        canLaserTrapAttackPlayer = CAN_LASER_TRAP_ATTACK_PLAYER.get();
        canLaserTrapHeatUpFurnace = CAN_LASER_TRAP_HEAT_UP_FURNACE.get();
    }

    private static float calcAttackDamage(double d) {
        // ((Entity) 1 + (Sword) 3 + (Laser Blade) 3 + (Netherite) [0, 1]) + ((config) d[0, 2046] - 6) = [1, 2048]
        return (float) d - 6.0F;
    }

    private static float calcAttackSpeed(double d) {
        // ((Entity) 4 + (Laser Blade) -1.2) + ((config) d[0.8, 2.8] - 2.8) = [0.8, 2.8]
        return (float) d - 2.8F;
    }
}
