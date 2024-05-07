package com.github.iunius118.tolaserblade.core.laserblade;

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

public class LaserBladeAppearance {
    private static final String KEY_OUTER = "out";
    private static final String KEY_INNER = "in";
    private static final String KEY_GRIP = "grip";

    private int type = LaserBlade.TYPE_DEFAULT;;
    private int outerColor = LaserBladeColor.RED.getBladeColor();;
    private int innerColor = LaserBladeColor.WHITE.getBladeColor();;
    private int gripColor = LaserBladeColor.WHITE.getGripColor();;
    private boolean isOuterSubColor = false;
    private boolean isInnerSubColor = false;

    public LaserBladeAppearance() { }

    public LaserBladeAppearance(LaserBladeModelData modelData) {
        type = modelData.modelType();
        Map<String, LaserBladeModelData.PartData> parts = modelData.parts();
        LaserBladeModelData.PartData out = parts.get(KEY_OUTER);
        LaserBladeModelData.PartData in = parts.get(KEY_INNER);
        LaserBladeModelData.PartData grip = parts.get(KEY_GRIP);

        if (out != null) {
            outerColor = out.color();
            isOuterSubColor = out.isSubtractiveColor();
        }

        if (in != null) {
            innerColor = in.color();
            isInnerSubColor = in.isSubtractiveColor();
        }

        if (grip != null) {
            gripColor = grip.color();
        }
    }

    public static LaserBladeAppearance of() {
        return new LaserBladeAppearance();
    }

    public static LaserBladeAppearance of(ItemStack itemStack) {
        LaserBladeModelData modelData = itemStack.get(ModDataComponents.LASER_BLADE_MODEL);

        if (modelData != null) {
            return new LaserBladeAppearance(modelData);
        } else {
            // Attempt to get and migrate data from CUSTOM_DATA
            return LaserBladeDataMigrator.getAppearance(itemStack);
        }
    }

    public int getType() {
        return Math.max(type, 0);
    }

    public LaserBladeAppearance setType(int type) {
        this.type = type;
        return this;
    }

    public int getOuterColor() {
        return outerColor;
    }

    public LaserBladeAppearance setOuterColor(int outerColor) {
        this.outerColor = outerColor;
        return this;
    }

    public boolean isOuterSubColor() {
        return isOuterSubColor;
    }

    public LaserBladeAppearance setOuterSubColor(boolean outerSubColor) {
        isOuterSubColor = outerSubColor;
        return this;
    }

    public LaserBladeAppearance switchOuterSubColor() {
        isOuterSubColor = !isOuterSubColor;
        return this;
    }

    public int getInnerColor() {
        return innerColor;
    }

    public LaserBladeAppearance setInnerColor(int innerColor) {
        this.innerColor = innerColor;
        return this;
    }

    public boolean isInnerSubColor() {
        return isInnerSubColor;
    }

    public LaserBladeAppearance setInnerSubColor(boolean innerSubColor) {
        isInnerSubColor = innerSubColor;
        return this;
    }

    public LaserBladeAppearance switchInnerSubColor() {
        isInnerSubColor = !isInnerSubColor;
        return this;
    }

    public int getGripColor() {
        return gripColor;
    }

    public LaserBladeAppearance setGripColor(int gripColor) {
        this.gripColor = gripColor;
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

    public void writeTo(ItemStack itemStack) {
        Map<String, LaserBladeModelData.PartData> parts = new ImmutableMap.Builder<String, LaserBladeModelData.PartData>()
                .put(KEY_OUTER, LaserBladeModelData.PartData.create(outerColor, isOuterSubColor))
                .put(KEY_INNER, LaserBladeModelData.PartData.create(innerColor, isInnerSubColor))
                .put(KEY_GRIP, LaserBladeModelData.PartData.create(gripColor, false))
                .build();
        LaserBladeModelData modelData = new LaserBladeModelData(type, parts);
        itemStack.set(ModDataComponents.LASER_BLADE_MODEL, modelData);
    }

    private void setColorsByNetherBiome(Level level, Holder<Biome> biomeHolder) {
        outerColor = LaserBladeColor.WHITE.getBladeColor();

        if (compareBiomes(biomeHolder, Biomes.SOUL_SAND_VALLEY) || compareBiomes(biomeHolder, Biomes.WARPED_FOREST)) {
            isOuterSubColor = true;
        } else {
            isInnerSubColor = true;
        }
    }

    private void setColorsByEndBiome(Level level, Holder<Biome> biomeHolder) {
        outerColor = LaserBladeColor.WHITE.getBladeColor();
        isOuterSubColor = isInnerSubColor = true;
    }

    private void setColorsByOverWorldBiome(Level level, Holder<Biome> biomeHolder) {
        if (compareBiomes(biomeHolder, Biomes.DEEP_DARK)) {
            // Deep dark biome
            setDeepDarkColors();
        } else if (compareBiomes(biomeHolder, Biomes.CHERRY_GROVE)) {
            // Cherry grove biome
            setCherryGroveColors();
        } else {
            float temp = biomeHolder.value().getBaseTemperature();
            outerColor = LaserBladeColor.getColorByTemperature(temp).getBladeColor();
        }
    }

    private void setDeepDarkColors() {
        outerColor = LaserBladeColor.CYAN.getBladeColor();
        innerColor = 0xFFFADCD7;    // Sculk's deep dark blue (negative)
        isInnerSubColor = true;
        gripColor = 0xFF052328;     // Sculk's deep dark blue
        type = 2;
    }

    private void setCherryGroveColors() {
        outerColor = LaserBladeColor.PINK.getBladeColor();
        innerColor = 0xFFFADCF0;    // Cherry blossom's light pink
        gripColor = 0xFF4B2D3C;     // Cherry log's dark brown
    }

    private boolean compareBiomes(Holder<Biome> biomeHolder, ResourceKey<Biome> biomeKey) {
        if (biomeHolder == null)
            return false;

        return biomeHolder.is(biomeKey);
    }
}
