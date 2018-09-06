package com.packt.process;

import java.io.IOException;
import java.io.File;

public class ChangeWorkDirectoryDemo{
	public static void main(String[] args) 
		throws IOException, InterruptedException{
		ProcessBuilder pb = new ProcessBuilder();

		pb.command("tree")
		  .inheritIO();

		pb.directory(new File("/home/"));

		Process p = pb.start();

		int exitValue = p.waitFor();

	}
}
