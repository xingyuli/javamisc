package org.swordess.toy.javamisc.junit.chapter3;

public interface Controller {

	public Response processRequest(Request req);
	
	public void addHandler(Request req, RequestHandler handler);
	
}
