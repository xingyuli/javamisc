package org.swordess.javamisc.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class FileUtil {

	static String ensureExistence(String filename) {
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		file.deleteOnExit();
		return file.getAbsolutePath();
	}
	
	static String ensureNonExistence(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
		return file.getAbsolutePath();
	}
	
	static void write(String filename, byte b) throws IOException {
		FileOutputStream out = new FileOutputStream(filename);
		out.write(b);
		out.close();
	}
	
	static void write(String filename, byte[] b) throws IOException {
		FileOutputStream out = new FileOutputStream(filename);
		out.write(b);
		out.close();
	}
	
	private FileUtil() {
	}
	
}
