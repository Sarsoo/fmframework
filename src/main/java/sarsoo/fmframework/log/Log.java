package sarsoo.fmframework.log;

import java.util.ArrayList;

import sarsoo.fmframework.log.console.Console;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class Log {

	private ArrayList<Console> consoles = new ArrayList<Console>();

	private Boolean writeToSTDOut;
	
	private ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
	private ArrayList<InfoEntry> infoList = new ArrayList<InfoEntry>();
	private ArrayList<ErrorEntry> errorList = new ArrayList<ErrorEntry>();

	public Log() {
		writeToSTDOut = true;
	}

	public Log(Console console, Boolean toSTD) {

		consoles.add(console);

		writeToSTDOut = toSTD;

	}

	public void clearConsoles() {
		consoles.clear();
		writeToSTDOut = true;
	}

	public void log(LogEntry entry) {
		logList.add(entry);
		writeLog(entry.toString());

	}

	public void logInfo(InfoEntry entry) {
		infoList.add(entry);
		writeLog(entry.toString());

	}

	public void logError(ErrorEntry entry) {
		errorList.add(entry);
		writeLog(entry.toString());

	}

	protected void writeLog(String logString) {

		if (consoles.size() > 0) {
			for (Console i : consoles) {
				i.write(logString);
			}
		}

		if (writeToSTDOut == true) {
			System.out.println(logString);
		}
	}

}
