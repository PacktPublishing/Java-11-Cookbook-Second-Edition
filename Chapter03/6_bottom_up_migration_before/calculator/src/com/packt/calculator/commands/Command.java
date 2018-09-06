package com.packt.calculator.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public interface Command{
	ObjectMapper objectMapper = new ObjectMapper();
	public void execute() throws Exception;

	default void printInJson(Map<String, Object> data) throws Exception{
		System.out.println(objectMapper.writeValueAsString(data));
	}

}