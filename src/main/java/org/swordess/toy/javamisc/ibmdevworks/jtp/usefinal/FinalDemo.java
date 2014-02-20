package org.swordess.toy.javamisc.ibmdevworks.jtp.usefinal;

public class FinalDemo {

	private final WithExceptionWhenConstruct obj;
	
	public FinalDemo() {
		// this would not be accepted by the compiler
		/*
		try {
			obj = new WithExceptionWhenConstruct();
		} catch (Exception e) {
			obj = null;
		}
		*/
		
		// while this would work
		WithExceptionWhenConstruct temp;
		try {
			temp = new WithExceptionWhenConstruct();
		} catch (Exception e) {
			temp = null;
		}
		obj = temp;
	}
	
}

class WithExceptionWhenConstruct {
	
	public WithExceptionWhenConstruct() throws Exception {
	}
	
}
