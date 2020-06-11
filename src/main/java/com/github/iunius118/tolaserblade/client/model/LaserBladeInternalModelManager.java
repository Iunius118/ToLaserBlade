package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeItemModelType0;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeItemModelType1;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeItemModelType217;

import java.util.Calendar;

public class LaserBladeInternalModelManager {
    public static SimpleItemModel getModel() {
        int modelType = ToLaserBladeConfig.CLIENT.internalModelType.get();

        if (modelType < 0) {
            final Calendar calendar = Calendar.getInstance();
            modelType = (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
        }

        switch(modelType) {
            case 1:
                return new LaserBladeItemModelType1();
            case 217:
                return new LaserBladeItemModelType217();
            default:
                return new LaserBladeItemModelType0();
        }
    }
}
