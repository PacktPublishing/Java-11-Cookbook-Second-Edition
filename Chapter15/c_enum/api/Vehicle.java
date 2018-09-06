package com.packt.cookbook.ch15_new_way.c_enum.api;

public interface Vehicle {
    void setSpeedModel(SpeedModel speedModel);
    double getSpeedMph(double timeSec);
    int getWeightPounds();
}
