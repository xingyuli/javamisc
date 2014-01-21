package org.swordess.javamisc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Cover {
	
	public int[] validECs() default {};
	public int[] invalidECs() default {};
	
	public int[] boundaries() default {};
	
	public String[] conditions() default {};
	
}
