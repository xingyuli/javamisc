package org.swordess.toy.javamisc.nio.tutorial;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockUsage {

	private static final int start = 10;
	private static final int end = 20;
	
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("usefilelocks.txt", "rw");
		FileChannel fc = raf.getChannel();
		
		System.out.println("trying to get lock");
		FileLock lock = fc.lock(start, end, false);
		System.out.println("got lock!");
		
		System.out.println("pausing");
		try { Thread.sleep(3000); } catch (InterruptedException e) {}
		
		System.out.println("going to release lock");
		lock.release();
		System.out.println("released lock");
		
		raf.close();
	}
	
}
