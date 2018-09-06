package com.packt.process;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class SpawnedProcessInfoDemo{
	public static void main(String [] args) throws IOException, InterruptedException{
		System.out.println("Started main");
		ProcessBuilder pBuilder = new ProcessBuilder("sleep", "20");
		Process p = pBuilder.inheritIO().start();

		ProcessHandle handle = p.toHandle();

		int exitValue = p.waitFor();

		ProcessHandle.Info info = handle.info();

		System.out.println("Command line: " + info.commandLine().get());
		System.out.println("Command: " + info.command().get());
		System.out.println("Arguments: " + 
			String.join(" ", info.arguments().get()));
		System.out.println("User: " + info.user().get());
		System.out.println("Start: " + info.startInstant().get());
		System.out.println("Total CPU time(ms): " + info.totalCpuDuration().get().toMillis());

		System.out.println("PID: " + handle.pid());
	}
}