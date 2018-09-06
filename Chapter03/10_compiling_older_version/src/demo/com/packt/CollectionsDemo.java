package com.packt;
import java.util.Map;
import java.util.HashMap;

public class CollectionsDemo{
	public static void main(String[] args){
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value3");
		map.put("key3", "value3");
		map.forEach((k,v) -> System.out.println(k + ", " + v));
	}
}