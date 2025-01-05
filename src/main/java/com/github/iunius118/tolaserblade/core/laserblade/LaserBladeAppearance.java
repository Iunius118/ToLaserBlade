package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.api.core.laserblade.LaserBladeState;
import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.github.iunius118.tolaserblade.world.item.component.LaserBladeModelData;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.Map;
import java.util.Objects;

public class LaserBladeAppearance {
    private static final String KEY_OUTER = "out";
    private static final String KEY_INNER = "in";
    private static final String KEY_GRIP  = "grip";
    private static final LaserBladeModelData.PartData DEFAULT_OUT  = new LaserBladeModelData.PartData(false, LaserBladeColor.RED.getOuterColor(), false);
    private static final LaserBladeModelData.PartData DEFAULT_IN   = new LaserBladeModelData.PartData(false, LaserBladeColor.WHITE.getInnerColor(), false);
    private static final LaserBladeModelData.PartData DEFAULT_GRIP = new LaserBladeModelData.PartData(false, LaserBladeColor.WHITE.getGripColor(), false);;

    private int type = LaserBlade.TYPE_DEFAULT;
    private LaserBladeModelData.PartData out  = DEFAULT_OUT;
    private LaserBladeModelData.PartData in   = DEFAULT_IN;
    private LaserBladeModelData.PartData grip = DEFAULT_GRIP;

    public LaserBladeAppearance() { }

    public LaserBladeAppearance(LaserBladeModelData modelData) {
        type = modelData.modelType();
        Map<String, LaserBladeModelData.PartData> parts = modelData.parts();
        out  = Objects.requireNonNullElse(parts.get(KEY_OUTER), out);
        in   = Objects.requireNonNullElse(parts.get(KEY_INNER), in);
        grip = Objects.requireNonNullElse(parts.get(KEY_GRIP), grip);
    }

    public static LaserBladeAppearance of() {
        return new LaserBladeAppearance();
    }

    public static LaserBladeAppearance of(ItemStack itemStack) {
        LaserBladeModelData modelData = itemStack.get(ModDataComponents.LASER_BLADE_MODEL);

        if (modelData != null) {
            return new LaserBladeAppearance(modelData);
        } else {
            // Attempt to get and migrate laser blade data from CUSTOM_DATA
            return LaserBladeDataMigrator.getAppearance(itemStack);
        }
    }

    /* Model Type */

    public int getType() {
        return Math.max(type, 0);
    }

    public LaserBladeAppearance setType(int type) {
        this.type = type;
        return this;
    }

    /* Outer Blade Layer */

    public LaserBladeState.Part getOuter() {
        return out;
    }

    public int getOuterColor() {
        return out.color();
    }

    public boolean isOuterSubColor() {
        return out.isSubtractiveColor();
    }

    public LaserBladeAppearance setOuterColor(int outerColor, boolean outerSubColor) {
        out = LaserBladeModelData.PartData.create(outerColor, outerSubColor);
        return this;
    }

    public LaserBladeAppearance setOuterColor(int outerColor) {
        return setOuterColor(outerColor, out.isSubtractiveColor());
    }

    public LaserBladeAppearance setOuterSubColor(boolean outerSubColor) {
        return setOuterColor(out.color(), outerSubColor);
    }

    public LaserBladeAppearance switchOuterSubColor() {
        return setOuterSubColor(!out.isSubtractiveColor());
    }

    /* Inner Blade Layer */

    public LaserBladeState.Part getInner() {
        return in;
    }

    public int getInnerColor() {
        return in.color();
    }

    public boolean isInnerSubColor() {
        return in.isSubtractiveColor();
    }

    public LaserBladeAppearance setInnerColor(int innerColor, boolean innerSubColor) {
        in = LaserBladeModelData.PartData.create(innerColor, innerSubColor);
        return this;
    }

