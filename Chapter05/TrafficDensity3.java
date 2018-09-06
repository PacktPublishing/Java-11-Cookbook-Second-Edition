package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TrafficDensity3 {

    public Integer[] trafficByLane(Stream<TrafficUnit> stream, int trafficUnitsNumber, double timeSec,
                                   SpeedModel speedModel, double[] speedLimitByLane) {
        int lanesCount = speedLimitByLane.length;
        Map<Integer, Integer> trafficByLane = new HashMap<>();
        for(int i = 1; i <= lanesCount; i++){
            trafficByLane.put(i, 0);
        }
        stream.limit(trafficUnitsNumber)
                .map(TrafficUnitWrapper::new)
                .map(tuw -> tuw.setSpeedModel(speedModel))
                .map(tuw -> calcSpeed(tuw.getVehicle(), tuw.getTraction(), timeSec))
                .forEach(speed -> trafficByLane.computeIfPresent(calcLaneNumber(lanesCount, speedLimitByLane, speed), (k, v) -> ++v));
        return trafficByLane.values().toArray(new Integer[lanesCount]);
    }

    protected int calcLaneNumber(int lanesCount, double[] speedLimitByLane, double speed){
        for(int i = 1; i <= lanesCount; i++){
            if(speed <= speedLimitByLane[i - 1]){
                return i;
            }
        }
        return lanesCount;
    }

    protected double calcSpeed(Vehicle vehicle, double traction, double timeSec){
        double speed = vehicle.getSpeedMph(timeSec);
        return Math.round(speed * traction);
    }

    private static class TrafficUnitWrapper {
        private Vehicle vehicle;
        private TrafficUnit trafficUnit;
        public TrafficUnitWrapper(TrafficUnit trafficUnit){
            this.vehicle = FactoryVehicle.build(trafficUnit);
            this.trafficUnit = trafficUnit;
        }
        public TrafficUnitWrapper setSpeedModel(SpeedModel speedModel) {
            this.vehicle.setSpeedModel(speedModel);
            return this;
        }
        public Vehicle getVehicle() { return vehicle; }
        public double getTraction() { return trafficUnit.getTraction(); }
    }

}
