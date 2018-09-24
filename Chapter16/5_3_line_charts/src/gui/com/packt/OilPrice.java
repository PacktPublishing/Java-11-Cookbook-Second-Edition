package com.packt;

public class OilPrice{
	public String period;
	public Double value;

	@Override
	public String toString(){
		return period + " = " + value;
	}
}