    public LaserBladeAppearance setInnerColor(int innerColor) {
        return setInnerColor(innerColor, in.isSubtractiveColor());
    }

    public LaserBladeAppearance setInnerSubColor(boolean innerSubColor) {
        return setInnerColor(in.color(), innerSubColor);
    }

    public LaserBladeAppearance switchInnerSubColor() {
        return setInnerSubColor(!in.isSubtractiveColor());
    }

    /* Grip */

    public LaserBladeState.Part getGrip() {
        return grip;
    }

    public int getGripColor() {
        return grip.color();
    }

    public LaserBladeAppearance setGripColor(int gripColor) {
        grip = LaserBladeModelData.PartData.create(gripColor, false);
        return this;
    }

    /* Util */

    public LaserBladeAppearance setColor(LaserBladeColor color) {
        out  = LaserBladeModelData.PartData.create(color.getOuterColor(), color.isOuterSubColor());
        in   = LaserBladeModelData.PartData.create(color.getInnerColor(), color.isInnerSubColor());
        grip = LaserBladeModelData.PartData.create(color.getGripColor(), false);
        return this;
    }

    public LaserBladeAppearance setColorsByBiome(Level level, Holder<Biome> biomeHolder) {
        ResourceKey<Level> dimension = level.dimension();

        if (Level.NETHER.equals(dimension)) {
            // The Nether
            setColorsByNetherBiome(level, biomeHolder);
        } else if (Level.END.equals(dimension)) {
            // The End
            setColorsByEndBiome(level, biomeHolder);
        } else {
            // Over-world etc.
            setColorsByOverWorldBiome(level, biomeHolder);
        }

        return this;
    }

    public void setTo(ItemStack itemStack) {
        var partBuilder = new ImmutableMap.Builder<String, LaserBladeModelData.PartData>();

        if (out.exists()) {
            partBuilder.put(KEY_OUTER, out);
        }

        if (in.exists()) {
            partBuilder.put(KEY_INNER, in);
        }

        if (grip.exists()) {
            partBuilder.put(KEY_GRIP, grip);
        }

        LaserBladeModelData modelData = new LaserBladeModelData(type, partBuilder.build());
        itemStack.set(ModDataComponents.LASER_BLADE_MODEL, modelData);
    }

    private void setColorsByNetherBiome(Level level, Holder<Biome> biomeHolder) {
        if (compareBiomes(biomeHolder, Biomes.SOUL_SAND_VALLEY) || compareBiomes(biomeHolder, Biomes.WARPED_FOREST)) {
            setColor(LaserBladeColor.BIOME_NETHER_B);
        } else {
            setColor(LaserBladeColor.BIOME_NETHER_A);
        }
    }

    private void setColorsByEndBiome(Level level, Holder<Biome> biomeHolder) {
        setColor(LaserBladeColor.BIOME_END);
    }

    private void setColorsByOverWorldBiome(Level level, Holder<Biome> biomeHolder) {
        if (compareBiomes(biomeHolder, Biomes.DEEP_DARK)) {
            // Deep dark biome
            setColor(LaserBladeColor.BIOME_DEEP_DARK);
            type = 2;
        } else if (compareBiomes(biomeHolder, Biomes.CHERRY_GROVE)) {
            // Cherry grove biome
            setColor(LaserBladeColor.BIOME_CHERRY_GROVE);
        } else if (compareBiomes(biomeHolder, Biomes.PALE_GARDEN)) {
            // Pale garden biome
            setColor(LaserBladeColor.BIOME_PALE_GARDEN);
        } else {
            float temp = biomeHolder.value().getBaseTemperature();
            setOuterColor(LaserBladeColor.getColorByTemperature(temp).getOuterColor());
        }
    }

    private boolean compareBiomes(Holder<Biome> biomeHolder, ResourceKey<Biome> biomeKey) {
        if (biomeHolder == null)
            return false;

        return biomeHolder.is(biomeKey);
    }
}
