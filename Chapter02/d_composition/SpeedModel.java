package com.packt.cookbook.ch02_oop.d_composition;

import java.util.Properties;

public class SpeedModel {
    private Properties conditions;

    public SpeedModel(Properties drivingConditions){
        this.conditions = drivingConditions;
    }

    public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
        String road = conditions.getProperty("roadCondition", "Dry");
        String tire = conditions.getProperty("tireCondition", "New");
        double v = 2.0*horsePower*746;
        v = v*timeSec*32.174/weightPounds;
        return Math.round(Math.sqrt(v)*0.68) - (road.equals("Dry")?2:5) - (tire.equals("New")?0:5);
    }
}
