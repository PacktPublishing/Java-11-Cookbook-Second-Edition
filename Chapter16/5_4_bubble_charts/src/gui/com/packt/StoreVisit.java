package com.packt;

public class StoreVisit{
	Integer hour;
	Integer visits;
	Integer sales;

	public StoreVisit(String[] elements){
		hour = Integer.parseInt(elements[0]);
		visits = Integer.parseInt(elements[1]);
		sales = Integer.parseInt(elements[2]);
	}

	public Integer getSales() { return sales; }
}