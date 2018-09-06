package com.packt.process;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class ManageSpawnedProcessDemo{
	public static void main(String [] args) throws Exception{
		System.out.println("Started main");
		ProcessBuilder pBuilder = new ProcessBuilder("sleep", "60");
		Process p = pBuilder.inheritIO().start();

		p.waitFor(10, TimeUnit.SECONDS);
		boolean isAlive = p.isAlive();
		System.out.println("Process alive? " + isAlive);

		if ( isAlive ){
			boolean normalTermination = p.supportsNormalTermination();
			System.out.println("Normal Termination? " + normalTermination);
			p.destroy();
			isAlive = p.isAlive();
			System.out.println("Process alive? " + isAlive);
		}

	}
}