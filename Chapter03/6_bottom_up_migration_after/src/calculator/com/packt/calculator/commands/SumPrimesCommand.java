package com.packt.calculator.commands;

import com.packt.math.MathUtil;
import java.util.Map;

public class SumPrimesCommand implements Command{
	public final Integer count;
	public SumPrimesCommand(Integer count){
		this.count = count;
	}

	@Override
	public void execute() throws Exception{
		printInJson(Map.of(String.format("Sum of %d primes", count) , 
			MathUtil.sumOfFirstNPrimes(count)));
		/*System.out.println(String.format("Sum of %d primes is %d", 
			count, MathUtil.sumOfFirstNPrimes(count)));*/
	}
}