package com.github.iunius118.tolaserblade.laserblade.upgrade;

import java.util.function.Function;

public interface Upgrade {
    Function<UpgradeResult, UpgradeResult> getFunction();

    enum Type {
        BATTERY,
        MEDIUM,
        EMITTER,
        CASING,
        REPAIR,
        OTHER
    }
}
