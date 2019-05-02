package sarsoo.fmframework.log.entry;

import java.util.ArrayList;

public class ErrorEntry extends LogEntry {

	protected int errorCode;

	public ErrorEntry(String methodIn) {
		super(methodIn);
	}

	public ErrorEntry setErrorCode(int error) {
		errorCode = error;
		return this;
	}
	
	@Override
	public ErrorEntry addArg(String arg) {
		if (args == null) {
			args = new ArrayList<String>();
		}
		args.add(arg);	
		return this;
	}

	public String toString() {

		String logString = String.format("%s !!%s", timestamp, method);
		
		if (errorCode != 0) {
			logString += String.format(" (%i)", errorCode);
		}

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
