package com.packt;

import java.io.IOException;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main{
    public static void main(String[] args) throws IOException{
        var scanner = new Scanner(System.in);
        
        System.out.println("Welcome to calendar printing system");
        while(true){
            System.out.println("Please enter month and year separated by space. -1 to exit");
            var month = scanner.nextInt();
            if ( month == -1 ){ break; }
            var year = scanner.nextInt();
            var startDate = LocalDate.of(year, month, 1);
            //because end date is excluded so we add 1 to it.
            var endDate = startDate.plusDays(startDate.lengthOfMonth());
            //var calendar = new HashMap<ChronoField.DAY_OF_WEEK, List<Integer>>();
            var dayBuckets = startDate.datesUntil(endDate).collect(
                
                Collectors.groupingBy(date -> date.getDayOfWeek(), 
                    Collectors.mapping(LocalDate::getDayOfMonth, 
                        Collectors.toList())
                ));
            System.out.println(dayBuckets);
        }
    }

}