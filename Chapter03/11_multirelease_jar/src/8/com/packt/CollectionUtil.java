package com.packt;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

public class CollectionUtil{
	public static List<String> list(String ... args){
		System.out.println("Using Arrays.asList");
		return Arrays.asList(args);
	}

	public static Set<String> set(String ... args){
		System.out.println("Using Arrays.asList and set.addAll");
		Set<String> set = new HashSet<>();
		set.addAll(list(args));
		return set;
	}
}