package com.packt.process;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RedirectFileDemo{
	public static void main(String[] args) 
		throws IOException, InterruptedException{
		ProcessBuilder pb = new ProcessBuilder("iostat", "-z");
		pb.redirectError(new File("error"))
			.redirectOutput(new File("output"));
		Process p = pb.start();
		int exitValue = p.waitFor();

		System.out.println("Output");
		Files.lines(Paths.get("output")).forEach(l -> System.out.println(l));

		System.out.println("Error");
		Files.lines(Paths.get("error")).forEach(l -> System.out.println(l));
	}
}
