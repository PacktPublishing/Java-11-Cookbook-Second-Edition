package com.packt.cookbook.ch07_concurrency.api;

import com.packt.cookbook.ch07_concurrency.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch07_concurrency.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch07_concurrency.api.Vehicle.VehicleType;


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



