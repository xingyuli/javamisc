package org.swordess.toy.javamisc.threading;

import java.util.concurrent.TimeUnit;

public class DaemonThreadDemo {

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

		// run as daemon
		t.setDaemon(true);
		t.start();

		// let the thread start its work
		try { Thread.sleep(2000); } catch (InterruptedException e) {}

		System.out.println("main done");

		// as the main thread has been finished, and only non-daemon threads
		// remaining, JVM will exit which means the thread t will not have
		// its task done

		/*
		 * output would be:
		 *   task begin
		 *   main done
		 *   JVM exit
		 */
	}

}
