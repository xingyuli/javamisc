package org.swordess.javamisc.exception;

import java.lang.Thread.UncaughtExceptionHandler;

public class UncaughtExceptionHandlerDemo {

	private static class MyHandler implements UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			System.err.println("Uncaught Exception thrown by " + t + ", detail:");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
		
		// t1 use the default uncaught exception handler
		Thread t1 = new Thread() {
			public void run() {
				throw new NullPointerException("just for demonstration");
			}
		};
		t1.start();
		
		// t2 use self-defined uncaught exception handler
		Thread t2 = new Thread() {
			public void run() {
				throw new ArrayIndexOutOfBoundsException("exception in Runnable");
			}
		};
		t2.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println(e.getClass() + "[" + e.getMessage() + "] thrown by " + t);
			}
		});
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread.setDefaultUncaughtExceptionHandler(null);
		throw new NumberFormatException("testing default handler of JVM");
	}
	
}
