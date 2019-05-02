package sarsoo.fmframework.util;

import java.util.ArrayList;

import sarsoo.fmframework.log.console.Console;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;

public class Reference {
	private static String userName;
	private static boolean isVerbose = false;
	private static boolean isHeadless = false;
	
	private static Console console;

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userNameIn) {
		userName = userNameIn;
	}

	public static boolean isHeadless() {
		return isHeadless;
	}

	public static void setIsHeadless(boolean headlessIn) {
		isHeadless = headlessIn;
	}
	
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
