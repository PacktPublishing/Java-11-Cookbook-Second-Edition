package com.packt.cookbook.ch14_testing.api;

import com.packt.cookbook.ch14_testing.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch14_testing.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch14_testing.api.Vehicle.VehicleType;


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
    default double getSpeed(){ return 0.0; }
}



