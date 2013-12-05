package org.swordess.javamisc.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Source:
 * http://www.javaworld.com/javaworld/jw-10-2012/121016-maximize-java-nio-and-nio2-for-application-responsiveness.html?page=4
 */
public class MemMapExample {
	
	private static int memMapSize = 20 * 1024 * 1024;
	private static String fn = "example_memory_mapped_file.txt";

	public static void main(String[] args) throws IOException {
		RandomAccessFile memoryMappedFile = new RandomAccessFile(fn, "rw");
		
		// Mapping a file into memory
		MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, memMapSize);
		
		// Writing into Memory Mapped File
		for (int i = 0; i < memMapSize; i++) {
			out.put((byte) 'A');
		}
		System.out.println("File '" + fn + "' is now " + Integer.toString(memMapSize) + " bytes full.");
		
		// Read from memory-mapped file.
		for (int i = 0; i < 30; i++) {
			System.out.println((char) out.get(i));
		}
		System.out.println("\nReading from memory-mapped file '" + fn + "' is complete.");
		
		memoryMappedFile.close();
	}
	
}
