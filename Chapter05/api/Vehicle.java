package com.packt.cookbook.ch05_streams.api;

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
        VehicleType(String type){
            this.type = type;
        }
        public String getType(){return this.type;}
        }
}
