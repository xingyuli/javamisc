package org.swordess.javamisc.junit.chapter7;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WebClientEasyMockTest {

	private ConnectionFactory factory;
	private InputStream stream;

	@Before
	public void setUp() {
		factory = createStrictMock("factory", ConnectionFactory.class);
		stream = createStrictMock("stream", InputStream.class);
	}
	
	@Test
	public void testGetContentOk() throws Exception {
		// define expectations
		expect(factory.getData()).andReturn(stream);
		expect(stream.read()).andReturn(new Integer((byte) 'W'));
		expect(stream.read()).andReturn(new Integer((byte) 'o'));
		expect(stream.read()).andReturn(new Integer((byte) 'r'));
		expect(stream.read()).andReturn(new Integer((byte) 'k'));
		expect(stream.read()).andReturn(new Integer((byte) 's'));
		expect(stream.read()).andReturn(new Integer((byte) '!'));
		expect(stream.read()).andReturn(-1);
		stream.close();

		// expectations done
		replay(factory);
		replay(stream);

		// execute
		WebClient client = new WebClient();
		String result = client.getContent(factory);

		// verify
		assertEquals("Works!", result);
	}

	@Test
	public void testGetContentCannotCloseInputStream() throws Exception {
		expect(factory.getData()).andReturn(stream);
		expect(stream.read()).andReturn(-1);
		stream.close();
		expectLastCall().andThrow(new IOException("cannot close"));
		
		replay(factory);
		replay(stream);
		
		WebClient client = new WebClient();
		String result = client.getContent(factory);
		
		assertNull(result);
	}

	@After
	public void tearDown() {
		verify(factory);
		verify(stream);
	}
	
}
