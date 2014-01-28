package org.swordess.javamisc.junit.chapter8;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SampleServletEasyMockTest {

	private SampleServlet servlet;
	private HttpServletRequest mockHttpServletRequest;
	private HttpSession mockHttpSession;

	@Before
	public void setUp() {
		servlet = new SampleServlet();
		mockHttpServletRequest = createStrictMock(HttpServletRequest.class);
		mockHttpSession = createStrictMock(HttpSession.class);
	}

	@Test
	public void testIsAuthenticatedAuthenticated() {
		// 1. define expectations
		expect(mockHttpServletRequest.getSession(eq(false))).andReturn(
				mockHttpSession);
		expect(mockHttpSession.getAttribute(eq("authenticated"))).andReturn(
				"true");
		
		// 2. expectations done
		replay(mockHttpServletRequest);
		replay(mockHttpSession);
		
		// 3. execute and verify
		assertTrue(servlet.isAuthenticated(mockHttpServletRequest));
	}

	@Test
	public void testIsAuthenticatedNotAuthenticated() {
		expect(mockHttpServletRequest.getSession(eq(false))).andReturn(
				mockHttpSession);
		expect(mockHttpSession.getAttribute(eq("authenticated"))).andReturn(
				"false");
		replay(mockHttpServletRequest);
		replay(mockHttpSession);
		assertFalse(servlet.isAuthenticated(mockHttpServletRequest));
	}
	
	@Test
	public void testIsAuthenticatedNoSession() {
		expect(mockHttpServletRequest.getSession(eq(false))).andReturn(null);
		replay(mockHttpServletRequest);
		replay(mockHttpSession);
		assertFalse(servlet.isAuthenticated(mockHttpServletRequest));
	}
	
	@After
	public void tearDown() {
		verify(mockHttpServletRequest);
		verify(mockHttpSession);
	}

}
