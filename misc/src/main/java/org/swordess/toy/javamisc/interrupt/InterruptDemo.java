package org.swordess.toy.javamisc.interrupt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


public class InterruptDemo {

	public static void main(String[] args) {
		ExecutorService es = Executors.newSingleThreadExecutor();
		final FutureTask<Void> task = new FutureTask<Void>(new Callable<Void>() {
			@Override
			public Void call() {
				int limit = 10;
				for (int i = 0; i < limit; i++) {
					if (Thread.interrupted()) {
						System.out.println("task has been INTERRUPTED, discard rest " + (limit - i) + " pieces of data");
						break;
					}
					
					System.out.println("current: " + i);
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						System.out.println("sleep interrupted");
						Thread.currentThread().interrupt();
					}
				}
				System.out.println("----------- task end -----------");
				return null;
			}
		});
		es.execute(task);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
				}
				if (task.cancel(true)) {
					System.out.println("cancellation succeeded!");
				}
			}
		}).start();
		
		es.shutdown();
	}
	
}
