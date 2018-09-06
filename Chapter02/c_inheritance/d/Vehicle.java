package com.packt.cookbook.ch02_oop.c_inheritance.d;

public abstract class Vehicle {
    private int weightPounds, horsePower;

    public Vehicle(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }

    public abstract int getMaxWeightPounds();

    public double getSpeedMph(double timeSec){
        double v = 2.0*this.horsePower*746;
        v = v*timeSec*32.174/getMaxWeightPounds();
        return Math.round(Math.sqrt(v)*0.68);
    }

}
