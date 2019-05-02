package sarsoo.fmframework.log.entry;

import java.util.ArrayList;

public class InfoEntry extends LogEntry {

	public InfoEntry(String methodIn) {
		super(methodIn);
	}
	
	@Override
	public InfoEntry addArg(String arg) {
		if (args == null) {
			args = new ArrayList<String>();
		}
		args.add(arg);	
		return this;
	}

	public String toString() {

		String logString = String.format("%s \t>%s", timestamp, method);

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
