package org.swordess.javamisc.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import static junit.framework.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Source:
 * http://blog.yohanliyanage.com/2010/10/ktjs-3-soft-weak-phantom-references/
 * <p>
 * 
 * On Oracle(Sun)'s HotSpot JVM:<br>
 * Strong > Soft > Weak > Phantom<br>
 * 
 * This is a demonstration on Oracle(Sun)'s JVM implementation which means
 * might not work for some other JVM implementors.
 */
@Ignore("This is not a real test!")
public class RefDemoOfBlog {

	/**
	 * 1. Soft references are most often used to implement memory-sensitive
	 *    caches.
	 * 2. All soft references to softly-reachable objects are guaranteed to
	 *    have been cleared before the virtual machine throws an
	 *    OutOfMemoryError.
	 */
	@Test
	public void testSoftRef() {
		// Make a Soft reference
		SoftReference<Object> softRef = new SoftReference<Object>(new Object());
		
		// Get a strong reference, and make it eligible for GC !
		Object obj = softRef.get();
		obj = null;
		
		System.gc();
		
		// since JVM has enough memory, it didn't reclaim the memory
		// consumed by our softly referenced instance
		assertNotNull(softRef.get());
	}
	
	/**
	 * 1. Weak references are most often used to implement canonicalizing
	 *    mappings.
	 * 2. Unlike SoftReference, Weak references can be reclaimed by the JVM
	 *    during a GC cycle, even though there's enough free memory available.
	 */
	@Test
	public void testWeakRef() {
		// Make a Weak reference
		WeakReference<Object> weakRef = new WeakReference<Object>(new Object());
		
		// as long as the GC does not occur, we can retrieve a strong reference
		// out of a weak reference by calling the ref.get() method
		assertNotNull(weakRef.get());

		// Get a strong reference again. Now its not eligible for GC
		Object strongRef = weakRef.get();
		System.gc();
		
		// as we still hold a strong reference, thus it's not weak-reachable,
		// the GC will not reclaim our weak reference
		assertNotNull(weakRef.get());
		
		// Make the instance eligible for GC again
		strongRef = null;
		System.gc();
		
		// as the obj instance is weak reachable, JVM reclaim the memory of that
		// instance
		assertNull(weakRef.get());
	}
	
	@Test
	public void testPhantomRefQueue() throws InterruptedException {
		final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
		PhantomReference<Object> phantomRef = new PhantomReference<Object>(new Object(), queue);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("Awaiting for GC");
					// This will block till it is GCd
					@SuppressWarnings("unchecked")
					PhantomReference<Object> ref = (PhantomReference<Object>) queue.remove();
					System.out.println(ref);
					System.out.println("Referenced GC'd");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		// Wait for 2nd thread to start
		Thread.sleep(2000);
		
		System.out.println("Invoking GC");
		System.gc();
	}
	
}
