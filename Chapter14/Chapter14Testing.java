package com.packt.cookbook.ch14_testing;

import com.packt.cookbook.ch14_testing.factories.DateLocation;
import com.packt.cookbook.ch14_testing.process.Dispatcher;
import com.packt.cookbook.ch14_testing.process.Process;
import com.packt.cookbook.ch14_testing.utils.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chapter14Testing {
    private static DateLocation dateLocation = new DateLocation(Month.APRIL, DayOfWeek.FRIDAY, 17,
            "USA", "Denver", "Main103S");
    private static double timeSec = 10.0;
    private static int trafficUnitsNumber = 1000;
    private static double[] speedLimitByLane = {15, 35, 55};

    public static void main(String... args) {
        //demo1_class_level_integration();
        //demo2_subsystem_level_integration();
        demo3_prepare_for_integration_testing();
    }

    private static void demo1_class_level_integration() {
        String result = IntStream.rangeClosed(1, speedLimitByLane.length).mapToDouble(i -> {
            AverageSpeed averageSpeed = new AverageSpeed(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane, i,100);
            ForkJoinPool commonPool = ForkJoinPool.commonPool();
            return commonPool.invoke(averageSpeed);
        }).mapToObj(Double::toString).collect(Collectors.joining(", "));
        System.out.println("Average speed = " + result);

        TrafficDensity trafficDensity = new TrafficDensity();
        Integer[] trafficByLane = trafficDensity.trafficByLane(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane );
        System.out.println("Traffic density = " + Arrays.stream(trafficByLane).map(Object::toString).collect(Collectors.joining(", ")));
    }

    private static void demo2_subsystem_level_integration() {
        DbUtil.createResultTable();
        DbUtil.createDataTables();

        Dispatcher.dispatch(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane);

        try {
            Thread.sleep(3000l);
        } catch (InterruptedException ex) {}

        Arrays.stream(Process.values()).forEach(v -> {
            System.out.println(v.name() + ": " + DbUtil.selectResult(v.name()));
        });
    }

    //Run this method only once
    private static void demo3_prepare_for_integration_testing() {
        DbUtil.createResultTable();
        DbUtil.createDataTables();
        TrafficDensity.recordData = true;
        try(Connection conn = DbUtil.getDbConnection()){
            TrafficDensity.conn = conn;
            Dispatcher.dispatch(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}


