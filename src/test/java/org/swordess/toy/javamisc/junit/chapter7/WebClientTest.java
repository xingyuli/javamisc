package org.swordess.toy.javamisc.junit.chapter7;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.swordess.toy.javamisc.junit.chapter7.WebClient;

public class WebClientTest {

	@Test
	public void testGetContentOk() throws Exception {
		// 1. preparation
		MockConnectionFactory mockConnectionFactory = new MockConnectionFactory();
		MockInputStream mockStream = new MockInputStream();
		mockStream.setBuffer("It works");
		mockConnectionFactory.setData(mockStream);
	
		// 2. execute
		WebClient client = new WebClient();
		String result = client.getContent(mockConnectionFactory);
		
		// 3. verify
		assertEquals("It works", result);
		mockStream.verify();
	}

}
