package com.packt.cookbook.ch05_streams.api;

@FunctionalInterface
public interface SpeedModel {
    double getSpeedMph(double timeSec, int weightPounds, int horsePower);

    enum DrivingCondition {
        ROAD_CONDITION,
        TIRE_CONDITION
    }

    enum RoadCondition {
        DRY(1.0),
        WET(0.2){
            public double getTraction(){
                return temperature > 60 ? 0.4 : 0.2; }
        },
        SNOW(0.04);

        private double traction;
        RoadCondition(double traction){
            this.traction = traction;
        }
        public double getTraction(){return this.traction;}
        public static int temperature;
    }

    enum TireCondition {
        NEW(1.0),
        WORN(0.2);

        private double traction;
        TireCondition(double traction){
            this.traction = traction;
        }
        public double getTraction(){return this.traction;}
    }
}
