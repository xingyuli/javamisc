package org.swordess.toy.javamisc.junit.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DefaultControllerTest {

	private DefaultController controller;
	private Request req;
	private RequestHandler handler;
	
	@Before
	public void instantiate() throws Exception {
		controller = new DefaultController();
		req = new SampleRequest();
		handler = new SampleHandler();
		
		controller.addHandler(req, handler);
	}
	
	@Test
	public void testAddHandler() {
		RequestHandler actualHandler = controller.getHandler(req);
		assertSame(
				"Handler we set in controller should be the same handler we get",
				handler, actualHandler);
	}
	
	@Test
	public void testProcessRequest() {
		Response resp = controller.processRequest(req);
		assertNotNull("Must not return a null response", resp);
		assertEquals(new SampleResponse(), resp);
	}
	
	@Test
	public void testProcessRequestAnswersErrorResponse() {
		SampleRequest req = new SampleRequest("testError");
		SampleExceptionHandler handler = new SampleExceptionHandler();
		controller.addHandler(req, handler);
		
		Response resp = controller.processRequest(req);
		assertNotNull("Must not return a null response", resp);
		assertEquals(ErrorResponse.class, resp.getClass());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetHandlerNotDefined() {
		SampleRequest req = new SampleRequest("testNotDefined");
		controller.getHandler(req);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddRequestDuplicateName() {
		SampleRequest req = new SampleRequest();
		SampleHandler handler = new SampleHandler();
		controller.addHandler(req, handler);
	}
	
	@Test(timeout = 130)
	@Ignore("Ignore for now until we decide a decent time-limit")
	public void testProcessMultipleRequestsTimeout() {
		RequestHandler handler = new SampleHandler();
		for (int i = 0; i < 99999; i++) {
			Request req = new SampleRequest(String.valueOf(i));
			controller.addHandler(req, handler);
			Response resp = controller.processRequest(req);
			assertNotNull(resp);
			assertNotSame(ErrorResponse.class, resp.getClass());
		}
	}
	
	private class SampleRequest implements Request {
		
		private static final String DEFAULT_NAME = "Test";
		
		private String name;
		
		public SampleRequest() {
			this(DEFAULT_NAME);
		}
		
		public SampleRequest(String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
	
	private class SampleHandler implements RequestHandler {
		
		@Override
		public Response process(Request req) throws Exception {
			return new SampleResponse();
		}
		
	}
	
	private class SampleExceptionHandler implements RequestHandler {

		@Override
		public Response process(Request req) throws Exception {
			throw new Exception("error processing request");
		}
		
	}
	
	private class SampleResponse implements Response {
		
		private static final String NAME = "Test";
		
		@Override
		public String getName() {
			return NAME;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof SampleResponse)) {
				return false;
			}
			return getName().equals(((SampleResponse)obj).getName());
		}
		
		@Override
		public int hashCode() {
			return NAME.hashCode();
		}
		
	}
	
}
