package org.swordess.toy.javamisc.junit.chapter7;

import java.io.InputStream;
 
public class WebClient {

	public String getContent(ConnectionFactory connectionFactory) {
		try (InputStream is = connectionFactory.getData()) {
			StringBuffer content = new StringBuffer();
			int data;
			while (-1 != (data = is.read())) {
				content.append(Character.toChars(data));
			}
			return content.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
}
