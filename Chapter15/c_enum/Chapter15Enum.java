package com.packt.cookbook.ch15_new_way.c_enum;

import com.packt.cookbook.ch15_new_way.c_enum.api.Car;
import com.packt.cookbook.ch15_new_way.c_enum.api.SpeedModel;
import com.packt.cookbook.ch15_new_way.c_enum.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch15_new_way.c_enum.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch15_new_way.c_enum.api.SpeedModel.DrivingCondition;
import java.util.Properties;

public class Chapter15Enum {

    public static void main(String arg[]) {
        demo1();
        demo2_traction();
        demo3_enumApi();
        demo4_values();
    }

    public static void demo1() {
        double timeSec = 10.0;
        String[] roadConditions = {RoadCondition.WET.toString(), RoadCondition.SNOW.toString()};
        String[] tireConditions = {TireCondition.NEW.toString(), TireCondition.WORN.toString()};
        RoadCondition.temperature = 63;
        for(String rc: roadConditions){
            for(String tc: tireConditions){
                Properties drivingConditions = new Properties();
                drivingConditions.put(DrivingCondition.ROAD_CONDITION.toString(), rc);
                drivingConditions.put(DrivingCondition.TIRE_CONDITION.toString(), tc);
                SpeedModel speedModel = FactorySpeedModel.generateSpeedModel(drivingConditions);

                Car car = FactoryVehicle.buildCar(4, 4000, 246);
                car.setSpeedModel(speedModel);
                System.out.println("Car speed (" + timeSec + " sec) = " + car.getSpeedMph(timeSec) + " mph");
            }
        }
    }

    public static void demo2_traction() {
        for(RoadCondition v: RoadCondition.values()){
            System.out.println(v + " => " + v.getTraction());
        }
    }

    public static void demo3_enumApi() {
        for(RoadCondition v: RoadCondition.values()){
            System.out.println(v.ordinal());
            System.out.println(v.name());
            System.out.println(RoadCondition.SNOW.equals(v));
        }
    }

    public static void demo4_values() {
        for(RoadCondition1 v: RoadCondition1.values()){
            System.out.println(v);
        }
    }

    public enum RoadCondition1 {
        DRY, WET, SNOW
    }
}
