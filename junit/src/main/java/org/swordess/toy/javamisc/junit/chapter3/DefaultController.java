package org.swordess.toy.javamisc.junit.chapter3;

import java.util.HashMap;
import java.util.Map;

public class DefaultController implements Controller {

	private Map<String, RequestHandler> requestHandlers = new HashMap<>();
	
	@Override
	public Response processRequest(Request req) {
		Response resp = null;
		try {
			resp = getHandler(req).process(req);
		} catch (Exception e) {
			resp = new ErrorResponse(req, e);
		}
		return resp;
	}

	@Override
	public void addHandler(Request req, RequestHandler handler) {
		if (requestHandlers.containsKey(req.getName())) {
			throw new RuntimeException(
					"A request handler has already been registered for request "
							+ "name " + "[" + req.getName() + "]");
		}
		requestHandlers.put(req.getName(), handler);
	}
	
	protected RequestHandler getHandler(Request req) {
		if (!requestHandlers.containsKey(req.getName())) {
			throw new RuntimeException("Cannot find handler for request name "
					+ "[" + req.getName() + "]");
		}
		return requestHandlers.get(req.getName());
	}

}
