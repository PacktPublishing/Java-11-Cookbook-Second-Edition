package com.packt.process;

import java.time.Instant;
import java.time.Duration;

public class CurrentProcessInfoDemo{
	public static void main(String[] args)
		throws InterruptedException {
		ProcessHandle handle = ProcessHandle.current();
		System.out.println("Started main...");
		for ( int i = 0 ; i < 100; i++){
			Thread.sleep(1000);
		}
		
		ProcessHandle.Info info = handle.info();

		System.out.println("Command line: " + info.commandLine().get());
		System.out.println("Command: " + info.command().get());
		System.out.println("Arguments: " + 
			String.join(" ", info.arguments().get()));
		System.out.println("User: " + info.user().get());
		System.out.println("Start: " + info.startInstant().get());
		System.out.println("Total CPU Duration: " + info.totalCpuDuration().get().toMillis() +"ms");

		System.out.println("PID: " + handle.pid());

		Instant end = Instant.now();
		System.out.println("End: " + end);
	}
}