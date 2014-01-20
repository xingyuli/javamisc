package org.swordess.javamisc.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Indicates a client to use a certain InputStream. This client has no
 * responsibility to close the stream passed in as it will always be closed
 * by {@link InputStreamProvider}. 
 * 
 * @author Liu Xingyu <xingyulliiuu@gmail.com>
 */
public interface InputStreamUser {

	/**
	 * Specify how to use the stream passed in.
	 * 
	 * @param in
	 * @throws IOException
	 */
	public void use(InputStream in) throws IOException;
	
}
