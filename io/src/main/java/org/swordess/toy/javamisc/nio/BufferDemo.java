package org.swordess.toy.javamisc.nio;

import java.nio.ByteBuffer;

public class BufferDemo {

	public void byteBuffer() {
		ByteBuffer buff = ByteBuffer.allocate(8);
		
		// 0x cafe babe
		buff.put((byte) 0xca);
		buff.putShort((short) 0xfeba);
		buff.put((byte) 0xbe);
		
		buff.flip();
		
		int data = buff.getInt();
		System.out.println(Integer.toHexString(data));
	}
	
	public static void main(String[] args) {
		new BufferDemo().byteBuffer();
	}
	
}
