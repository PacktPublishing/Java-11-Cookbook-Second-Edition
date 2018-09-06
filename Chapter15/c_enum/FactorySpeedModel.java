package com.packt.cookbook.ch15_new_way.c_enum;

import com.packt.cookbook.ch15_new_way.c_enum.api.SpeedModel;

import java.util.Properties;

public class FactorySpeedModel {
    public static SpeedModel generateSpeedModel(Properties drivingConditions){
        return new SpeedModelImpl(drivingConditions);
    }

    private static class SpeedModelImpl implements SpeedModel{
        private Properties drivingConditions;

        private SpeedModelImpl(Properties drivingConditions){
            this.drivingConditions = drivingConditions;
        }

        public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
            double traction = 1d;
            if(drivingConditions.containsKey(DrivingCondition.ROAD_CONDITION.toString())){
                String rcValue = drivingConditions.getProperty(DrivingCondition.ROAD_CONDITION.toString());
                double roadTraction = RoadCondition.valueOf(rcValue.toUpperCase()).getTraction();
                System.out.println("Road condition is " + rcValue + " => traction=" + roadTraction);
                traction = traction * roadTraction;
            }
            if(drivingConditions.containsKey(DrivingCondition.TIRE_CONDITION.toString())){
                String tcValue = drivingConditions.getProperty(DrivingCondition.TIRE_CONDITION.toString());
                double tireTraction = TireCondition.valueOf(tcValue.toUpperCase()).getTraction();
                System.out.println("Tire condition is " + tcValue + " => traction=" + tireTraction);
                traction = traction * tireTraction;
            }
            double v = 2.0 * horsePower * 746;
            v = v * timeSec * 32.174 / weightPounds;
            return Math.round(Math.sqrt(v) * 1.68 * traction);
        }
    }
}

