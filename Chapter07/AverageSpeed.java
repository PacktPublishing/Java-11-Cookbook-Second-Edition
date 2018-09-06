package com.packt.cookbook.ch07_concurrency;

import com.packt.cookbook.ch07_concurrency.api.DateLocation;
import com.packt.cookbook.ch07_concurrency.api.SpeedModel;
import com.packt.cookbook.ch07_concurrency.api.TrafficUnit;
import com.packt.cookbook.ch07_concurrency.api.Vehicle;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class AverageSpeed extends RecursiveTask<Double> {
    private double timeSec;
    private DateLocation dateLocation;
    private int threshold, trafficUnitsNumber;

    public AverageSpeed(DateLocation dateLocation, double timeSec, int trafficUnitsNumber, int threshold){
        this.timeSec = timeSec;
        this.threshold = threshold;
        this.dateLocation = dateLocation;
        this.trafficUnitsNumber = trafficUnitsNumber;
    }

    protected Double compute() {
        if (trafficUnitsNumber < threshold) {
            double speed = FactoryTraffic.getTrafficUnitStream(dateLocation, trafficUnitsNumber)
                    .map(TrafficUnitWrapper::new)
                    .map(tuw -> tuw.setSpeedModel(FactorySpeedModel.generateSpeedModel(tuw.getTrafficUnit())))
                    .map(tuw -> tuw.calcSpeed(timeSec))
                    .mapToDouble(TrafficUnitWrapper::getSpeed)
                    .average()
                    .getAsDouble();
            System.out.println("speed ("+trafficUnitsNumber+") = " + speed);
            return (double) Math.round(speed);
        }
        else{
            int tun = trafficUnitsNumber / 2;
            System.out.println("tun = " + tun);
            AverageSpeed as1 = new AverageSpeed(dateLocation, timeSec, tun, threshold);
            AverageSpeed as2 = new AverageSpeed(dateLocation, timeSec, tun, threshold);
            //return doForkJoin1(as1, as2);
            //return doForkJoin2(as1, as2);
            //return doInvoke(as1, as2);
            return doInvokeAll(as1, as2);
        }
    }

    private static double doForkJoin1(AverageSpeed as1, AverageSpeed as2){
        as1.fork(); //add to the queue
        double res1 = as1.join();  //wait until completed

        as2.fork();
        double res2 = as2.join();

        return (double) Math.round((res1 + res2) / 2);
    }

    private static double doForkJoin2(AverageSpeed as1, AverageSpeed as2){
        as1.fork();                   //add to the queue
        double res1 = as2.compute(); //get the result recursively
        double res2 = as1.join();   //wait until the queued task is done
        return (double) Math.round((res1 + res2) / 2);
    }

    private static double doInvoke(AverageSpeed as1, AverageSpeed as2) {
        double res1 = as1.invoke();
        double res2 = as2.invoke();
        return (double) Math.round((res1 + res2) / 2);
    }

    private static double doInvokeAll(AverageSpeed as1, AverageSpeed as2){
        return ForkJoinTask.invokeAll(List.of(as1, as2))
                .stream()
                .mapToDouble(ForkJoinTask::join)
                .map(Math::round)
                .average()
                .getAsDouble();
    }

    private class TrafficUnitWrapper {
        private double speed;
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
        public double getSpeed() { return speed; }

        TrafficUnit getTrafficUnit(){
            return this.trafficUnit;
        }

        public TrafficUnitWrapper calcSpeed(double timeSec) {
            double speed = this.vehicle.getSpeedMph(timeSec);
            this.speed = Math.round(speed * this.trafficUnit.getTraction());
            return this;
        }
    }


}
