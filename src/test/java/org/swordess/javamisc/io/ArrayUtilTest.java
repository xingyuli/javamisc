package org.swordess.javamisc.io;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.swordess.javamisc.Cover;

public class ArrayUtilTest {

	/*
	 * equals(byte[], int, byte[], int, int)
	 * 
	 * # Equivalent Conditions
	 * External Condition               Valid EC            Invalid EC
	 * ================================ =================== ===============================
	 * source array                     any(1)              N/A
	 * start point of source array      [0, src.length)(2)  (?, -1](3), [src.length, ?)(4)
	 * destination array                any(5)              N/A
	 * start point of destination array [0, dest.length)(6) (?, -1](7), [dest.length, ?)(8)
	 * -------------------------------- ------------------- -------------------------------
	 * number of elements to compare
	 *                                  >0(9)
	 *                                  && srcOffset+length<=src.length(10)
	 *                                  && destOffset+length<=dest.length(11)
	 *                                                      <=0(12),
	 *                                                      srcOffset+length>src.length(13),
	 *                                                      destOffset+length>dest.length(14)
	 * 
	 * # Boundary Analyze
	 * b1 - null source array
	 * b2 - empty source array
	 * b3 - source array with one element
	 * b4 - srcOffset = -1
	 * b5 - srcOffset = 0
	 * b6 - srcOffset = 1
	 * b7 - srcOffset = src.length - 1
	 * b8 - srcOffset = src.length
	 * b9 - srcOffset = src.length + 1
	 * b10 - null destination array
	 * b11 - empty destination array
	 * b12 - destination array with one element
	 * b13 - destOffset = -1
	 * b14 - destOffset = 0
	 * b15 - destOffset = 1
	 * b16 - destOffset = dest.length - 1
	 * b17 - destOffset = dest.length
	 * b18 - destOffset = dest.length + 1
	 * b19 - length = -1
	 * b20 - length = 0
	 * b21 - length = 1
	 * b22 - length = src.length - srcOffset
	 * b23 - length = src.length - srcOffset - 1
	 * b24 - length = src.length - srcOffset + 1
	 * b25 - length = dest.length - destOffset
	 * b26 - length = dest.length - destOffset - 1
	 * b27 - length = dest.length - destOffset + 1
	 * b28 - no elements equal
	 * b29 - only the first element equals
	 * b30 - all elements equal but the last one
	 */
	
	/* **************** cover Valid EC start **************** */
	
	@Cover(ec = {1,2,5,6,9,10,11}, boundaries = {6, 25, 29})
	@Test
	public void equals1() {
		assertTrue(!ArrayUtil.equals(
				new byte[] { 1, 10, 20, 30 }, 1,
				new byte[] { 1, 1, 2, 3, 5 }, 2,
				2
			));
	}
	
	/* **************** cover Valid EC end **************** */
	
	/* **************** cover Invalid EC start **************** */
	
	@Cover(ec = 3, boundaries = 4)
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void equals2() {
		ArrayUtil.equals(
				new byte[] { 1, 2, 3, 5, 7, 9 }, -1,
				new byte[] { 1, 1, 2, 3, 5 },    2,
				3
			);
	}
	
	@Cover(ec = 4, boundaries = 8)
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void equals3() {
		ArrayUtil.equals(
				new byte[] { 1, 2, 3, 5, 7, 9 }, 6,
				new byte[] { 1, 1, 2, 3, 5 },    2,
				3
			);
	}
	
	@Cover(ec = 7, boundaries = 13)
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void equals4() {
		ArrayUtil.equals(
				new byte[] { 1, 2, 3, 5, 7, 9 }, 1,
				new byte[] { 1, 1, 2, 3, 5 },    -1,
				3
			);
	}
	
	@Cover(ec = 8, boundaries = 17)
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void equals5() {
		ArrayUtil.equals(
				new byte[] { 1, 2, 3, 5, 7, 9 }, 1,
				new byte[] { 1, 1, 2, 3, 5 },    5,
				3
			);
	}
	
	@Cover(ec = 12, boundaries = 20)
	@Test(expected = IllegalArgumentException.class)
	public void equals6() {
		ArrayUtil.equals(
				new byte[] { 1, 2, 3, 5, 7, 9 }, 1,
				new byte[] { 1, 1, 2, 3, 5 },    2,
				0
			);
	}
	
	@Cover(ec = 13, boundaries = {15, 24, 26})
	@Test(expected = IllegalArgumentException.class)
	public void equals7() {
		ArrayUtil.equals(
				new byte[] { 1, 1, 2, 3, 5 },    2,
				new byte[] { 1, 2, 3, 5, 7, 9 }, 1,
				4
			);
	}
	
	@Cover(ec = 14, boundaries = 27)
	@Test(expected = IllegalArgumentException.class)
	public void equals8() {
		ArrayUtil.equals(
				new byte[] { 1, 2, 3, 5, 7, 9 }, 1,
				new byte[] { 1, 1, 2, 3, 5 },    2,
				4
			);

	}
	
	/* **************** cover Invalid EC end **************** */
	
	/* **************** cover rest Boundaries start **************** */
	
	@Cover(boundaries = {1, 10})
	@Test
	public void equals9() {
		assertTrue(ArrayUtil.equals(null, -1, null, -1, -1));
	}
	
	@Cover(boundaries = {2, 11})
	@Test
	public void equals10() {
		assertTrue(ArrayUtil.equals(new byte[0], -1, new byte[0], -1, -1));
	}
	
	@Cover(boundaries = {3, 5, 7, 12, 14, 16, 21, 22, 28})
	@Test
	public void equals11() {
		assertTrue(!ArrayUtil.equals(new byte[]{1}, 0, new byte[2], 0, 1));
	}
	
	@Cover(boundaries = 9)
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void equals12() {
		ArrayUtil.equals(new byte[]{1,2}, 3, new byte[]{1}, 0, 1);
	}
	
	@Cover(boundaries = 18)
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void equals13() {
		ArrayUtil.equals(new byte[]{1}, 0, new byte[]{1,2}, 3, 1);
	}
	
	@Cover(boundaries = 19)
	@Test(expected = IllegalArgumentException.class)
	public void equals14() {
		ArrayUtil.equals(new byte[]{1,2}, 0, new byte[]{1,2}, 0, -1);
	}
	
	@Cover(boundaries = 30)
	@Test
	public void equals15() {
		assertTrue(!ArrayUtil.equals(new byte[]{2,3}, 0, new byte[]{1,2,4}, 1, 2));
	}
	
	/* **************** cover rest Boundaries end **************** */
	
}
