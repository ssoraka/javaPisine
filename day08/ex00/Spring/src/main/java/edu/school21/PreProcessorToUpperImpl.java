package edu.school21;

public class PreProcessorToUpperImpl implements PreProcessor{

	@Override
	public String preProcess(String text) {
		return text.toUpperCase();
	}
}
