package org.swordess.javamisc.jdk7.changenotifier;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 * Source:
 * http://www.javaworld.com/javaworld/jw-10-2012/121016-maximize-java-nio-and-nio2-for-application-responsiveness.html?page=2
 */
public class Watcher {
	
	public static void main(String[] args) {
		Path this_dir = Paths.get(".");
		System.out.println("Now watching the current directory...");
		
		try {
			WatchService watcher = this_dir.getFileSystem().newWatchService();
			this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
			
			WatchKey watchKey = watcher.take();
			List<WatchEvent<?>> events = watchKey.pollEvents();
			for (WatchEvent<?> event : events) {
				System.out.println("Someone just created the file '"
						+ event.context().toString() + "'.");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
}
