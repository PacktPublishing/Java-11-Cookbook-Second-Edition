package com.packt.calculator.commands;

import com.packt.banking.BankUtil;
import java.util.Map;

public class SimpleInterestCommand implements Command{
	public final Double principal; 
	public final Integer rate; 
	public final Integer duration;

	public SimpleInterestCommand(Double principal, Integer rate, Integer duration){
		this.principal = principal;
		this.rate = rate;
		this.duration = duration;
	}

	@Override
	public void execute() throws Exception{
		Double interest = BankUtil.computeSimpleInterest(principal, rate, duration);
		printInJson(Map.of("simple interest",interest));
		//System.out.println(String.format("Simple Interest is %f", interest));
	}
}