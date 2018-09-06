package com.packt.cookbook.ch14_testing;

import com.packt.cookbook.ch14_testing.factories.DateLocation;
import com.packt.cookbook.ch14_testing.api.SpeedModel;
import com.packt.cookbook.ch14_testing.api.TrafficUnit;
import com.packt.cookbook.ch14_testing.api.Vehicle;
import com.packt.cookbook.ch14_testing.factories.FactorySpeedModel;
import com.packt.cookbook.ch14_testing.factories.FactoryTraffic;
import com.packt.cookbook.ch14_testing.factories.FactoryVehicle;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class AverageSpeed extends RecursiveTask<Double> {
    private int laneNumber;
    private double timeSec;
    private double[] speedLimitByLane;
    private DateLocation dateLocation;
    private int threshold, trafficUnitsNumber;
    private SpeedModel speedModel = FactorySpeedModel.getSpeedModel();

    public AverageSpeed(int trafficUnitsNumber, double timeSec, DateLocation dateLocation, double[] speedLimitByLane, int laneNumber, int threshold){
        this.timeSec = timeSec;
        this.threshold = threshold;
        this.laneNumber = laneNumber;
        this.dateLocation = dateLocation;
        this.speedLimitByLane = speedLimitByLane;
        this.trafficUnitsNumber = trafficUnitsNumber;
    }

    protected Double compute() {
        double max = speedLimitByLane[laneNumber - 1];
        double min = laneNumber == 1 ? 0.0 : speedLimitByLane[laneNumber - 2];
        if (trafficUnitsNumber < threshold) {
            double speed = FactoryTraffic.getTrafficUnitStream(dateLocation, trafficUnitsNumber)
                    .map(TrafficUnitWrapper::new)
                    .map(tuw -> tuw.setSpeedModel(speedModel))
                    .map(tuw -> calcSpeed(tuw.getVehicle(), tuw.getTraction(), timeSec))
                    .mapToDouble(sp -> sp)
                    .filter(sp -> sp > min && sp <= max)
                    .average()
                    .getAsDouble();
            return (double) Math.round(speed);
        }
        else{
            int tun = trafficUnitsNumber / 2;
            AverageSpeed as1 = new AverageSpeed(tun, timeSec, dateLocation, speedLimitByLane, laneNumber, threshold);
            AverageSpeed as2 = new AverageSpeed(tun, timeSec, dateLocation, speedLimitByLane, laneNumber, threshold);
            return ForkJoinTask.invokeAll(List.of(as1, as2))
                    .stream()
                    .mapToDouble(ForkJoinTask::join)
                    .map(Math::round)
                    .average()
                    .getAsDouble();
        }

    }

    protected double calcSpeed(Vehicle vehicle, double traction, double timeSec){
        double speed = vehicle.getSpeedMph(timeSec);
        return Math.round(speed * traction);
    }

    private static class TrafficUnitWrapper {
        private Vehicle vehicle;
        private TrafficUnit trafficUnit;
        public TrafficUnitWrapper(TrafficUnit trafficUnit){
            this.trafficUnit = trafficUnit;
            this.vehicle = FactoryVehicle.build(trafficUnit);
        }
        public TrafficUnitWrapper setSpeedModel(SpeedModel speedModel) {
            this.vehicle.setSpeedModel(speedModel);
            return this;
        }
        public Vehicle getVehicle() { return vehicle; }
        public double getTraction() { return trafficUnit.getTraction(); }
    }

}
