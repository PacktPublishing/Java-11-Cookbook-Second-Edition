package com.packt.banking;

import java.util.Objects;

/**
 * Hello world!
 *
 */
public class Banking 
{
    public static Double simpleInterest(Double principal, 
        Double rateOfInterest, Integer years){
        Objects.requireNonNull(principal, "Principal cannot be null");
        Objects.requireNonNull(rateOfInterest, "Rate of interest cannot be null");
        Objects.requireNonNull(years, "Years cannot be null");

        return ( principal * rateOfInterest * years ) / 100;
    }
}
