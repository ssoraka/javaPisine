package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.Logic;

@Parameters(separators = "=")
public class Program {
	@Parameter(names="--white")
	String white;
	@Parameter(names="--black")
	String black;

	private static final String IMAGE = "/resources/it.bmp";
	public static void main(String[] args) {
		Program program = new Program();
		try {
			JCommander.newBuilder()
				.addObject(program)
				.build()
				.parse(args);
			program.run();
		} catch (ParameterException ex) {
			System.err.println("Error arguments!");
			System.err.println("Valid arguments are:");
			System.err.println("--white=\"white symbol\"");
			System.err.println("--black=\"black symbol\"");
			System.exit(1);
		}
	}

	public void run() {
		Logic logic = new Logic();
		if (white == null || black == null) {
			System.err.println("No enough parameters!");
			System.exit(1);
		}
		logic.printImage(white, black, Program.class.getResource(IMAGE));
	}
}