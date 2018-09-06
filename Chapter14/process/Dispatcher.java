package com.packt.cookbook.ch14_testing.process;

import com.packt.cookbook.ch14_testing.factories.DateLocation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class Dispatcher {

    public static void dispatch(int trafficUnitsNumber, double timeSec, DateLocation dateLocation, double[] speedLimitByLane) {

        ExecutorService execService =  ForkJoinPool.commonPool();
        try (SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>()){
            subscribe(publisher, execService, Process.AVERAGE_SPEED, timeSec, dateLocation, speedLimitByLane);
            subscribe(publisher, execService, Process.TRAFFIC_DENSITY, timeSec, dateLocation, speedLimitByLane);
            publisher.submit(trafficUnitsNumber);
        } finally {
            try {
                execService.shutdown();
                execService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (Exception ex) {
                System.out.println("Caught around execService.awaitTermination(): " + ex.getClass().getName());
            } finally {
                execService.shutdownNow();
            }
        }
    }

    private static void subscribe(SubmissionPublisher<Integer> publisher, ExecutorService execService,
                                  Process process, double timeSec, DateLocation dateLocation, double[] speedLimitByLane){
        Processor<Integer> subscriber =
                new Processor<>(process, timeSec, dateLocation, speedLimitByLane);
        Subscription subscription = new Subscription(subscriber, execService);
        subscriber.onSubscribe(subscription);
        publisher.subscribe(subscriber);
    }
}
