package org.swordess.javamisc.junit.chapter6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.util.ByteArrayISO8859Writer;
import org.swordess.javamisc.junit.chapter6.WebClient;

/**
 * Stub of the whole web server.
 */
public class WebClientTest {

	@BeforeClass
	public static void setUp() throws Exception {
		Server server = new Server(8080);

		Context contentOkContext = new Context(server, "/testGetContentOk");
		contentOkContext.setHandler(new TestGetContentOkHandler());
		
		Context contentNotFoundContext = new Context(server, "/testGetContentNotFound");
		contentNotFoundContext.setHandler(new TestGetContentNotFoundHandler());
		
		server.setStopAtShutdown(true);
		server.start();
	}
	
	@Test
	public void testGetContentOk() throws Exception {
		WebClient client = new WebClient();
		String result = client.getContent(new URL("http://localhost:8080/testGetContentOk"));
		assertEquals("It works", result);
	}
	
	@Test
	public void testGetContentNotFound() throws Exception {
		WebClient client = new WebClient();
		String result = client.getContent(new URL("http://localhost:8080/testGetContentNotFound"));
		assertNull(result);
	}
	
	private static class TestGetContentOkHandler extends AbstractHandler {

		@Override
		public void handle(String target, HttpServletRequest request,
				HttpServletResponse response, int dispatch) throws IOException,
				ServletException {
			ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
			writer.write("It works");
			writer.flush();
			
			OutputStream out = response.getOutputStream();
			response.setIntHeader(HttpHeaders.CONTENT_LENGTH, writer.size());
			writer.writeTo(out);
			writer.close();
			out.flush();
		}
		
	}
	
	private static class TestGetContentNotFoundHandler extends AbstractHandler {

		@Override
		public void handle(String target, HttpServletRequest request,
				HttpServletResponse response, int dispatch) throws IOException,
				ServletException {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
	}
	
}
