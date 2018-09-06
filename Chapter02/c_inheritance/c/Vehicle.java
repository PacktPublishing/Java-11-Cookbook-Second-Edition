package com.packt.cookbook.ch02_oop.c_inheritance.c;

public class Vehicle {
    private int weightPounds, horsePower;

    public Vehicle(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }

    protected double getSpeedMph(double timeSec, int weightPounds){
        double v = 2.0*this.horsePower*746;
        v = v*timeSec*32.174/weightPounds;
        return Math.round(Math.sqrt(v)*0.68);
    }
}
