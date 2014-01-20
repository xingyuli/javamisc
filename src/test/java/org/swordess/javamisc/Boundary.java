package org.swordess.javamisc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Boundary {
	public int nbr();
	public String desc();
}
