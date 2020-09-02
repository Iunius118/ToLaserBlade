package com.github.iunius118.tolaserblade.laserblade.upgrade;

import java.util.function.Function;

public interface Upgrade {
    Function<UpgradeResult, UpgradeResult> getFunction();
}
