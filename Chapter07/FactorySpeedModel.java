package com.packt.cookbook.ch07_concurrency;

import com.packt.cookbook.ch07_concurrency.api.SpeedModel;
import com.packt.cookbook.ch07_concurrency.api.TrafficUnit;

public class FactorySpeedModel {
    public static SpeedModel generateSpeedModel(TrafficUnit trafficUnit){
        return new SpeedModelImpl(trafficUnit);
    }
    private static class SpeedModelImpl implements SpeedModel{
        private TrafficUnit trafficUnit;

        private SpeedModelImpl(TrafficUnit trafficUnit){
            this.trafficUnit = trafficUnit;
        }

        public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
            double traction = trafficUnit.getTraction();
            double v = 2.0 * horsePower * 746 * timeSec * 32.174 / weightPounds;
            return Math.round(Math.sqrt(v) * 0.68 * traction);
        }
    }
}

