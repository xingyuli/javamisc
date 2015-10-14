package org.swordess.toy.javamisc.junit.chapter6;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.swordess.toy.javamisc.junit.chapter6.WebClient;

/**
 * Stub of connection.
 */
public class WebClientTest1 {

	@BeforeClass
	public static void setUp() {
		URL.setURLStreamHandlerFactory(new StubStreamHandlerFactactory());
	}

	@Test
	public void testGetContentOk() throws Exception {
		WebClient client = new WebClient();
		String result = client.getContent(new URL("http://localhost"));
		assertEquals("It works", result);
	}
	
	private static class StubStreamHandlerFactactory implements
			URLStreamHandlerFactory {

		@Override
		public URLStreamHandler createURLStreamHandler(String protocol) {
			return new StubHttpURLStreamHandler();
		}

	}

	private static class StubHttpURLStreamHandler extends URLStreamHandler {

		@Override
		protected URLConnection openConnection(URL u) throws IOException {
			return new StubHttpURLConnection(u);
		}

	}
	
	private static class StubHttpURLConnection extends HttpURLConnection {

		protected StubHttpURLConnection(URL url) {
			super(url);
		}

		@Override
		public InputStream getInputStream() throws IOException {
			if (!getDoInput()) {
				throw new ProtocolException("Cannot read from URLConnection"
						+ " if doInput=false (call setDoInput(true))");
			}
			return new ByteArrayInputStream("It works".getBytes());
		}
		
		@Override
		public void connect() throws IOException {}
		
		@Override
		public void disconnect() {}

		@Override
		public boolean usingProxy() {
			return false;
		}

	}

}
