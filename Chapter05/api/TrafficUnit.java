package com.packt.cookbook.ch05_streams.api;

import com.packt.cookbook.ch05_streams.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch05_streams.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch05_streams.api.Vehicle.VehicleType;


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
