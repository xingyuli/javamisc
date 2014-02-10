package org.swordess.toy.javamisc.concurrent.nonblocking;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

	private AtomicInteger value = new AtomicInteger();
	
	public int getValue() {
		return value.get();
	}
	
	public void increment() {
		// this is just a demonstration, in fact AtomicInteger provides
		// getAndIncrement() for us
		for (;;) {
			int current = value.get();
			int next = current + 1;
			if (value.compareAndSet(current, next)) {
				break;
			}
		}
	}
	
}
