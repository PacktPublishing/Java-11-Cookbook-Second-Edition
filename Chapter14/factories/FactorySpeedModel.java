package com.packt.cookbook.ch14_testing.factories;

import com.packt.cookbook.ch14_testing.api.SpeedModel;

public class FactorySpeedModel {
    public static SpeedModel getSpeedModel(){
        return  (t, wp, hp) -> {
            double weightPower = 2.0 * hp * 746 * 32.174 / wp;
            return Math.round(Math.sqrt(t * weightPower) * 0.68);
        };
    }
}

