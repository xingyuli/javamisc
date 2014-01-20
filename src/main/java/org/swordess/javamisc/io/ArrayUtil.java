package org.swordess.javamisc.io;

public class ArrayUtil {

	public static boolean isPartialEquals(byte[] src, int srcOffset,
			byte[] dest, int destOffset, int length) {
		
		if (null == src && null == dest) {
			return true;
		}
		
		if (null == src || null == dest) {
			return false;
		}

		if (srcOffset < 0) {
			throw new IllegalArgumentException(
					"srcOffset should be larger or equal than 0");
		}
		if (destOffset < 0) {
			throw new IllegalArgumentException(
					"destOffset should be larger or equal than 0");
		}
		
		if (srcOffset >= src.length) {
			throw new IllegalArgumentException(
					"srcOffset should be less than the length of src");
		}
		if (destOffset >= dest.length) {
			throw new IllegalArgumentException(
					"destOffset should be less than the length of dest");
		}
		
		if (srcOffset + length > src.length) {
			throw new IllegalArgumentException(
					"length exceed the upper bounds of src when starting from the offset "
							+ srcOffset);
		}
		if (destOffset + length > dest.length) {
			throw new IllegalArgumentException(
					"length exceed the upper bounds of dest when starting from the offset "
							+ destOffset);
		}
		
		for (int i = 0; i < length; i++) {
			if (!(src[srcOffset + i] == dest[destOffset + i])) {
				return false;
			}
		}
		return true;
	}
	
	private ArrayUtil() {
	}
	
}
