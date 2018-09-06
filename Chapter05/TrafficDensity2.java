package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrafficDensity2 {

    public Integer[] trafficByLane(Stream<TrafficUnit> stream, int trafficUnitsNumber, double timeSec,
                                   SpeedModel speedModel, double[] speedLimitByLane) {
        int lanesCount = speedLimitByLane.length;
        Map<Integer, Integer> trafficByLane = stream
                .limit(trafficUnitsNumber)
                .map(TrafficUnitWrapper::new)
                .map(tuw -> tuw.setSpeedModel(speedModel))
                .map(tuw -> calcSpeed(tuw.getVehicle(), tuw.getTraction(), timeSec))
                .map(speed -> countByLane(lanesCount, speedLimitByLane, speed))
                .collect(Collectors.groupingBy(CountByLane::getLane, Collectors.summingInt(CountByLane::getCount)));
        for(int i = 1; i <= lanesCount; i++){
            trafficByLane.putIfAbsent(i, 0);
        }
        return trafficByLane.values().toArray(new Integer[lanesCount]);
    }

    private CountByLane countByLane(int lanesNumber, double[] speedLimit, double speed){
        for(int i = 1; i <= lanesNumber; i++){
            if(speed <= speedLimit[i - 1]){
                return new CountByLane(1, i);
            }
        }
        return new CountByLane(1, lanesNumber);
    }

    private class CountByLane{
        int count, lane;
        private CountByLane(int count, int lane){
            this.count = count;
            this.lane = lane;
        }
        public int getLane() { return lane; }
        public int getCount() { return count; }
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
