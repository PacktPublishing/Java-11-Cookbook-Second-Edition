package com.packt.cookbook.ch02_oop.b_innerclass;

public class Chapter02InnerClasses {

    public static void main(String... args) {
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;

        Vehicle vehicle = new Vehicle(vehicleWeightPounds, engineHorsePower);
        System.out.println("Vehicle speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");

        Vehicle1 vehicle1 = new Vehicle1(vehicleWeightPounds, engineHorsePower);
        System.out.println("Vehicle speed (" + timeSec + " sec) = " + vehicle1.getSpeedMph(timeSec) + " mph");

        Vehicle2 vehicle2 = new Vehicle2(vehicleWeightPounds, engineHorsePower);
        System.out.println("Vehicle speed (" + timeSec + " sec) = " + vehicle2.getSpeedMph(timeSec) + " mph");

        vehicle = new Vehicle(vehicleWeightPounds, engineHorsePower){
            public double getSpeedMph(double timeSec){
                return -1.0d;
            }
        };
        printTheSpeed(timeSec, vehicle);

        VehicleInterface vehicleObject = new VehicleInterface(){
            public double getSpeedMph(double timeSec){
                return -1.0d;
            }
        };
        printTheSpeed(timeSec, vehicleObject);

        printTheSpeed(timeSec, x -> 11.0 );
    }

    public static void printTheSpeed(double timeSec, Vehicle vehicle){
        System.out.println("Vehicle speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");
    }

    public static void printTheSpeed(double timeSec, VehicleInterface vehicle){
        System.out.println("Vehicle speed (" + timeSec + " sec) = " + vehicle.getSpeedMph(timeSec) + " mph");
    }

    public interface VehicleInterface {
        double getSpeedMph(double timeSec);
    }

}
