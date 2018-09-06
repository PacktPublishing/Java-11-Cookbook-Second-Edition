package com.packt;

import jdk.jshell.JShell;
import jdk.jshell.JShell.Builder;
import jdk.jshell.SnippetEvent;
import jdk.jshell.Snippet;
import jdk.jshell.Snippet.Status;
import java.util.List;
import java.util.Scanner;

public class JshellJavaApiDemo{
	public static void main(String[] args) {
		JShell myShell = JShell.create();

		System.out.println("Welcome to JShell Java API Demo");
		System.out.println("Please Enter a Snippet. Enter EXIT to exit:");
		try(Scanner reader = new Scanner(System.in)){
			while(true){
				String snippet = reader.nextLine();
				if ( "EXIT".equals(snippet)){
					break;
				}
				List<SnippetEvent> events = myShell.eval(snippet);
				events.stream().forEach(se -> {
					System.out.print("Evaluation status: " + se.status());
					System.out.println(" Evaluation result: " + se.value());
				});
			}
		}
		System.out.println("Snippets processed: ");
		myShell.snippets().forEach(s -> {
			String msg = String.format("%s -> %s", s.kind(), s.source());
			System.out.println(msg);
		});

		System.out.println("Methods: ");
		myShell.methods().forEach(m -> System.out.println(m.name() + " " + m.signature()));

		System.out.println("Variables: ");
		myShell.variables().forEach(v -> System.out.println(v.typeName() + " " + v.name()));
		myShell.close();
	}
}