package org.swordess.javamisc.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputStreamProvider {

	private static final Logger LOG = Logger.getLogger("input-stream-provider");
	
	private final String path;
	
	public InputStreamProvider(String path) {
		this.path = path;
	}
	
	public void usedBy(InputStreamUser inUser) {
		InputStream in = null;
		try {
			LOG.log(Level.INFO, "opening InputStream for " + path + " ...");
			in = new FileInputStream(path);
			inUser.use(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					LOG.log(Level.INFO, "closing InputStream ...");
					in.close();
					LOG.log(Level.INFO, "InputStream closed");
				} catch (IOException e) {
					LOG.log(Level.WARNING, "cannot close InputStream", e);
				}
			}
		}
	}
	
	public void usedBy(Collection<InputStreamUser> inUsers) {
		BufferedInputStream in  = null;
		try {
			LOG.log(Level.INFO, "opening BufferedInputStream for " + path + " ...");
			in = new BufferedInputStream(new FileInputStream(path));
			for (InputStreamUser inUser : inUsers) {
				in.mark(Integer.MAX_VALUE);
				inUser.use(in);
				in.reset();
				LOG.log(Level.INFO, "stream has been reset");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					LOG.log(Level.INFO, "closing BufferedInputStream ...");
					in.close();
					LOG.log(Level.INFO, "BufferedInputStream closed");
				} catch (IOException e) {
					LOG.log(Level.WARNING, "cannot close BufferedInputStream", e);
				}
			}
		}
	}
	
}
