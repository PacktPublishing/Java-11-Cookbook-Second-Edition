package com.packt.cookbook.ch02_oop.a_classes;

public class Chapter02Classes {

    public static void main(String... args) {
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;

        Engine engine = new Engine();
        engine.setHorsePower(engineHorsePower);

        Vehicle vehicle = new Vehicle(vehicleWeightPounds, engine);
        System.out.println("Vehicle speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");

        vehicle = new Car(4, vehicleWeightPounds, engine);

        System.out.println("Car speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");

    }
}
