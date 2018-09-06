package com.packt.cookbook.ch02_oop.c_inheritance.e;

public class Truck extends Vehicle {
    private int payload;

    public Truck(int payloadPounds, int weightPounds, int horsePower) {
        super(weightPounds + payloadPounds, horsePower);
        this.payload = payloadPounds;
    }

    public int getPayload() { return this.payload; }
}