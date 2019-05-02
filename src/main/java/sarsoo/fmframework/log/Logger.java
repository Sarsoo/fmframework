package sarsoo.fmframework.log;

public class Logger {

	private static Log log = new Log();
	
	public static Log getLog() {
		return log;
	}
	
	public static void setLog(Log logIn) {
		log = logIn;
	}

}
