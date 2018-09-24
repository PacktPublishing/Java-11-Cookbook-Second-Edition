package com.packt;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRules;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRules;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class Main{
    public static void main(String[] args) {
        var dateTime = ZonedDateTime.now();
        System.out.println("Date Time using now(): " + dateTime);

        var indianTz = ZoneId.of("Asia/Kolkata");
        var istDateTime = ZonedDateTime.now(indianTz);
        System.out.println("Date Time using ZoneId: " + istDateTime);

        var indianTzOffset = ZoneOffset.ofHoursMinutes(5, 30);
        istDateTime = ZonedDateTime.now(indianTzOffset);
        System.out.println("Date Time using ZoneOffset: " + istDateTime);
        
        ZonedDateTime dateTimeOf = ZonedDateTime.of(2018, 4, 22, 14, 30, 11, 33, indianTz);
        System.out.println("Date Time using of(): " + dateTimeOf);

        var localDateTime = dateTimeOf.toLocalDateTime();
        System.out.println("LocalDateTime from ZonedDateTime: " + localDateTime);
        
    }

}