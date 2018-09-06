package com.packt.cookbook.ch02_oop.e_interface.a;

import com.packt.cookbook.ch02_oop.e_interface.a.api.Car;
import com.packt.cookbook.ch02_oop.e_interface.a.api.SpeedModel;
import com.packt.cookbook.ch02_oop.e_interface.a.api.Truck;
import com.packt.cookbook.ch02_oop.e_interface.a.api.Vehicle;

import java.util.Properties;

public class Chapter02Interface {

    public static void main(String arg[]) {
        demo1();
        demo2();
        demo3();
        demo4();
    }

    public static void demo1() {
        int horsePower = 246;
        int payload = Truck.convertKgToPounds(1500);
        int vehicleWeight = Truck.convertKgToPounds(1800);

        Truck truck = FactoryVehicle.buildTruck(payload, vehicleWeight, horsePower);

        System.out.println("Weight in pounds: " + vehicleWeight);
        int kg = truck.getWeightKg(vehicleWeight);
        System.out.println("Weight converted to kg: " + kg);
        System.out.println("Weight converted back to pounds: " + Truck.convertKgToPounds(kg));
    }

    public static void demo2() {
        int horsePower = 246;
        int payload = Truck.convertKgToPounds(1500);
        int vehicleWeight = Truck.convertKgToPounds(1800);

        Truck truck = FactoryVehicle.buildTruck(payload, vehicleWeight, horsePower);

        System.out.println("Payload in pounds: " + truck.getPayloadPounds());
        int kg = truck.getPayloadKg();
        System.out.println("Payload converted to kg: " + kg);
        System.out.println("Payload converted back to pounds: " + Truck.convertKgToPounds(kg));
    }

    public static void demo3() {
        int horsePower = 246;
        int payload = 3300;
        int vehicleWeight = 4000;

        Truck truck = FactoryVehicle.buildTruck(payload, vehicleWeight, horsePower);

        System.out.println("Payload in pounds: " + truck.getPayloadPounds());
        System.out.println("Payload in kg: " + truck.getPayloadKg());
    }

    public static void demo4() {
        double timeSec = 10.0;
        int horsePower = 246;
        int vehicleWeight = 4000;
        Properties drivingConditions = new Properties();
        drivingConditions.put("roadCondition", "Wet");
        drivingConditions.put("tireCondition", "New");
        SpeedModel speedModel = FactorySpeedModel.generateSpeedModel(drivingConditions);

        Car car = FactoryVehicle.buildCar(4, vehicleWeight, horsePower);
        car.setSpeedModel(speedModel);
        System.out.println("Car speed (" + timeSec + " sec)=" + car.getSpeedMph(timeSec) + " mph");

        Vehicle vehicle = FactoryVehicle.buildCrewCab(4, 3300, vehicleWeight, horsePower);
        vehicle.setSpeedModel(speedModel);

        System.out.println("Payload = " + ((Truck)vehicle).getPayloadPounds() + " pounds");
        System.out.println("Passengers count = " + ((Car)vehicle).getPassengersCount());
        System.out.println("Crew cab speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");
    }
}
