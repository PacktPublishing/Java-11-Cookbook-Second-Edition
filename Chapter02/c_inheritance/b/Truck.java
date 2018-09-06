package com.packt.cookbook.ch02_oop.c_inheritance.b;

public class Truck extends Vehicle {
    private int payload, weightPounds, horsePower;

    public Truck(int payloadPounds, int weightPounds, int horsePower) {
        super(weightPounds, horsePower);
        this.payload = payloadPounds;
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }

    public int getPayload() { return this.payload; }

    public double getSpeedMph(double timeSec) {
        int weight = this.weightPounds + this.payload;
        double v = 2.0*this.horsePower*746;
        v = v*timeSec*32.174/weight;
        return Math.round(Math.sqrt(v)*0.68);
    }
}