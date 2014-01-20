package org.swordess.javamisc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface TestCaseAnalysis {
	
	public MethodAnalysis[] value();
	
	public static @interface MethodAnalysis {
		public String method();
		public EquivalentCondition[] equivalentConditions();
		public Boundary[] boundaries() default {};
	}
	
}
