package com.packt.cookbook.ch02_oop.e_interface.b;

import com.packt.cookbook.ch02_oop.e_interface.b.api.Car;
import com.packt.cookbook.ch02_oop.e_interface.b.api.SpeedModel;
import com.packt.cookbook.ch02_oop.e_interface.b.api.Truck;
import com.packt.cookbook.ch02_oop.e_interface.b.api.Vehicle;

public class FactoryVehicle {
    public static Car buildCar(int passengersCount, int weightPounds, int horsePower){
        return new CarImpl(passengersCount, weightPounds, horsePower);
    }

    public static Truck buildTruck(int payloadPounds, int weightPounds, int horsePower){
        return new TruckImpl(payloadPounds, weightPounds, horsePower);
    }

    private static class CarImpl extends VehicleImpl implements Car {
        private int passengersCount;
        private CarImpl(int passengersCount, int weightPounds, int horsePower) {
            super(weightPounds + passengersCount * 250, horsePower);
            this.passengersCount = passengersCount;
        }
        public int getPassengersCount() {
            return this.passengersCount;
        }
    }
    private static class TruckImpl extends VehicleImpl implements Truck {
        private int payloadPounds;
        private TruckImpl(int payloadPounds, int weightPounds, int horsePower) {
            super(weightPounds+payloadPounds, horsePower);
            this.payloadPounds = payloadPounds;
        }
        public int getPayloadPounds(){ return payloadPounds; }
        //public static int convertKgToPounds(int kilograms){ return -1; }
        //public int getPayloadKg(){ return -2; }
    }
    private abstract static class VehicleImpl implements Vehicle {
        private SpeedModel speedModel;
        private int weightPounds, horsePower;

        private VehicleImpl(int weightPounds, int horsePower) {
            this.weightPounds = weightPounds;
            this.horsePower = horsePower;
        }
        public void setSpeedModel(SpeedModel speedModel){
            this.speedModel = speedModel;
        }
        public double getSpeedMph(double timeSec){
            return this.speedModel.getSpeedMph(timeSec, weightPounds, horsePower);
        }
        //public int getWeightPounds(){ return this.weightPounds; }
    }

}
