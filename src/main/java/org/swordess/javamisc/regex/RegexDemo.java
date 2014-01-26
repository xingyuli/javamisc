package org.swordess.javamisc.regex;

import java.util.regex.Pattern;

public class RegexDemo {

	public void backReference() {
		System.out.println(Pattern.matches("(\\d\\d)\\1\\1", "121212"));
		System.out.println(Pattern.matches("(\\d{4})\\w{2}\\1", "1111aa1111"));
	}
	
}
