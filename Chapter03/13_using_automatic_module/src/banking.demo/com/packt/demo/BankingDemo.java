package com.packt.demo;

import com.packt.banking.Banking;

public class BankingDemo{
    public static void main(String[] args) {
        Double principal = 1000.0;
        Double rateOfInterest = 10.0;
        Integer years = 2;
        Double simpleInterest = Banking.simpleInterest(principal, 
            rateOfInterest, years);
        System.out.println("The simple interest is: " + simpleInterest);
    }
}