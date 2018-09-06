package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.Car;
import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Truck;
import com.packt.cookbook.ch05_streams.api.Vehicle;

public class FactoryVehicle {
    public static Vehicle build(TrafficUnit trafficUnit){
        switch (trafficUnit.getVehicleType()){
            case CAR:
                return new CarImpl(trafficUnit.getPassengersCount(), trafficUnit.getWeightPounds() , trafficUnit.getHorsePower());
            case TRUCK:
                return new TruckImpl(trafficUnit.getPayloadPounds(), trafficUnit.getWeightPounds() , trafficUnit.getHorsePower());
            case CAB_CREW:
                return new CrewCabImpl(trafficUnit.getPassengersCount(), trafficUnit.getPayloadPounds(), trafficUnit.getWeightPounds() , trafficUnit.getHorsePower());
            default:
                System.out.println("Unexpected vehicle type " + trafficUnit.getVehicleType());
                return new CrewCabImpl(trafficUnit.getPassengersCount(), trafficUnit.getPayloadPounds(), trafficUnit.getWeightPounds() , trafficUnit.getHorsePower());
        }
    }

    private static Truck buildTruck(int payloadPounds, int weightPounds, int horsePower){
        return new TruckImpl(payloadPounds, weightPounds, horsePower);
    }

    private static Vehicle buildCrewCab(int passengersCount, int payload, int weightPounds, int horsePower){
        return new CrewCabImpl(passengersCount, payload, weightPounds, horsePower);
    }
    private static class CarImpl extends VehicleImpl implements Car {
        private int passengersCount;
        private CarImpl(int passengersCount, int weightPounds, int horsePower) {
            super(weightPounds + passengersCount, horsePower);
            this.passengersCount = passengersCount;
        }
        public int getPassengersCount() {
            return this.passengersCount;
        }
    }
    private static class TruckImpl extends VehicleImpl implements Truck {
        private int payloadPounds;
        private TruckImpl(int payloadPounds, int weightPounds, int horsePower) {
            super(weightPounds+payloadPounds, horsePower);
            this.payloadPounds = payloadPounds;
        }
        public int getPayloadPounds(){ return payloadPounds; }
    }
    private static class CrewCabImpl extends VehicleImpl implements Car, Truck {
        private int payloadPounds;
        private int passengersCount;
        private CrewCabImpl(int passengersCount, int payloadPounds, int weightPounds, int horsePower) {
            super(weightPounds+payloadPounds+passengersCount*250, horsePower);
            this.payloadPounds = payloadPounds;
            this.passengersCount = passengersCount;
        }
        public int getPayloadPounds(){ return payloadPounds; }
        public int getPassengersCount() {
            return this.passengersCount;
        }
    }
    private abstract static class VehicleImpl implements Vehicle {
        private SpeedModel speedModel;
        private int weightPounds, horsePower;

        private VehicleImpl(int weightPounds, int horsePower) {
            this.weightPounds = weightPounds;
            this.horsePower = horsePower;
        }
        public void setSpeedModel(SpeedModel speedModel){
            this.speedModel = speedModel;
        }
        public double getSpeedMph(double timeSec){
            return this.speedModel.getSpeedMph(timeSec, weightPounds, horsePower);
        }
        public int getHorsePower(){ return this.horsePower; }
        public int getWeightPounds(){ return this.weightPounds; }
    }

}
