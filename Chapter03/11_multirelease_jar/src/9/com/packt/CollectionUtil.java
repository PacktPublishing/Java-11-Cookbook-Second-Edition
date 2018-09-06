package com.packt;

import java.util.Map;
import java.util.Set;
import java.util.List;

public class CollectionUtil{

	public static List<String> list(String ... args){
		System.out.println("Using factory methods");
		return List.of(args);
	}

	public static Set<String> set(String ... args){
		System.out.println("Using factory methods");
		return Set.of(args);
	}
}