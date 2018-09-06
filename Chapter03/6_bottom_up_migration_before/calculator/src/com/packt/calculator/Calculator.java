package com.packt.calculator;

import com.packt.calculator.commands.*;
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
		System.out.println("6. Simple Interest");
		System.out.println("7. Compound Interest");
		System.out.println("8. Exit");
		System.out.println("Enter the number to choose operation");
		return reader.nextInt();
	}

	public static void main(String[] args) throws Exception{
		Scanner reader = new Scanner(System.in);
		Integer choice = 0 ;
		do{
			Command command = null;
			choice = acceptChoice(reader);
			switch(choice){
				case 1:
					System.out.println("Enter the number");
					command = new PrimeCheckCommand(reader.nextInt());
				break;
				case 2:
					System.out.println("Enter the number");
					command = new EvenCheckCommand(reader.nextInt());
				break;
				case 3:
					System.out.println("How many primes?");
					command = new SumPrimesCommand(reader.nextInt());
				break;
				case 4:
					System.out.println("How many evens?");
					command = new SumEvensCommand(reader.nextInt());
				break;
				case 5: 
					System.out.println("How many odds?");
					command = new SumOddsCommand(reader.nextInt());
				break;
				case 6:
					System.out.println("Enter principal, rate and number of years");
					command = new SimpleInterestCommand(reader.nextDouble(), 
						reader.nextInt(), reader.nextInt());
				break;
				case 7:
					System.out.println("Enter principal, rate, number of compunds per year " + 
						"and number of years");
					command = new CompoundInterestCommand(reader.nextDouble(), 
						reader.nextInt(), reader.nextInt(), reader.nextInt());
				break;
			}
			if ( command != null ){
				command.execute();
			}
		}while(choice < 8 && choice > 0);
	}
}
