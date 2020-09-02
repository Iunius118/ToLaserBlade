package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

public class LaserBladeVisual {
    private BladeColor bladeColor;
    private GripColor gripColor;

    public LaserBladeVisual(CompoundNBT compound) {
        bladeColor = new BladeColor(compound);
        gripColor = new GripColor(compound);
    }

    public int getInnerColor() {
        return bladeColor.getInnerColor();
    }

    public boolean isInnerSubColor() {
        return bladeColor.isInnerSubColor();
    }

    public int getOuterColor() {
        return bladeColor.getOuterColor();
    }

    public boolean isOuterSubColor() {
        return bladeColor.isOuterSubColor();
    }

    public int getGripColor() {
        return gripColor.getGripColor();
    }

    public void write(CompoundNBT compound) {
        bladeColor.write(compound);
        gripColor.write(compound);
    }

    /* Inner classes */
    private static class BladeColor {
        private PartColor innerColor;
        private PartColor outerColor;

        private static final String KEY_INNER_COLOR = "colorC";
        private static final String KEY_OUTER_COLOR = "colorH";
        private static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
        private static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";

        public BladeColor(CompoundNBT compound) {
            innerColor = new PartColor(compound, KEY_INNER_COLOR, KEY_IS_INNER_SUB_COLOR, Color.WHITE.getBladeColor());
            outerColor = new PartColor(compound, KEY_OUTER_COLOR, KEY_IS_OUTER_SUB_COLOR, Color.RED.getBladeColor());
        }

        public int getInnerColor() {
            return innerColor.color;
        }

        public boolean isInnerSubColor() {
            return innerColor.isSubtractColor;
        }

        public int getOuterColor() {
            return outerColor.color;
        }

        public boolean isOuterSubColor() {
            return outerColor.isSubtractColor;
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

    private static class PartColor {
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
