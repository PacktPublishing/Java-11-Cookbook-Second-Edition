package com.packt.demo;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class OpenModuleDemo{
    final static ObjectMapper MAPPER = new ObjectMapper();
    static{
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    public static void main(String[] args) 
        throws Exception {
        Person p = new Person("Mohamed", "Sanaulla", 
            LocalDate.now().minusYears(30), "India");
        String json = MAPPER.writeValueAsString(p);
        System.out.println("The Json for Person is: ");
        System.out.println(json);
    }
}