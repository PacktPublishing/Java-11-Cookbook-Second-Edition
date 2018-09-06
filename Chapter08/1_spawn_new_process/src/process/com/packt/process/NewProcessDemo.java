package com.packt.process;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class NewProcessDemo{
	public static void main(String [] args) throws IOException, InterruptedException{
		ProcessBuilder pBuilder = new ProcessBuilder("free", "-m");
		Process p = pBuilder.inheritIO().start();
		if(p.waitFor(1, TimeUnit.SECONDS)){
			System.out.println("process completed successfully");
		}else{
			System.out.println("waiting time elapsed, process did not complete");
			System.out.println("destroying process forcibly");
			p.destroyForcibly();
		}
	}
}