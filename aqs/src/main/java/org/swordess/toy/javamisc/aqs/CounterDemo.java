package org.swordess.toy.javamisc.aqs;

import java.util.concurrent.CountDownLatch;

public class CounterDemo {

	private int concurrencyLevel;
	
	public CounterDemo(int concurrencyLevel) {
		this.concurrencyLevel = concurrencyLevel;
	}
	
	public void execute(final AtomicCounter counter) {
		final CountDownLatch ready = new CountDownLatch(concurrencyLevel);
		final CountDownLatch go = new CountDownLatch(1);
		final CountDownLatch done = new CountDownLatch(concurrencyLevel);

		for (int i = 0; i < concurrencyLevel; i++) {
			new Thread() {
				public void run() {
					ready.countDown();
					try { go.await(); } catch (InterruptedException e) {}
					counter.increment();
					done.countDown();
				}
			}.start();
		}
		
		try { ready.await(); } catch (InterruptedException e) {}
		go.countDown();
		try { done.await(); } catch (InterruptedException e) {}
	}
	
	public static void main(String[] args) {
		int concurrencyLevel = 50;
		CounterDemo demo = new CounterDemo(concurrencyLevel);

		AtomicCounter atomicCounter = new AtomicCounter();
		demo.execute(atomicCounter);
		System.out.println("atomicCounter.value == " + concurrencyLevel + ": "
				+ (concurrencyLevel == atomicCounter.getValue()));
	}
	
}
