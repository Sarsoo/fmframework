package sarsoo.fmframework.util;

public class ConsoleHandler {
	
	private static Console console;
	
	private static boolean isVerbose = false;
	
	public static Console getConsole() {
		return console;
	}
	
	public static boolean isVerbose() {
		return isVerbose;
	}

	public static void setVerbose(Console consoleIn) {
		if(consoleIn == null)
			isVerbose = false;
		else 
			isVerbose = true;
		console = consoleIn;
	}

}
