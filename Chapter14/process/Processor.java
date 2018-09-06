package com.packt.cookbook.ch14_testing.process;

import com.packt.cookbook.ch14_testing.AverageSpeed;
import com.packt.cookbook.ch14_testing.TrafficDensity;
import com.packt.cookbook.ch14_testing.factories.DateLocation;
import com.packt.cookbook.ch14_testing.utils.DbUtil;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Processor<T> implements Flow.Subscriber<T> {
    private String result;
    private double timeSec;
    private Process process;
    private int trafficUnitsNumber;
    private double[] speedLimitByLane;
    private DateLocation dateLocation;
    private Flow.Subscription subscription;

    public Processor(Process process, double timeSec, DateLocation dateLocation, double[] speedLimitByLane){
        this.process = process;
        this.timeSec = timeSec;
        this.dateLocation = dateLocation;
        this.speedLimitByLane = speedLimitByLane;
    }

    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(0);
    }

    public void onNext(T item) {
        if(item != null) {
            trafficUnitsNumber = (int)item;
            switch (process){
                case AVERAGE_SPEED:
                    calcAverageSpeed(trafficUnitsNumber);
                    break;
                case TRAFFIC_DENSITY:
                    calcTrafficDensity(trafficUnitsNumber);
            }
        }
        this.subscription.request(1);
    }

    public void onError(Throwable ex) {
        ex.printStackTrace();
    }

    public void onComplete() {
        System.out.println(process + "(" + trafficUnitsNumber + "): " + result);
        DbUtil.storeResult(process.name(), trafficUnitsNumber, timeSec, dateLocation.toString(), speedLimitByLane, result);
    }

    private void calcAverageSpeed(int trafficUnitsNumber){
        result = IntStream.rangeClosed(1, speedLimitByLane.length).mapToDouble(i -> {
            AverageSpeed averageSpeed = new AverageSpeed(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane, i, 100);
            ForkJoinPool commonPool = ForkJoinPool.commonPool();
            return commonPool.invoke(averageSpeed);
        }).mapToObj(Double::toString).collect(Collectors.joining(", "));
    }

    private void calcTrafficDensity(int trafficUnitsNumber){
        result = Arrays.stream(new TrafficDensity().trafficByLane(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane))
        .map(Object::toString).collect(Collectors.joining(", "));
    }

}
