package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrafficDensity1 {

    public Integer[] trafficByLane(Stream<TrafficUnit> stream, int trafficUnitsNumber, double timeSec,
                                   SpeedModel speedModel, double[] speedLimitByLane) {
        int lanesCount = speedLimitByLane.length;
        Map<Integer, Integer> trafficByLane = stream
                .limit(trafficUnitsNumber)
                .map(TrafficUnitWrapper::new)
                .map(tuw -> tuw.setSpeedModel(speedModel))
                .map(tuw -> tuw.calcSpeed(timeSec))
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
        public double calcSpeed(double timeSec) {
            double speed = this.vehicle.getSpeedMph(timeSec);
            return Math.round(speed * this.trafficUnit.getTraction());
        }
    }

}
