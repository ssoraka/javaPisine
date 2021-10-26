package edu.school21;

public class PrinterWithPrefixImpl implements Printer{
	private Renderer renderer;
	private String prefix;

	public PrinterWithPrefixImpl(Renderer renderer) {
		this.renderer = renderer;
		this.prefix = "Default";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void print (String text) {
		renderer.printText(prefix + text);
	}
}
