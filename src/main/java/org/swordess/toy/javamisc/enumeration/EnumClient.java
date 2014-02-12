package org.swordess.toy.javamisc.enumeration;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EnumClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 1234);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(Weekday.FRIDAY);
			out.flush();
		} finally {
			if (null != socket) {
				socket.close();
			}
		}
	}
	
}
