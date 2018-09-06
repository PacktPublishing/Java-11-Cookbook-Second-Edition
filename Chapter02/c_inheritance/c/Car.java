package com.packt.cookbook.ch02_oop.c_inheritance.c;

public class Car extends Vehicle {
    private int passengersCount, weightPounds;
    public Car(int passengersCount, int weightPounds, int horsePower) {
        super(weightPounds, horsePower);
        this.passengersCount = passengersCount;
        this.weightPounds = weightPounds;
    }
    public int getPassengersCount() {
        return this.passengersCount;
    }
    public double getSpeedMph(double timeSec) {
        int weight = this.weightPounds + this.passengersCount * 250;
        return getSpeedMph(timeSec, weight);
    }
}
