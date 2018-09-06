package com.packt.cookbook.ch04_functional.c.api;

import com.packt.cookbook.ch04_functional.c.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch04_functional.c.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch04_functional.c.api.Vehicle.VehicleType;

public interface TrafficUnit {
    VehicleType getVehicleType();
    int getHorsePower();
    int getWeightPounds();
    int getPayloadPounds();
    int getPassengersCount();
    double getSpeedLimitMph();
    double getTraction();
    RoadCondition getRoadCondition();
    TireCondition getTireCondition();
    int getTemperature();
}
