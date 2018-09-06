package com.packt.calculator.commands;

import com.packt.math.MathUtil;
import java.util.Map;

public class SumEvensCommand implements Command{
	public final Integer count;
	public SumEvensCommand(Integer count){
		this.count = count;
	}

	@Override
	public void execute() throws Exception{
		printInJson(Map.of(String.format("Sum of %d evens", count) , 
			MathUtil.sumOfFirstNEvens(count)));
		//System.out.println(String.format("Sum of %d evens is %d", 
		//	count, MathUtil.sumOfFirstNEvens(count)));
	}
}