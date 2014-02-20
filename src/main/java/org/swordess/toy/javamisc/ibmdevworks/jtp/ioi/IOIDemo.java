package org.swordess.toy.javamisc.ibmdevworks.jtp.ioi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IOIDemo {

	private static Set<String> patternSet;
	static {
		Set<String> temp = new HashSet<>();
		temp.add("\b(roast beef)\b");
		temp.add("\b(on rye)\b");
		temp.add("\b(with mustard)\b");
		patternSet = Collections.unmodifiableSet(temp);
	}
	
	private static Set<String> patternSetUsingIOI
		= new SetAdapter<>(new HashSet<String>())
			.append("\b(roast beef)\b")
			.append("\b(on rye)\b")
			.append("\b(with mustard)\b")
			.unmodifiableSet();

}
