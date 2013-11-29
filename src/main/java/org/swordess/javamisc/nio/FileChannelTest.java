package org.swordess.javamisc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.junit.Test;

public class FileChannelTest {

	@Test
	public void testRead() {
		FileInputStream inStream = null;
		
		try {
			String path = FileChannelTest.class.getResource("data/nio-data-read.txt").getPath();
			inStream = new FileInputStream(path);
			
			FileChannel inChannel = inStream.getChannel();
			
			// allocate the bytes on the Heap
			ByteBuffer buf = ByteBuffer.allocate(256);
			byte[] dst = new byte[256];
			
			byte zero = 0;
			int bytesRead = -1;
			while (-1 != (bytesRead = inChannel.read(buf))) {
				buf.position(0);
				buf.get(dst);
				buf.clear();
				
				String text = new String(dst, 0, bytesRead);
				Arrays.fill(dst, zero);
				
				System.out.print(text);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inStream) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	@Test
	public void testReadWhenIKnowMoreAboutBufferAndChannel() {
		String path = FileChannelTest.class.getResource("").getPath() + "data/nio-data-read.txt";
		
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(path);
			FileChannel inChannel = inStream.getChannel();
			
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			byte[] buff = new byte[1024];
			StringBuilder content = new StringBuilder();
			while (inChannel.read(buffer) > 0) {
				buffer.flip();
				buffer.get(buff, 0, buffer.limit());
				
				content.append(new String(buff, 0, buffer.limit()));
				buffer.clear();
			}
			System.out.println(content);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inStream) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	@Test
	public void testWrite() {
		String path = FileChannelTest.class.getResource("").getPath() + "data/nio-data-write.txt";
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
		
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(path);
			FileChannel outChannel = outStream.getChannel();
			
			ByteBuffer buf = ByteBuffer.wrap("This is nothing special!".getBytes());
			while (buf.hasRemaining()) {
				outChannel.write(buf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != outStream) {
				try {
					outStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
}
