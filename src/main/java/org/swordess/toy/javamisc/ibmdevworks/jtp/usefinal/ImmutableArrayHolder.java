package org.swordess.toy.javamisc.ibmdevworks.jtp.usefinal;

public final class ImmutableArrayHolder {

	private final int[] array;
	
	public ImmutableArrayHolder(int[] array) {
		this.array = array.clone();
	}
	
	public int get(int index) {
		return array[index];
	}
	
	public int getArraylength() {
		return array.length;
	}
	
	public int[] getArray() {
		return array.clone();
	}
	
}
