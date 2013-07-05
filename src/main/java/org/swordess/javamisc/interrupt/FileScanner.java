package org.swordess.javamisc.interrupt;

import java.io.File;
import java.util.Scanner;

public class FileScanner {

	private static void listFile(File f) throws InterruptedException {
		if (f == null) {
			throw new IllegalArgumentException();
		}
		
		if (f.isFile()) {
			System.out.println(f);
			return;
		}
		
		File[] allFiles = f.listFiles();
		if (Thread.interrupted()) {
			throw new InterruptedException("文件扫描任务被中断");
		}
		
		for (File file : allFiles) {
			listFile(file);
		}
	}
	
	public static String readFromConsole() {
		Scanner scanner = new Scanner(System.in);
		try {
			return scanner.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] args) {
		final Thread fileIteratorThread = new Thread() {
			public void run() {
				try {
					listFile(new File("C:\\xingyuli"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		new Thread() {
			public void run() {
				while (true) {
					if ("quit".equalsIgnoreCase(readFromConsole())) {
						if (fileIteratorThread.isAlive()) {
							fileIteratorThread.interrupt();
							return;
						}
					} else {
						System.out.println("输入quit退出文件扫描");
					}
				}
			}
		}.start();
		
		fileIteratorThread.start();
	}

}
