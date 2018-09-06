package com.packt.calculator.commands;

import com.packt.math.MathUtil;
import java.util.Map;

public class PrimeCheckCommand implements Command{
	public final Integer number;
	public PrimeCheckCommand(Integer n){
		number = n;
	}

	@Override
	public void execute() throws Exception{
		if (MathUtil.isPrime(number)){
			printInJson(Map.of(number.toString(), "prime"));
			//System.out.println("The number " + number +" is prime");
		}else{
			printInJson(Map.of(number.toString(), "not prime"));
			//System.out.println("The number " + number +" is not prime");
		}
	}
}