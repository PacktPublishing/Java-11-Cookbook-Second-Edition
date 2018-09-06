package com.packt;

public class FallOfWicket{
	Double over;
	Integer score;

	public FallOfWicket(String[] elements){
		over = Double.parseDouble(elements[1]);
		score = Integer.parseInt(elements[2]);
	}

	@Override
	public String toString(){
		return over +"-" + score;
	}
}