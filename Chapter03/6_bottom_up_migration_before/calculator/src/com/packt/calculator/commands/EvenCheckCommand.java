package com.packt.calculator.commands;

import com.packt.math.MathUtil;
import java.util.Map;

public class EvenCheckCommand implements Command{
	public final Integer number;
	public EvenCheckCommand(Integer n){
		number = n;
	}

	@Override
	public void execute() throws Exception{
		if (MathUtil.isEven(number)){
			printInJson(Map.of(number.toString(), "even"));
			//System.out.println("The number " + number +" is even");
		}else{
			printInJson(Map.of(number.toString(), "odd"));
			//System.out.println("The number " + number +" is odd");
		}
	}
}