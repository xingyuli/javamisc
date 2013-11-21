package org.swordess.javamisc.regex;

import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

	@Test
	public void testBackReference() {
		System.out.println(Pattern.matches("(\\d\\d)\\1\\1", "121212"));
		System.out.println(Pattern.matches("(\\d{4})\\w{2}\\1", "1111aa1111"));
	}
	
}
