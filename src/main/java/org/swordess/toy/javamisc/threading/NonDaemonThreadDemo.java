package org.swordess.toy.javamisc.threading;

import java.util.concurrent.TimeUnit;

public class NonDaemonThreadDemo {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("JVM exit");
			}
		});

		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println("task begin");
				try {
					TimeUnit.SECONDS.sleep(5);
					System.out.println("task done normally");
				} catch (InterruptedException e) {
					System.out.println("task done abnormally");
				}
			}
		});

		// A thread will use its parent thread's daemon setting, thus
		// thread t will be non-daemon as the main thread is non-daemon.
		t.start();
		
		// let the thread start its work
		try { Thread.sleep(2000); } catch (InterruptedException e) {}

		System.out.println("main done");

		// And as the JVM only will exit when all non-daemon threads
		// have been finished, thus the thread t will finish the task
		// normally.
		
		/*
		 * output would be:
		 *   task begin
		 *   main done
		 *   task done normally
		 *   JVM exit
		 */
	}
	
}
