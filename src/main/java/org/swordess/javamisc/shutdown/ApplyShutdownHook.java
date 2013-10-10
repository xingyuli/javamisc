package org.swordess.javamisc.shutdown;

import java.util.Scanner;

public class ApplyShutdownHook {

	public static void main(String[] args) {
		/*
		 * This shutdown hook will be triggered when program exits normally, including:
		 * (1) all non-daemon thread are finished or
		 * (2) System.exit is called
		 * or when Control-C send SIGINT signal.
		 * 
		 * While the kill -9 will not.
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				new Throwable("debug shutdown").printStackTrace();
			}
		});
		
		System.out.println("Please input something:");
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if ("quit".equals(line)) {
				break;
			} else {
				System.out.println("continue ...");
			}
		}
		
		System.out.println("program exit");
		System.exit(0);
	}
	
}
