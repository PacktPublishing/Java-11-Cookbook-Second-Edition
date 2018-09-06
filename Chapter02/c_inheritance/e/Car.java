package com.packt.cookbook.ch02_oop.c_inheritance.e;

public class Car extends Vehicle {
    private int passengersCount;
    public Car(int passengersCount, int weightPounds, int horsePower) {
        super(weightPounds + passengersCount * 250, horsePower);
        this.passengersCount = passengersCount;
    }
    public int getPassengersCount() {
        return this.passengersCount;
    }
}
