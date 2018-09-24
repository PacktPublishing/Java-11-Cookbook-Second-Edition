package com.packt;

public class Marks{
	public String id;
	public Double unitTests;
	public Double midTerm;
	public Double finalTerm;

	public Marks(String[] elements){
		id = elements[0];
		unitTests = Double.parseDouble(elements[1]);
		midTerm = Double.parseDouble(elements[2]);
		finalTerm = Double.parseDouble(elements[3]);
	}

	public Double getUnitTests(){ return unitTests; }
	public Double getMidTerm(){ return midTerm; }
	public Double getFinalTerm(){ return finalTerm; }
}