package com.packt.process;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class ManageSubProcessDemo{
	public static void main(String[] args) throws Exception{
		
		for ( int i = 0; i < 10; i++){
			new ProcessBuilder("/bin/bash", "script.sh")
				.redirectOutput(ProcessBuilder.Redirect.DISCARD)
				.start();
		}
		
		ProcessHandle currentProcess = ProcessHandle.current();
		
		System.out.println("Obtaining children");
		currentProcess.children().forEach(pHandle -> {
			System.out.println(pHandle.info());
		});

		System.out.println("Obtaining descendants");
		
		currentProcess.descendants().forEach(pHandle -> {
			System.out.println(pHandle.info());
		});

	}
}