package com.packt.cookbook.ch14_testing.factories;

import com.packt.cookbook.ch14_testing.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch14_testing.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch14_testing.api.Vehicle.VehicleType;
import com.packt.cookbook.ch14_testing.api.TrafficUnit;
import com.packt.cookbook.ch14_testing.utils.DbUtil;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FactoryTraffic {
    public static boolean readDataFromDb = false;
    private static boolean switchToRealData = false;
    public static Stream<TrafficUnit> getTrafficUnitStream(DateLocation dl, int trafficUnitsNumber){
        if(readDataFromDb){
            if(!DbUtil.isEnoughData(trafficUnitsNumber)){
                System.out.println("Not enough data");
                return new ArrayList<TrafficUnit>().stream();
            }
            return readDataFromDb(trafficUnitsNumber);
        }
        if(switchToRealData){
            return getRealData(dl,  trafficUnitsNumber);
        } else {
            return IntStream.range(0, trafficUnitsNumber).mapToObj(i -> generateOneUnit());
        }
    }

    private static Stream<TrafficUnit> readDataFromDb(int trafficUnitsNumber){
        return DbUtil.selectData(trafficUnitsNumber).stream();
    }

    private static Stream<TrafficUnit> getRealData(DateLocation dl, int trafficUnitsNumber) {
        //connect to the source of the real data and request the flow or collection of data
        return new ArrayList<TrafficUnit>().stream();
    }

    private static TrafficUnit generateOneUnit(){
        double r0 = Math.random();
        VehicleType vehicleType = r0<0.4?VehicleType.CAR:(r0>0.6?VehicleType.TRUCK: VehicleType.CAB_CREW);
        double r1 = Math.random();
        double r2 = Math.random();
        double r3 = Math.random();
        return new TrafficModelImpl(vehicleType, gen(4,1), gen(3300,1000), gen(246,100), gen(4000,2000),
                (r1>0.5? RoadCondition.WET: RoadCondition.DRY), (r2>0.5? TireCondition.WORN: TireCondition.NEW),
                r1>0.5?(r3>0.5?63:50):63);
    }

    private static int gen(int i1, int i2){
        double r = Math.random();
        return (int)Math.rint(r * i1) + i2;
    }

    private static class TrafficModelImpl implements TrafficUnit {
        private int passengersCount, payloadPounds, horsePower, weightPounds, temperature;
        private RoadCondition roadCondition;
        private TireCondition tireCondition;
        private VehicleType vehicleType;

        public TrafficModelImpl(VehicleType vehicleType, int passengersCount, int payloadPounds, int engineHorsePower, int vehicleWeightPounds,
                                RoadCondition roadCondition, TireCondition tireCondition, int temperatureFarenheit) {
            this.vehicleType = vehicleType;
            this.passengersCount = passengersCount;
            this.payloadPounds = payloadPounds;
            this.horsePower = engineHorsePower;
            this.weightPounds = vehicleWeightPounds;
            this.roadCondition = roadCondition;
            this.tireCondition = tireCondition;
            this.temperature = temperatureFarenheit;
        }

        public VehicleType getVehicleType() { return vehicleType; }

        public int getPassengersCount() {
            return passengersCount;
        }

        public int getPayloadPounds() { return payloadPounds; }

        public int getHorsePower() {
            return horsePower;
        }

        public int getWeightPounds() {
            return weightPounds;
        }

        public double getTraction() {
            RoadCondition.temperature = getTemperature();
            double rt = getRoadCondition().getTraction();
            double tt = getTireCondition().getTraction();
            return rt*tt;
        }

        public RoadCondition getRoadCondition() {
            return roadCondition;
        }

        public TireCondition getTireCondition() {
            return tireCondition;
        }

        public int getTemperature() {
            return temperature;
        }

        public double getSpeedLimitMph() {
            return 55.0;
        }

        @Override
        public String toString() {
            return "TrafficUnit{ " + vehicleType.getType() +
                    ", passengers=" + passengersCount +
                    ", payloadPounds=" + payloadPounds +
                    ", horsePower=" + horsePower +
                    ", weightPounds=" + weightPounds +
                    ", temperature=" + temperature +
                    ", roadCondition=" + roadCondition +
                    ", tireCondition=" + tireCondition +
                    " }";
        }
    }

}
