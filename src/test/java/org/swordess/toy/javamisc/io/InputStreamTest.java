package org.swordess.toy.javamisc.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;
import org.swordess.toy.javamisc.io.InputStreamProvider;
import org.swordess.toy.javamisc.io.InputStreamProvider.InputStreamUser;


@Ignore("Out of dated since these test cases are very poor..., thus cannot be used as real test!")
public class InputStreamTest {

	@Test
	public void testUsedByOneUser() throws FileNotFoundException {
		InputStreamProvider inProvider = new InputStreamProvider(absolutePathOf("io-read.txt"));
		inProvider.usedBy(new LineNumberedUser());
	}
	
	@Test
	public void testUsedByMultipleUsers() throws FileNotFoundException {
		Collection<InputStreamUser> inUsers = new ArrayList<InputStreamUser>();
		inUsers.add(new LineNumberedUser());
		inUsers.add(new InputStreamUser() {
			public void use(InputStream in) throws IOException {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder content = new StringBuilder();
				String line = null;
				while (null != (line=reader.readLine())) {
					line = line.replace('a', 'A')
							   .replace('e', 'E')
							   .replace('i', 'I')
							   .replace('o', 'O')
							   .replace('u', 'U');
					content.append(line);
					content.append('\n');
				}
				System.out.println(content);
			}
		});
		
		InputStreamProvider inProvider = new InputStreamProvider(absolutePathOf("io-read.txt"));
		inProvider.usedBy(inUsers);
	}
	
	private static class LineNumberedUser implements InputStreamUser {
		
		@Override
		public void use(InputStream in) throws IOException {
			LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(in))) ;
			String line = null;
			while (null != (line=reader.readLine())) {
				System.out.println(reader.getLineNumber() + ": " + line);
			}
		}
		
	}
	
	private static String absolutePathOf(String filename) {
		return InputStreamTest.class.getResource("").getPath() + filename;
	}
	
}
