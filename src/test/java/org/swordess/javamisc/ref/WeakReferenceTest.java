package org.swordess.javamisc.ref;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("This is not a real test!")
public class WeakReferenceTest {

	@Test
	public void testWeakReferenceUsingARuntimeConstantStringAsTheReferent() {
		WeakReference<String> weakRef = new WeakReference<String>("aha");
		
		String strongRef = weakRef.get();
		assertNotNull(strongRef);
		
		// make the referent weakly reachable
		strongRef = null;
		
		// As the referent string "aha" is placed in Permanent Generation /
		// Method Area's Runtime Constant Pool, the referent string "aha" will
		// not be reclaimed even if it is weakly reachable
		System.gc();
		
		assertNotNull(weakRef.get());
	}
	
	/* ********************************************************************* */
	
	// NOTE: the new String(...) is used as the referent, thus we allocate the
	// memory in the Java Heap rather than the Runtime Constant Pool
	private static WeakReference<String> email = new WeakReference<String>(new String("xxx@yyy.com"));
	
	@Test
	public void testStaticWeakReference() {
		String emailRef = email.get();
		assertNotNull(emailRef);
		
		// make email's referent weakly reachable
		emailRef = null;
		
		// even though email is strongly reachable because of static variable
		// is in scope of GC Roots
		// email's referent will be reclaimed  
		System.gc(); 

		assertNull(email.get());
	}

	/* ********************************************************************* */
	
	@Test
	public void testLocalWeakReference() {
		// NOTE: still use a new String(...) as the referent
		WeakReference<String> localEmail = new WeakReference<String>(new String("ccc@ddd.com"));
		
		String localEmailRef = localEmail.get();
		assertNotNull(localEmailRef);
		
		// make localEmail's referent weakly reachable
		localEmailRef = null;
		
		// localEmail's referent will be reclaimed
		System.gc();
		
		assertNull(localEmail.get());
	}
	
	/* ********************************************************************* */
	
	private static WeakHashMap<String, String> userEmailToCostcenter = new WeakHashMap<String, String>();
	
	@Test
	public void testWeakHashMap() {
		initCache();
		assertNotNull(userEmailToCostcenter.get("222@aaa.com"));
		
		System.gc();
		assertNull(userEmailToCostcenter.get("222@aaa.com"));
	}
	
	private static void initCache() {
		// as long as using new String(...) when add the keys, the entries
		// could be automatically reclaimed after GC
		userEmailToCostcenter.put(new String("111@aaa.com"), "111111");
		userEmailToCostcenter.put(new String("222@aaa.com"), "222222");
		userEmailToCostcenter.put(new String("333@aaa.com"), "333333");
	}
	
}
