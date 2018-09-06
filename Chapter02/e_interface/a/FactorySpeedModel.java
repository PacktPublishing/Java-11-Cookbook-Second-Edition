package com.packt.cookbook.ch02_oop.e_interface.a;

import com.packt.cookbook.ch02_oop.e_interface.a.api.SpeedModel;
import java.util.Properties;

public class FactorySpeedModel {
    public static SpeedModel generateSpeedModel(Properties drivingConditions){
        return new SpeedModelImpl(drivingConditions);
    }

    private static class SpeedModelImpl implements SpeedModel {
        private Properties drivingConditions;

        private SpeedModelImpl(Properties drivingConditions){
            this.drivingConditions = drivingConditions;
        }

        public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
            String roadCondition = drivingConditions.getProperty("roadCondition", "Dry");
            String tireCondition = drivingConditions.getProperty("tireCondition", "New");
            double v = 2.0*horsePower*746;
            v = v*timeSec*32.174/weightPounds;
            return Math.round(Math.sqrt(v)*0.68) - (roadCondition.equals("Dry")?2:5) - (tireCondition.equals("New")?0:5);
        }
    }
}
