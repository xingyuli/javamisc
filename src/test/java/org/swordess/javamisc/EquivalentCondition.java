package org.swordess.javamisc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface EquivalentCondition {
	
	public String name();
	public Condition[] valid();
	public Condition[] invalid() default {};
	
	public static @interface Condition {
		public int nbr();
		public String desc();
	}
	
}
