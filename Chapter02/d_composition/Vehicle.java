package com.packt.cookbook.ch02_oop.d_composition;

public class Vehicle {
    private SpeedModel speedModel;
    private int weightPounds, horsePower;

    public Vehicle(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }
    public void setSpeedModel(SpeedModel speedModel){
        this.speedModel = speedModel;
    }
    public double getSpeedMph(double timeSec){
        return this.speedModel.getSpeedMph(timeSec, weightPounds, horsePower);
    }
}
