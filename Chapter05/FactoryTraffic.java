package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch05_streams.api.SpeedModel.TireCondition;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle.VehicleType;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FactoryTraffic {

    public static Stream<TrafficUnit> getTrafficUnitStream(int trafficUnitsNumber, Month month, DayOfWeek dayOfWeek, int hour, String country, String city, String trafficLight){
        return IntStream.range(0, trafficUnitsNumber).mapToObj(i -> generateOneUnit(month, dayOfWeek, hour, country, city, trafficLight));
    }

    public static List<TrafficUnit> generateTraffic(int trafficUnitsNumber, Month month, DayOfWeek dayOfWeek, int hour, String country, String city, String trafficLight){
        return IntStream.range(0, trafficUnitsNumber).mapToObj(i -> generateOneUnit(month, dayOfWeek, hour, country, city, trafficLight))
                .collect(Collectors.toList());
    }
    public static TrafficUnit generateOneUnit(Month month, DayOfWeek dayOfWeek, int hour, String country, String city, String trafficLight){
        double r0 = Math.random(); VehicleType vehicleType = r0<0.4?VehicleType.CAR:(r0>0.6?VehicleType.TRUCK: VehicleType.CAB_CREW);
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
