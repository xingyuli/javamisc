package org.swordess.javamisc.nio.tutorial;

import java.nio.ByteBuffer;

import org.junit.Test;

public class BufferUsage {

	@Test
	public void testWrap() {
		byte[] internal = "Hello World!".getBytes();
		
		// will share the same underlying data array
		ByteBuffer buffer = ByteBuffer.wrap(internal);
		System.out.println(new String(buffer.array()));
		
		internal[internal.length - 1] = '.';
		System.out.println(new String(buffer.array()));
	}
	
	@Test
	public void testSlice() {
		// will produce 8 bytes
		byte[] data = "Java NIO".getBytes();
		
		// limit = capacity = array.length
		// position = 0
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		// window from [5, 8)
		buffer.position(5);
		ByteBuffer sliceOfBuffer = buffer.slice();
		
		// buffer produces by slice will have independent variables
		System.out.println(sliceOfBuffer.position());
		System.out.println(sliceOfBuffer.limit());
		System.out.println(sliceOfBuffer.capacity());
		
		System.out.println(new String(sliceOfBuffer.array(), 5, data.length - 5));
		
		// but they still share the same underlying data array
		System.out.println(sliceOfBuffer.array() == buffer.array());
	}
	
}
