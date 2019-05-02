package sarsoo.fmframework.log.entry;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogEntry {
	
	protected String method;
	protected ArrayList<String> args;
	protected LocalDateTime timestamp;
	
	public LogEntry(String methodIn) {
		method = methodIn;
		timestamp = LocalDateTime.now();
	}
	
	public String getMethod() {
		return method;
	}
	
	public ArrayList<String> getArgs() {
		return args;
	}
	
	public LocalDateTime getTimeStamp() {
		return timestamp;
	}
	
	public LogEntry addArg(String arg) {
		if (args == null) {
			args = new ArrayList<String>();
		}
		args.add(arg);	
		return this;
	}
	
	public String toString() {
		
		String logString = String.format("%s >>%s", timestamp, method);

		if (args != null) {
			if (args.size() > 0) {

				logString += ":";

				for (String i : args) {
					logString += " " + i;
				}
			}
		}
		
		return logString;
	}

}
