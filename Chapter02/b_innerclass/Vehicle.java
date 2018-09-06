package com.packt.cookbook.ch02_oop.b_innerclass;

public class Vehicle {
    private int weightPounds;
    private Engine engine;

    public Vehicle(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.engine = new Engine(horsePower);
    }

    public double getSpeedMph(double timeSec){
        double v = 2.0*this.engine.getHorsePower()*746;
        v = v*timeSec*32.174/this.weightPounds;
        return Math.round(Math.sqrt(v)*0.68);
    }

    private class Engine {
        private int horsePower;
        private Engine(int horsePower) {
            this.horsePower = horsePower;
        }
        public int getHorsePower() { return horsePower; }
    }
}
