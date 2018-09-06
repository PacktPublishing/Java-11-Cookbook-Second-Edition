package com.packt.cookbook.ch02_oop.c_inheritance.c;

public class Truck extends Vehicle {
    private int payload, weightPounds;

    public Truck(int payloadPounds, int weightPounds, int horsePower) {
        super(weightPounds, horsePower);
        this.payload = payloadPounds;
        this.weightPounds = weightPounds;
    }

    public int getPayload() { return this.payload; }

    public double getSpeedMph(double timeSec) {
        int weight = this.weightPounds + this.payload;
        return getSpeedMph(timeSec, weight);
    }
}