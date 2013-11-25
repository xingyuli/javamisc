package org.swordess.javamisc.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamUser {

	public abstract void use(InputStream in) throws IOException;
	
}
