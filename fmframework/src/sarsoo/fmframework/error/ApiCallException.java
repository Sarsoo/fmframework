package sarsoo.fmframework.error;

public class ApiCallException extends Exception {
	
	private int failureCode;
	
	public ApiCallException(int failureCode) {
		this.failureCode = failureCode;
	}
	
	public int getFailureCode() {
		return failureCode;
	}
	
	public String getError() {
		switch(failureCode) {
		case 2:
			return "Invalid service - This service does not exist";
		case 3:
			return "Invalid Method - No method with that name in this package";
		case 4:
			return "Authentication Failed - You do not have permissions to access the service";
		case 5:
			return "Invalid format - This service doesn't exist in that format";
		case 6:
			return "Invalid parameters - Your request is missing a required parameter";
		case 7:
			return "Invalid resource specified";
		case 8:
			return "Operation failed - Something else went wrong";
		case 9:
			return "Invalid session key - Please re-authenticate";
		case 10:
			return "Invalid API key - You must be granted a valid key by last.fm";
		case 11:
			return "Service Offline - This service is temporarily offline. Try again later.";
		case 13:
			return "Invalid method signature supplied";
		case 16:
			return "There was a temporary error processing your request. Please try again";
		case 26:
			return "Suspended API key - Access for your account has been suspended, please contact Last.fm";
		case 29:
			return "Rate limit exceeded - Your IP has made too many requests in a short period";	
		default:
			return null;
		}
	}

}
