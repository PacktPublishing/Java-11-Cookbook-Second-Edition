package com.packt;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class Main{
    public static void main(String[] args) {

        var date = LocalDate.now();
        
        System.out.println("Current Date is: " + date);
        var dayOfWeek = date.getDayOfWeek();
        System.out.println("Day Of Week: " + dayOfWeek);

        var dayOfMonth = date.getDayOfMonth();
        System.out.println("Day Of Month: " + dayOfMonth);

        var month = date.getMonth();
        System.out.println("Month: " + month + ", Value: " + month.getValue());
        var year = date.getYear();
        System.out.println("Year: " + year);

        var date1 = LocalDate.of(2018, 4, 12);
        var date2 = LocalDate.of(2018, Month.APRIL, 12);
        compare(date1, date2);
        
        date2 = LocalDate.ofYearDay(2018, 102);
        compare(date1, date2);

        date2 = LocalDate.parse("2018-04-12");
        compare(date1, date2);

        var time = LocalTime.now();
        System.out.println("Current time is: " + time);
        

        time = LocalTime.of(23, 11, 11, 11);
        var hour = time.getHour();
        System.out.println("Hour " + hour);
        var minutes = time.getMinute();
        System.out.println("Minutes " + minutes);
        var seconds = time.get(ChronoField.SECOND_OF_MINUTE);
        System.out.println("Seconds " + seconds);
        System.out.println("Time after 3600 seconds in the day: " + LocalTime.ofSecondOfDay(3600));

        var dateTime = LocalDateTime.now();
        System.out.println("Current Date time is: " + dateTime); 
        
        var dateTime1 = LocalDateTime.of(2018, 04, 12, 13, 30, 22);
        var dateTime2 = LocalDateTime.of(2018, Month.APRIL, 12, 13, 30, 22);
        compare(dateTime1, dateTime2);
        
        dateTime2 = LocalDateTime.of(date2, LocalTime.of(13, 30, 22));
        compare(dateTime1, dateTime2);

    }

    private static void compare(LocalDate date1, LocalDate date2){
        if ( !date1.equals(date2) ) {
            throw new AssertionError(String.format("Date 1 %s is not equal to Date 2 %s", date1, date2));
        }
    }

    private static void compare(LocalDateTime time1, LocalDateTime time2){
        if ( !time1.equals(time2) ) {
            throw new AssertionError(String.format("DateTime 1 %s is not equal to DateTime 2 %s", 
                time1, time2));
        }
    }
}