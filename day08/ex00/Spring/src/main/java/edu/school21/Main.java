package edu.school21;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new
				FileSystemXmlApplicationContext("context.xml");
		Printer printer = context.getBean("printerWithPrefix", Printer.class);
		printer.print("Hello!");
	}
}
