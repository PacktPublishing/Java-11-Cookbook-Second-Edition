package com.packt.process;

import java.io.IOException;
import java.util.Map;

public class EnvironmentVariableDemo{
	public static void main(String[] args) 
		throws IOException, InterruptedException {

		ProcessBuilder pb = new ProcessBuilder();

		pb.command("printenv")
		  .inheritIO();

		Map<String, String> environment = pb.environment();
		environment.put("COOKBOOK_VAR1", "First variable");
		environment.put("COOKBOOK_VAR2", "Second variable");
		environment.put("COOKBOOK_VAR3", "Third variable");

		Process p = pb.start();

		int exitValue = p.waitFor();
		
	}
}