package com.packt.cookbook.ch04_functional.c.api;

public interface Vehicle {
    void setSpeedModel(SpeedModel speedModel);
    double getSpeedMph(double timeSec);
    int getWeightPounds();
    int getHorsePower();

    enum VehicleType {
        CAR("Car"),
        TRUCK("Truck"),
        CAB_CREW("CabCrew");

        private String type;
        private VehicleType(String type){
            this.type = type;
        }
        public String getType(){return this.type;}

        @Override
        public String toString() {
            return this.name() + ":" + this.getType() ;
        }
    }

}
