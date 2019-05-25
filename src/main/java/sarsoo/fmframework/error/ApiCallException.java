package sarsoo.fmframework.error;

import sarsoo.fmframework.log.Log;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;

public class ApiCallException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int failureCode;
	private String failureString;
	private String method;
	
	public ApiCallException(String method, int failureCode, String failureDescription) {
		this.failureCode = failureCode;
		this.failureString = failureDescription;
		this.method = method;
		Logger.getLog().logError(getLogEntry());
	}
	
	public ApiCallException(String method, int failureCode, String failureDescription, Log log) {
		this.method = method;
		this.failureCode = failureCode;
		this.failureString = failureDescription;
		log.logError(getLogEntry());
	}
	
	private ErrorEntry getLogEntry() {
		return new ErrorEntry("ApiCallException").addArg(method).addArg(Integer.toString(failureCode)).addArg(failureString);
	}
	
	public String getCauseMethod() {
		return method;
	}
	
	public int getFailureCode() {
		return failureCode;
	}
	
	public String getFailureMessage() {
		return failureString;
	}


}
