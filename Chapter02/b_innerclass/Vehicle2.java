package com.packt.cookbook.ch02_oop.b_innerclass;

public class Vehicle2 {
    private int weightPounds;
    private int horsePower;

    public Vehicle2(int weightPounds, int horsePower) {
        this.weightPounds = weightPounds;
        this.horsePower = horsePower;
    }

    private int getWeightPounds() {
        return weightPounds;
    }

    public double getSpeedMph(double timeSec){
        class Engine {
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
        Engine engine = new Engine(this.horsePower);
        return engine.getSpeedMph(timeSec);
    }

}
