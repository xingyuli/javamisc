package org.swordess.toy.javamisc.xp;

import static org.junit.Assert.*;
import org.junit.Test;

public class CheckForPrimeTest {

	private CheckForPrime checkForPrime = new CheckForPrime();
	
	// Test case 1
	@Test
	public void testCheckPrime_true() {
		assertTrue(checkForPrime.primeCheck(3));
	}
	
	// Test cases 2,3
	@Test
	public void testCheckPrime_false() {
		assertFalse(checkForPrime.primeCheck(0));
		assertFalse(checkForPrime.primeCheck(1000));
	}
	
	// Test case 7
	@Test
	public void testCheckPrime_checkArgs_char_input() {
		try {
			String[] args = new String[]{"r"};
			checkForPrime.checkArgs(args);
			fail("Should raise an Exception.");
		} catch (Exception success) {
			// successful test
		}
	}
	
	// Test case 5
	@Test
	public void testCheckPrime_checkArgs_above_upper_bound() {
		try {
			String[] args = new String[]{"1001"};
			checkForPrime.checkArgs(args);
			fail("Should raise an Exception.");
		} catch (Exception success) {
			// successful test
		}
	}
	
	// Test case 4
	@Test
	public void testCheckPrime_checkArgs_neg_input() {
		try {
			String[] args = new String[]{"-1"};
			checkForPrime.checkArgs(args);
			fail("Should raise an Exception.");
		} catch (Exception success) {
			// successful test
		}
	}
	
	// Test case 6
	@Test
	public void testCheckPrime_checkArgs_2_inputs() {
		try {
			String[] args = new String[]{"5", "99"};
			checkForPrime.checkArgs(args);
			fail("Should raise an Exception.");
		} catch (Exception success) {
			// successful test
		}
	}
	
	// Test case 8
	@Test
	public void testCheckPrime_checkArgs_0_inputs() {
		try {
			String[] args = new String[0];
			checkForPrime.checkArgs(args);
			fail("Should raise an Exception.");
		} catch (Exception success) {
			// successful test
		}
	}
	
}
