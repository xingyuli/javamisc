package org.swordess.javamisc.junit.chapter8;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SampleServlet extends HttpServlet {

	public boolean isAuthenticated(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (null == session) {
			return false;
		}

		String authenticationAttribute = (String) session
				.getAttribute("authenticated");
		return Boolean.valueOf(authenticationAttribute);
	}

}
