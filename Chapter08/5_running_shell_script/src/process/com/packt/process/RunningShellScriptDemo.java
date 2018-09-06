package com.packt.process;

import java.io.IOException;
import java.io.File;
import java.util.Map;

public class RunningShellScriptDemo{
	public static void main(String[] args)
		throws IOException, InterruptedException {

		ProcessBuilder pb = new ProcessBuilder();
		
		pb.directory(new File("/root"));

		Map<String, String> environment = pb.environment();
		environment.put("MY_VARIABLE", "From your parent Java process");

		pb.command("/bin/bash", "script.sh").inheritIO();

		Process p = pb.start();

		int exitValue = p.waitFor();
	}
}
