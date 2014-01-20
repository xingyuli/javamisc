package org.swordess.javamisc.io;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.swordess.javamisc.io.InputStreamProvider.InputStreamUser;

public class InputStreamProviderTest {

	/*
	 * constructor - InputStreamProvider(String)
	 * 
	 * # Equivalent Conditions(EC):
	 * External Condition  Valid EC             Invalid EC
	 * ------------------  -------------------  ----------------------
	 * path of the file    non-empty string(1)  null(2), ""(3), " "(4)
	 * whether file exist  yes(5)               no(6)
	 * 
	 * # Boundary Analyze
	 * path of null(b1)
	 * path of ""(b2)
	 * path of " "(b3)
	 * 
	 * # TestCase
	 * TC1: 1(5)  - non-empty and exist
	 * TC2: 2(b1) - null
	 * TC3: 3(b2) - ""
	 * TC4: 4(b3) - " "
	 * TC5: 6     - non-empty but not exist
	 */
	
	@Test
	public void instantiateInputStreamProvider1() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("tc1.tmp"));
		assertNotNull(provider);
	}

	@Test(expected = IllegalArgumentException.class)
	public void instantiateInputStreamProvider2() throws FileNotFoundException {
		new InputStreamProvider(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void instantiateInputStreamProvider3() throws FileNotFoundException {
		new InputStreamProvider("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void instantiateInputStreamProvider4() throws FileNotFoundException {
		new InputStreamProvider(" ");
	}
	
	@Test(expected = FileNotFoundException.class)
	public void instantiateInputStreamProvider5() throws FileNotFoundException {
		new InputStreamProvider(FileUtil.ensureNonExistence("tc5.tmp"));
	}
	
	/*
	 * InputStreamProvider#usedBy(InputStreamUser)
	 * 
	 * # Equivalent Conditions
	 * External Condition  Valid EC     Invalid EC
	 * ------------------  -----------  ----------
	 * user of stream      non-null(1)  null(2)
	 * 
	 * # Boundary Analyze
	 * user of null(b1)
	 * user closed the stream(b2)
	 * 
	 * # TestCase
	 * TC1: 1     - non-null user and didn't close the stream
	 * TC2: 2(b1) - null user
	 * TC3: b2    - non-null user and close the stream explicitly
	 */
	
	/* ******************* cover Valid EC start ******************* */
	
	@Test
	public void usedBy1() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBy1.tmp"));
		provider.usedBy(new InputStreamUser() {
			@Override
			public void use(InputStream in) throws IOException {
				// do nothing and let the stream provide to close
			}
		});
	}
	
	/* ******************* cover Valid EC end ******************* */

	/* ******************* cover Invalid EC start ******************* */
	
	@Test(expected = NullPointerException.class)
	public void usedBy2() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBy2.tmp"));
		provider.usedBy((InputStreamUser)null);
	}
	
	@Test
	public void usedBy3() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBy3.tmp"));
		provider.usedBy(new InputStreamUser() {
			@Override
			public void use(InputStream in) throws IOException {
				// close the stream in the client code
				in.close();
			}
		});
	}

	/* ******************* cover Invalid EC end ******************* */
	
	/*
	 * InputStreamProvider#usedBy(Collection<InputStreamUser>)
	 * 
	 * # Equivelent Condition
	 * External Condition  Valid EC  Invalid EC
	 * ------------------  --------  ----------
	 * users of stream     non-null(1)  null(2)
	 * 
	 * # Boundary Analyze
	 * users of null(b1)
	 * users with empty elements(b2)
	 * users with null elements(b3)
	 * 
	 * # TestCase
	 * TC1: 1     - non-null users and with non-null elements
	 * TC2: 2(b1) - null users
	 * TC3: b2    - non-null users and with empty elements
	 * TC4: b3    - non-null users and with null elements
	 * 
	 * According to the specification, we need to verify whether the clients be
	 * used in sequential order. But we can use the TC1 to do this work.
	 */

	private static int ACTUAL_INVOKE_NUMBER = 0;
	
	/* ******************* cover Valid EC start ******************* */
	
	@Test
	public void usedBySeveralUsers1() throws FileNotFoundException {
		Collection<RecordableStreamUser> users = new ArrayList<RecordableStreamUser>();
		users.add(new RecordableStreamUser());
		users.add(new RecordableStreamUser());
		users.add(new RecordableStreamUser());
		users.add(new RecordableStreamUser());
		
		// set up the expected invoke number for each stream user
		int iterationOrder = 0;
		for (RecordableStreamUser user : users) {
			user.setExpectedInvokeNumber(++iterationOrder);
		}
		
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers1.tmp"));
		provider.usedBy(users);
		
		// verify the invoke number for each stream user
		for (RecordableStreamUser user : users) {
			assertEquals(user.getExpectedInvokeNumber(), user.getActualInvokeNumber());
		}
	}
	
	/* ******************* cover Valid EC end ******************* */
	
	/* ******************* cover Invalid EC start ******************* */
	
	@Test(expected = NullPointerException.class)
	public void usedBySeveralUsers2() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers2.tmp"));
		provider.usedBy((Collection<InputStreamUser>)null);
	}
	
	@Test
	public void usedBySeveralUsers3() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers3.tmp"));
		provider.usedBy(Collections.<InputStreamUser>emptySet());
	}
	
	@Test
	public void usedBySeveralUsers4() throws FileNotFoundException {
		List<InputStreamUser> usersWithNullElements = new ArrayList<InputStreamUser>(16);
		Collections.fill(usersWithNullElements, null);
		
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers4.tmp"));
		provider.usedBy(usersWithNullElements);
	}
	
	/* ******************* cover Invalid EC end ******************* */
	
	private static class RecordableStreamUser implements InputStreamUser {

		private int expectedInvokeNumber;
		private int actualInvokeNumber;

		public void use(InputStream in) throws IOException {
			// do nothing but remember the actual invoke number
			actualInvokeNumber = ++ACTUAL_INVOKE_NUMBER;
		}

		void setExpectedInvokeNumber(int expectedInvokeNumber) {
			this.expectedInvokeNumber = expectedInvokeNumber;
		}

		int getExpectedInvokeNumber() {
			return expectedInvokeNumber;
		}

		int getActualInvokeNumber() {
			return actualInvokeNumber;
		}
		
	}
	
}
