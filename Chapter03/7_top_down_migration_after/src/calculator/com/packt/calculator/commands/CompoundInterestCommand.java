package com.packt.calculator.commands;

import com.packt.banking.BankUtil;
import java.util.Map;
public class CompoundInterestCommand implements Command{
	public final Double principal;
	public final Integer rateInPercent;
	public final Integer noOfCompoundsPerYear;
	public final Integer duration;

	public CompoundInterestCommand(Double principal, Integer rateInPercent, 
		Integer noOfCompoundsPerYear, Integer duration){
		this.principal = principal;
		this.rateInPercent = rateInPercent;
		this.noOfCompoundsPerYear = noOfCompoundsPerYear;
		this.duration = duration;
	}

	@Override
	public void execute() throws Exception{
		Double interest = BankUtil.computeCompoundInterest(principal, rateInPercent, 
			noOfCompoundsPerYear, duration);
		printInJson(Map.of("compound interest", interest));
		//System.out.println(String.format("Compound Interest is %f", interest));
	}
}