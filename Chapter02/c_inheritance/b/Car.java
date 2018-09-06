package com.packt.cookbook.ch02_oop.c_inheritance.b;

public class Car extends Vehicle {
    private int passengersCount, weightPounds, horsePower;
    public Car(int passengersCount, int weightPounds, int horsePower) {
        super(weightPounds, horsePower);
        this.passengersCount = passengersCount;
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }
    public int getPassengersCount() {
        return this.passengersCount;
    }
    public double getSpeedMph(double timeSec) {
        int weight = this.weightPounds + this.passengersCount * 250;
        double v = 2.0*this.horsePower*746;
        v = v*timeSec*32.174/weight;
        return Math.round(Math.sqrt(v)*0.68);
        //Car-specific calculations
//        return -1d;
    }
}
