package org.swordess.javamisc.junit.chapter3;

public class ErrorResponse implements Response {

	private Request originalRequest;
	private Exception originalException;

	public ErrorResponse(Request req, Exception e) {
		this.originalRequest = req;
		this.originalException = e;
	}

	public Request getOriginalRequest() {
		return originalRequest;
	}

	public Exception getOriginalException() {
		return originalException;
	}

	@Override
	public String getName() {
		return "Error";
	}

}
