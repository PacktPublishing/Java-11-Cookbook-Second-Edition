package com.packt.banking;
import java.util.stream.IntStream;
import java.util.function.IntPredicate;

public class BankUtil{

	public static Double computeSimpleInterest(Double principal, Integer rate, Integer duration){
		return (principal * rate * duration) / 100;
	}

	public static Double computeCompoundInterest(Double principal, Integer rateInPercent, 
		Integer noOfCompoundsPerYear, Integer duration){
		Double rate = rateInPercent / 100d;

		Double intermediate = 1 + (rate/noOfCompoundsPerYear);
		Double amount = principal * Math.pow(intermediate, (duration * noOfCompoundsPerYear));
		return (amount - principal);
	}

}
