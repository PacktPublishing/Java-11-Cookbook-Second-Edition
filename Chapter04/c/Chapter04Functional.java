package com.packt.cookbook.ch04_functional.c;

import com.packt.cookbook.ch04_functional.c.api.SpeedModel;
import com.packt.cookbook.ch04_functional.c.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch04_functional.c.api.TrafficUnit;
import com.packt.cookbook.ch04_functional.c.api.Vehicle;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Chapter04Functional {

    public static void main(String... args) {
        demo1_speedModel();
        demo2_API();
    }

    public static void demo1_speedModel() {

        double timeSec = 10.0;
        TrafficUnit trafficUnit = FactoryTraffic.getOneUnit(Month.APRIL, DayOfWeek.FRIDAY, 17, "USA", "Denver", "Main103S");
        Vehicle vehicle = FactoryVehicle.build(trafficUnit);
        SpeedModel speedModel = FactorySpeedModel.generateSpeedModel(trafficUnit);
        vehicle.setSpeedModel(speedModel);
        printResult(trafficUnit, timeSec, vehicle.getSpeedMph(timeSec));

        speedModel =  (t, wp, hp) -> calculateSpeed(trafficUnit, t, wp, hp);
        vehicle.setSpeedModel(speedModel);
        printResult(trafficUnit, timeSec, vehicle.getSpeedMph(timeSec));

        speedModel =  (t, wp, hp) -> {
            double tr = trafficUnit.getTraction();
            double weightPower = 2.0 * hp * 746 * 32.174 / wp;
            return Math.round(Math.sqrt(t * weightPower) * 0.68 * tr);
        };
        vehicle.setSpeedModel(speedModel);
        printResult(trafficUnit, timeSec, vehicle.getSpeedMph(timeSec));
    }

    private static void printResult(TrafficUnit tu, double timeSec, double speedMph){
        System.out.println("Road " + tu.getRoadCondition() + ", tires " + tu.getTireCondition()
                + ": " + tu.getVehicleType().getType() + " speedMph (" + timeSec + " sec)=" + speedMph + " mph");
    }

    private static double calculateSpeed(TrafficUnit tu, double t, int wp, int hp){
        double weightPower = 2.0 * hp * 746 * 32.174 / wp;
        return Math.round(Math.sqrt(t * weightPower) * 0.68 * tu.getTraction());
    }

    public static void demo2_API() {
        Traffic api = new TrafficImpl(Month.APRIL, DayOfWeek.FRIDAY, 17, "USA", "Denver", "Main103S");
        double timeSec = 10.0;
        int trafficUnitsNumber = 10;
        api.speedAfterStart(timeSec, trafficUnitsNumber);

        SpeedModel speedModel =  (t, wp, hp) -> {
            double weightPower = 2.0 * hp * 746 * 32.174 / wp;
            return Math.round(Math.sqrt(t * weightPower) * 0.68);
        };
        api.speedAfterStart(timeSec, trafficUnitsNumber, speedModel);

        Predicate<TrafficUnit> limitTraffic = tu ->
                (tu.getHorsePower() < 250 && tu.getVehicleType() == Vehicle.VehicleType.CAR)
                        || (tu.getHorsePower() < 400 && tu.getVehicleType() == Vehicle.VehicleType.TRUCK);

        Predicate<TrafficUnit> limitTraffic2 = tu ->
                tu.getRoadCondition() == RoadCondition.WET
                        && tu.getTireCondition() == SpeedModel.TireCondition.NEW
                        && tu.getTemperature() > 65;

        api.speedAfterStart(timeSec, trafficUnitsNumber, speedModel, limitTraffic);

        BiPredicate<TrafficUnit, Double> limitTrafficAndSpeed = (tu, sp) ->
                (sp > (tu.getSpeedLimitMph() + 8.0) && tu.getRoadCondition() == RoadCondition.DRY)
                        || (sp > (tu.getSpeedLimitMph() + 5.0) && tu.getRoadCondition() == RoadCondition.WET)
                        || (sp > (tu.getSpeedLimitMph() + 0.0) && tu.getRoadCondition() == RoadCondition.SNOW);

        api.speedAfterStart(timeSec, trafficUnitsNumber, speedModel, limitTrafficAndSpeed);

        BiConsumer<TrafficUnit, Double> printResults = (tm, sp) ->
                System.out.println("Road " + tm.getRoadCondition() + ", tires " + tm.getTireCondition()
                        + ": " + tm.getVehicleType().getType() + " speedMph (" + timeSec + " sec)=" + sp + " mph");

        api.speedAfterStart(timeSec, trafficUnitsNumber, speedModel, limitTrafficAndSpeed, printResults);
    }

    public interface Traffic {
        void speedAfterStart(double timeSec, int trafficUnitsNumber);
        void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel);
        void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel, Predicate<TrafficUnit> limitTraffic);
        void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel, BiPredicate<TrafficUnit,Double> limitTraffic);
        void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel,
                             BiPredicate<TrafficUnit,Double> limitTraffic, BiConsumer<TrafficUnit, Double> printResult);
    }

    public static class TrafficImpl implements Traffic {
        private int hour;
        private Month month;
        private DayOfWeek dayOfWeek;
        private String country, city, trafficLight;

        public TrafficImpl(Month month, DayOfWeek dayOfWeek, int hour, String country, String city, String trafficLight){
            this.month = month;
            this.dayOfWeek = dayOfWeek;
            this.hour = hour;
            this.country = country;
            this.city = city;
            this.trafficLight = trafficLight;
        }

        public void speedAfterStart(double timeSec, int trafficUnitsNumber) {
            List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber,
                    month, dayOfWeek, hour, country, city, trafficLight);
            for(TrafficUnit tu: trafficUnits){
                Vehicle vehicle = FactoryVehicle.build(tu);
                SpeedModel speedModel = FactorySpeedModel.generateSpeedModel(tu);
                vehicle.setSpeedModel(speedModel);
                double speed = vehicle.getSpeedMph(timeSec);
                printResult(tu, timeSec, speed);
            }
        }
        public void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel) {
            List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber,
                    month, dayOfWeek, hour, country, city, trafficLight);
            for(TrafficUnit tu: trafficUnits){
                Vehicle vehicle = FactoryVehicle.build(tu);
                vehicle.setSpeedModel(speedModel);
                double speed = vehicle.getSpeedMph(timeSec);
                speed = Math.round(speed * tu.getTraction());
                printResult(tu, timeSec, speed);
            }
        }
        public void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel,
                                    Predicate<TrafficUnit> limitTraffic) {
            List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber,
                    month, dayOfWeek, hour, country, city, trafficLight);
            for(TrafficUnit tu: trafficUnits){
                if(limitTraffic.test(tu)){
                    Vehicle vehicle = FactoryVehicle.build(tu);
                    vehicle.setSpeedModel(speedModel);
                    double speed = vehicle.getSpeedMph(timeSec);
                    speed = Math.round(speed * tu.getTraction());
                    printResult(tu, timeSec, speed);
                }
            }
        }

        public void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel,
                                    BiPredicate<TrafficUnit, Double> limitTrafficAndSpeed) {
            List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber,
                    month, dayOfWeek, hour, country, city, trafficLight);
            for(TrafficUnit tu: trafficUnits){
                Vehicle vehicle = FactoryVehicle.build(tu);
                vehicle.setSpeedModel(speedModel);
                double speed = vehicle.getSpeedMph(timeSec);
                speed = Math.round(speed * tu.getTraction());
                if(limitTrafficAndSpeed.test(tu, speed)){
                    printResult(tu, timeSec, speed);
                }
            }
        }
        public void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel,
            BiPredicate<TrafficUnit, Double> limitSpeed, BiConsumer<TrafficUnit, Double> printResult) {
            List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber,
                    month, dayOfWeek, hour, country, city, trafficLight);
            for(TrafficUnit tu: trafficUnits){
                Vehicle vehicle = FactoryVehicle.build(tu);
                vehicle.setSpeedModel(speedModel);
                double speed = vehicle.getSpeedMph(timeSec);
                speed = Math.round(speed * tu.getTraction());
                if(limitSpeed.test(tu, speed)){
                    printResult.accept(tu, speed);
                }
            }
        }
    }

}


