package org.swordess.toy.javamisc.junit.chapter7;

import java.io.InputStream;

import org.swordess.toy.javamisc.junit.chapter7.ConnectionFactory;

public class MockConnectionFactory implements ConnectionFactory {

	private InputStream inputStream;

	public void setData(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public InputStream getData() throws Exception {
		return inputStream;
	}

}
