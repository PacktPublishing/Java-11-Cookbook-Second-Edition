package com.packt.cookbook.ch14_testing;

import com.packt.cookbook.ch14_testing.factories.DateLocation;
import com.packt.cookbook.ch14_testing.api.SpeedModel;
import com.packt.cookbook.ch14_testing.api.TrafficUnit;
import com.packt.cookbook.ch14_testing.api.Vehicle;
import com.packt.cookbook.ch14_testing.factories.FactorySpeedModel;
import com.packt.cookbook.ch14_testing.factories.FactoryTraffic;
import com.packt.cookbook.ch14_testing.factories.FactoryVehicle;
import com.packt.cookbook.ch14_testing.utils.DbUtil;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TrafficDensity {
    public static Connection conn;
    public static boolean recordData = false;
    private boolean commonDataRecorded = false;

    public Integer[] trafficByLane(int trafficUnitsNumber, double timeSec, DateLocation dateLocation, double[] speedLimitByLane) {
        if(recordData && !commonDataRecorded){
            DbUtil.recordDataCommon(trafficUnitsNumber, timeSec, dateLocation.toString(), speedLimitByLane);
        }
        SpeedModel speedModel = FactorySpeedModel.getSpeedModel();
        return trafficByLane(FactoryTraffic.getTrafficUnitStream(dateLocation, trafficUnitsNumber), trafficUnitsNumber,
                timeSec, speedModel, speedLimitByLane);
    }

    private Integer[] trafficByLane(Stream<TrafficUnit> stream, int trafficUnitsNumber, double timeSec,
                                   SpeedModel speedModel, double[] speedLimitByLane) {
        int lanesCount = speedLimitByLane.length;
        Map<Integer, Integer> trafficByLane = new HashMap<>();
        for(int i = 1; i <= lanesCount; i++){
            trafficByLane.put(i, 0);
        }
        stream.limit(trafficUnitsNumber)
                .map(TrafficUnitWrapper::new)
                .map(tuw -> tuw.setSpeedModel(speedModel))
                .map(tuw -> calcSpeed(tuw, timeSec))
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

    private double calcSpeed(TrafficUnitWrapper tuw, double timeSec){
        double speed = calcSpeed(tuw.getVehicle(), tuw.getTrafficUnit().getTraction(), timeSec);
        if(recordData) {
            DbUtil.recordData(conn, tuw.getTrafficUnit(), speed);
        }
        return speed;
    }

    private class TrafficUnitWrapper {
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
        public TrafficUnit getTrafficUnit() { return trafficUnit; }
    }
}
