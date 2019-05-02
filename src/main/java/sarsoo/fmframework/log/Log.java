package sarsoo.fmframework.log;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
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
	
	private LocalDateTime createdTime = LocalDateTime.now();

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

	public void dumpLog() {
		Logger.getLog().log(new LogEntry("dumpLog"));
		try {
			FileWriter writer = new FileWriter(String.format("%s_Log.txt", createdTime));
			
			for(LogEntry i: logList) {
				writer.write(i.toString());
			}
			
			writer.close();
			Logger.getLog().log(new InfoEntry("dumpLog").addArg("log written"));
		} catch (IOException e) {
			Logger.getLog().log(new ErrorEntry("dumpLog").addArg("io exception"));
			e.printStackTrace();
		}
	}
	
	public void dumpInfoLog() {
		Logger.getLog().log(new LogEntry("dumpInfoLog"));
		try {
			FileWriter writer = new FileWriter(String.format("%s_InfoLog.txt", createdTime));
			
			for(LogEntry i: infoList) {
				writer.write(i.toString());
			}
			
			writer.close();
			Logger.getLog().log(new InfoEntry("dumpInfoLog").addArg("log written"));
		} catch (IOException e) {
			Logger.getLog().log(new ErrorEntry("dumpInfoLog").addArg("io exception"));
			e.printStackTrace();
		}
	}
	
	public void dumpErrorLog() {
		Logger.getLog().log(new LogEntry("dumpErrorLog"));
		try {
			FileWriter writer = new FileWriter(String.format("%s_ErrorLog.txt", createdTime));
			
			for(LogEntry i: errorList) {
				writer.write(i.toString());
			}
			
			writer.close();
			Logger.getLog().log(new InfoEntry("dumpErrorLog").addArg("log written"));
		} catch (IOException e) {
			Logger.getLog().log(new ErrorEntry("dumpErrorLog").addArg("io exception"));
			e.printStackTrace();
		}
	}

}
