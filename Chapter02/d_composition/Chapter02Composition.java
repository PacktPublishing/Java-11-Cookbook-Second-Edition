package com.packt.cookbook.ch02_oop.d_composition;

import java.util.Properties;

public class Chapter02Composition {

    public static void main(String arg[]) {
        double timeSec = 10.0;
        int horsePower = 246;
        int vehicleWeight = 4000;

        Properties drivingConditions = new Properties();
        drivingConditions.put("roadCondition", "Wet");
        drivingConditions.put("tireCondition", "New");
        SpeedModel speedModel = new SpeedModel(drivingConditions);

        Car car = new Car(4, vehicleWeight, horsePower);
        car.setSpeedModel(speedModel);
        System.out.println("Car speed (" + timeSec + " sec) = " + car.getSpeedMph(timeSec) + " mph");
    }


}
