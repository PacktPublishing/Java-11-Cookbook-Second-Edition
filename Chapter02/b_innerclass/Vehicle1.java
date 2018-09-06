package com.packt.cookbook.ch02_oop.b_innerclass;

public class Vehicle1 {
    private int weightPounds;
    private Engine engine;
    public Vehicle1(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.engine = new Engine(horsePower);
    }

    private int getWeightPounds() {
        return weightPounds;
    }

    public double getSpeedMph(double timeSec){
        return this.engine.getSpeedMph(timeSec);
    }

    private class Engine {
        private int horsePower;

        private Engine(int horsePower) {
            this.horsePower = horsePower;
        }

        private double getSpeedMph(double timeSec){
            double v = 2.0*this.horsePower*746;
            v = v*timeSec*32.174/getWeightPounds();
            return Math.round(Math.sqrt(v)*0.68);
        }
    }
}
