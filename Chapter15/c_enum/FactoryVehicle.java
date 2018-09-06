package com.packt.cookbook.ch15_new_way.c_enum;

import com.packt.cookbook.ch15_new_way.c_enum.api.Car;
import com.packt.cookbook.ch15_new_way.c_enum.api.SpeedModel;
import com.packt.cookbook.ch15_new_way.c_enum.api.Vehicle;

public class FactoryVehicle {
    public static Car buildCar(int passengersCount, int weightPounds, int horsePower){
        return new CarImpl(passengersCount, weightPounds, horsePower);
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
        public int getMaxWeightPounds(){ return getWeightPounds(); }
        public int getMaxWeight(WeigthUnit unit){ return getWeightPounds(); }
    }
    private abstract static class VehicleImpl implements Vehicle {
        private SpeedModel speedModel;
        private int weightPounds, horsePower;

        public VehicleImpl(int weightPounds, int horsePower) {
            this.weightPounds = weightPounds;
            this.horsePower = horsePower;
        }
        public void setSpeedModel(SpeedModel speedModel){
            this.speedModel = speedModel;
        }
        public double getSpeedMph(double timeSec){
            return this.speedModel.getSpeedMph(timeSec, weightPounds, horsePower);
        }
        public int getWeightPounds(){ return this.weightPounds; }
    }

}
