package sarsoo.fmframework.log.entry;

public class InfoEntry extends LogEntry {

	public InfoEntry(String methodIn) {
		super(methodIn);
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
