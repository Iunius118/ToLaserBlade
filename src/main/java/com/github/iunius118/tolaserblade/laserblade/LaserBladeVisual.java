package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.Constants;

public class LaserBladeVisual {
    private final BladeColor bladeColor;
    private final GripColor gripColor;

    public LaserBladeVisual(CompoundNBT compound) {
        bladeColor = new BladeColor(compound);
        gripColor = new GripColor(compound);
    }

    public PartColor getInnerColor() {
        return bladeColor.innerColor;
    }

    public PartColor getOuterColor() {
        return bladeColor.outerColor;
    }

    public PartColor getGripColor() {
        return gripColor.gripColor;
    }

    public void setColorsByBiome(Biome biome) {
        // Color by Biome type or Biome temperature
        if (biome.getCategory() == Biome.Category.NETHER) {
            // The Nether
            getInnerColor().isSubtractColor = true;
        } else if (biome.getCategory() == Biome.Category.THEEND) {
            // The End
            getOuterColor().isSubtractColor = true;

        } else {
            // Biomes on Over-world or the other dimensions
            float temp = biome.func_242445_k(); // TODO: func_242445_k = getDefaultTemperature

            if (temp > 1.5F) {
                // t > 1.5
                getOuterColor().color = Color.TEMP_DESERT.getBladeColor();
            } else if (temp > 1.0F) {
                // 1.5 >= t > 1.0
                getOuterColor().color = Color.TEMP_SAVANNA.getBladeColor();
            } else if (temp > 0.8F) {
                // 1.0 >= t > 0.8
                getOuterColor().color = Color.TEMP_JUNGLE.getBladeColor();
            } else if (temp >= 0.5F) {
                // 0.8 >= t >= 0.5
                getOuterColor().color = Color.RED.getBladeColor();
            } else if (temp >= 0.2F) {
                // 0.5 > t >= 0.2
                getOuterColor().color = Color.TEMP_TAIGA.getBladeColor();
            } else if (temp >= -0.25F) {
                // 0.2 > t >= -0.25
                getOuterColor().color = Color.TEMP_ICE_PLAIN.getBladeColor();
            } else {
                // -0.25 > t
                getOuterColor().color = Color.TEMP_SNOWY_TAIGA.getBladeColor();
            }
        }
    }

    public void write(CompoundNBT compound) {
        bladeColor.write(compound);
        gripColor.write(compound);
    }

    /* Inner classes */
    private static class BladeColor {
        public PartColor innerColor;
        public PartColor outerColor;

        private static final String KEY_INNER_COLOR = "colorC";
        private static final String KEY_OUTER_COLOR = "colorH";
        private static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
        private static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";

        public BladeColor(CompoundNBT compound) {
            innerColor = new PartColor(compound, KEY_INNER_COLOR, KEY_IS_INNER_SUB_COLOR, Color.WHITE.getBladeColor());
            outerColor = new PartColor(compound, KEY_OUTER_COLOR, KEY_IS_OUTER_SUB_COLOR, Color.RED.getBladeColor());
        }

        public void write(CompoundNBT compound) {
            compound.putInt(KEY_INNER_COLOR, innerColor.color);
            compound.putBoolean(KEY_IS_INNER_SUB_COLOR, innerColor.isSubtractColor);
            compound.putInt(KEY_OUTER_COLOR, outerColor.color);
            compound.putBoolean(KEY_IS_OUTER_SUB_COLOR, outerColor.isSubtractColor);
        }
    }

    private static class GripColor {
        private PartColor gripColor;

        private static final String KEY_GRIP_COLOR = "colorG";

        public GripColor(CompoundNBT compound) {
            gripColor = new PartColor(compound, KEY_GRIP_COLOR, null, Color.WHITE.getBladeColor());
        }

        public int getGripColor() {
            return gripColor.color;
        }

        public void write(CompoundNBT compound) {
            compound.putInt(KEY_GRIP_COLOR, gripColor.color);
        }
    }

    public static class PartColor {
        public int color = -1;
        public boolean isSubtractColor = false;

        public PartColor(CompoundNBT compound, String colorKey, String subKey, int defaultColor) {
            color = defaultColor;

            if (colorKey != null && compound.contains(colorKey, Constants.NBT.TAG_INT)) {
                color = compound.getInt(colorKey);
            }

            if (subKey != null) {
                isSubtractColor = compound.getBoolean(subKey);
            }
        }
    }

    private static class ModelType {
        private int type;

        private static final String KEY_TYPE = "type";

        public ModelType(int modelType) {
            this.type = modelType;
        }
    }
}
