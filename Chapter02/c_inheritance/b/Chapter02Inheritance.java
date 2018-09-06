package com.packt.cookbook.ch02_oop.c_inheritance.b;

public class Chapter02Inheritance {

    public static void main(String... args) {
        demo1_baseClass();
        demo2_children();
    }

    public static void demo1_baseClass() {
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;

        Vehicle vehicle = new Car(4, vehicleWeightPounds, engineHorsePower);
        System.out.println("Passengers count=" + ((Car)vehicle).getPassengersCount());
        System.out.println("Car speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");

        vehicle = new Truck(3300, vehicleWeightPounds, engineHorsePower);
        System.out.println("Payload=" + ((Truck)vehicle).getPayload() + " pounds");
        System.out.println("Truck speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");
    }

    public static void demo2_children() {
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;

        Car car = new Car(4, vehicleWeightPounds, engineHorsePower);
        System.out.println("Passengers count=" + car.getPassengersCount());
        System.out.println("Car speed (" + timeSec + " sec) = " + car.getSpeedMph(timeSec) + " mph");

        Truck truck = new Truck(3300, vehicleWeightPounds, engineHorsePower);
        System.out.println("Payload=" + truck.getPayload() + " pounds");
        System.out.println("Truck speed (" + timeSec + " sec) = " + truck.getSpeedMph(timeSec) + " mph");
    }

}
