package com.packt.cookbook.ch07_concurrency.api;

import java.time.DayOfWeek;
import java.time.Month;

public class DateLocation {
    private int hour;
    private Month month;
    private DayOfWeek dayOfWeek;
    private String country, city, trafficLight;

    public DateLocation(Month month, DayOfWeek dayOfWeek, int hour, String country, String city, String trafficLight) {
        this.hour = hour;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.country = country;
        this.city = city;
        this.trafficLight = trafficLight;
    }

    public int getHour() {
        return hour;
    }

    public Month getMonth() {
        return month;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getTrafficLight() {
        return trafficLight;
    }
}
