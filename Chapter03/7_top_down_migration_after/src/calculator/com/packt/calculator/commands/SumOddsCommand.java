package com.packt.calculator.commands;

import com.packt.math.MathUtil;
import java.util.Map;

public class SumOddsCommand implements Command{
	public final Integer count;
	public SumOddsCommand(Integer count){
		this.count = count;
	}

	@Override
	public void execute() throws Exception{
		printInJson(Map.of(String.format("Sum of %d odds", count) , 
			MathUtil.sumOfFirstNOdds(count)));
		// System.out.println(String.format("Sum of %d odds is %d", 
		// 	count, MathUtil.sumOfFirstNOdds(count)));
	}
}