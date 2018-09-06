package com.packt.cookbook.ch02_oop.c_inheritance.a;

public class Vehicle {
    private int weightPounds, horsePower;

    public Vehicle(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }

    public double getSpeedMph(double timeSec){
        double v = 2.0*this.horsePower*746;
        v = v*timeSec*32.174/weightPounds;
        return Math.round(Math.sqrt(v)*0.68);
    }
}
