package com.packt.process;
import java.io.*;
import java.util.List;

public class PipeDemo{
	public static void main(String[] args) throws Exception{
		List<ProcessBuilder> pipeline = List.of(
			new ProcessBuilder("cat", "iris.data.txt"),
			new ProcessBuilder("cut", "-d", ",", "-f", "5"),
			new ProcessBuilder("uniq", "-c").redirectOutput(ProcessBuilder.Redirect.INHERIT)
		);
		
		List<Process> processes = ProcessBuilder.startPipeline(pipeline);

		int exitValue = processes.get(processes.size() - 1).waitFor();
		
	}
}