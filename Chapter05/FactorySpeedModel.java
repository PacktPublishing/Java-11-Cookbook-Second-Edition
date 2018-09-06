package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;

public class FactorySpeedModel {
    public static SpeedModel generateSpeedModel(TrafficUnit trafficUnit){
        return new SpeedModelImpl(trafficUnit);
    }
    public static SpeedModel getSpeedModel(){
        return SpeedModelImpl.getSpeedModel();
    }
    private static class SpeedModelImpl implements SpeedModel{
        private TrafficUnit trafficUnit;

        private SpeedModelImpl(TrafficUnit trafficUnit){
            this.trafficUnit = trafficUnit;
        }

        public double getSpeedMph1(double timeSec, int weightPounds, int horsePower){
            double traction = trafficUnit.getTraction();
            double v = 2.0 * horsePower * 746 * timeSec * 32.174 / weightPounds;
            return Math.round(Math.sqrt(v) * 0.68 * traction);
        }

        public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
            double speed = getSpeedModel().getSpeedMph(timeSec, weightPounds, horsePower);
            return Math.round(speed * trafficUnit.getTraction());
        }

        public static SpeedModel getSpeedModel(){
            return  (t, wp, hp) -> {
                double weightPower = 2.0 * hp * 746 * 32.174 / wp;
                return Math.round(Math.sqrt(t * weightPower) * 0.68);
            };
        }
    }
}

