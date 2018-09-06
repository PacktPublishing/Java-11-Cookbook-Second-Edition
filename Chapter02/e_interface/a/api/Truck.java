package com.packt.cookbook.ch02_oop.e_interface.a.api;

public interface Truck extends Vehicle {
    int getPayloadPounds();
    default int getPayloadKg(){
        return convertPoundsToKg(getPayloadPounds());
    }

    default int getWeightKg(int pounds){
        return convertPoundsToKg(pounds);
    }

    private int convertPoundsToKg(int pounds){
        return (int) Math.round(0.454 * pounds);
    }

    static int convertKgToPounds(int kilograms){
        return (int) Math.round(2.205 * kilograms);
    }
}
