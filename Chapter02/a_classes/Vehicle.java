package com.packt.cookbook.ch02_oop.a_classes;

public class Vehicle {
    private int weightPounds;
    private Engine engine;

    public Vehicle(int weightPounds, Engine engine) {
        this.weightPounds = weightPounds;
        if(engine == null){
            throw new RuntimeException("Engine value is not set.");
        }
        this.engine = engine;
    }

    public int getWeightPounds() {
        return weightPounds;
    }

    public Engine getEngine() { return engine; }

    protected double getSpeedMph(double timeSec){
        double v = 2.0*this.engine.getHorsePower()*746;
        v = v*timeSec*32.174/this.weightPounds;
        return Math.round(Math.sqrt(v)*0.68);
    }

}
