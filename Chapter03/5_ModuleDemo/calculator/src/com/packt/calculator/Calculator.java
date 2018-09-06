package com.packt.calculator;

import com.packt.math.MathUtil;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.function.Function;

public class Calculator{
    private static Integer acceptChoice(Scanner reader){
        System.out.println("************Advanced Calculator************");
        System.out.println("1. Prime Number check");
        System.out.println("2. Even Number check");
        System.out.println("3. Sum of N Primes");
        System.out.println("4. Sum of N Evens");
        System.out.println("5. Sum of N Odds");
        System.out.println("6. Exit");
        System.out.println("Enter the number to choose operation");
        return reader.nextInt();
    }

    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        Integer choice = 0 ;
        do{
            choice = acceptChoice(reader);
            switch(choice){
                case 1:
                    System.out.println("Enter the number");
                    Integer number = reader.nextInt();
                    if (MathUtil.isPrime(number)){
                        System.out.println("The number " + number +" is prime");
                    }else{
                        System.out.println("The number " + number +" is not prime");
                    }
                    break;
                case 2:
                    System.out.println("Enter the number");
                    number = reader.nextInt();
                    if (MathUtil.isEven(number)){
                        System.out.println("The number " + number +" is even");
                    }
                    break;
                case 3:
                    System.out.println("How many primes?");
                    Integer count = reader.nextInt();
                    System.out.println(String.format("Sum of %d primes is %d",
                            count, MathUtil.sumOfFirstNPrimes(count)));
                    break;
                case 4:
                    System.out.println("How many evens?");
                    count = reader.nextInt();
                    System.out.println(String.format("Sum of %d evens is %d",
                            count, MathUtil.sumOfFirstNEvens(count)));
                    break;
                case 5:
                    System.out.println("How many odds?");
                    count = reader.nextInt();
                    System.out.println(String.format("Sum of %d odds is %d",
                            count, MathUtil.sumOfFirstNOdds(count)));
                    break;
            }
        }while(choice < 6 && choice > 0);
    }
}