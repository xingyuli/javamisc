package org.swordess.toy.javamisc.enumeration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EnumServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1234);
			Socket socket = ss.accept();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Weekday day = (Weekday) in.readObject();

			if (day == Weekday.FRIDAY) {
				System.out.println("is same object");
			} else {
				System.out.println("isn't same object");
			}
			
			switch (day) {
			case FRIDAY:
				System.out.println("switch-case is FRIDAY");
				break;
			default:
				System.out.println("switch-case failed");
			}
		} finally {
			if (null != ss) {
				ss.close();
			}
		}
	}
	
}
