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
import org.swordess.javamisc.Boundary;
import org.swordess.javamisc.EquivalentCondition.Condition;
import org.swordess.javamisc.Cover;
import org.swordess.javamisc.EquivalentCondition;
import org.swordess.javamisc.TestCaseAnalysis;
import org.swordess.javamisc.TestCaseAnalysis.MethodAnalysis;
import org.swordess.javamisc.io.InputStreamProvider.InputStreamUser;

@TestCaseAnalysis({
	@MethodAnalysis(
		method = "InputStreamProvider(String)",
		equivalentConditions = {
			@EquivalentCondition(
				name    = "path of the file",
				valid   = @Condition(nbr = 1, desc = "non-empty string"),
				invalid = {
					@Condition(nbr = 2, desc = "null"),
					@Condition(nbr = 3, desc = "''"),
					@Condition(nbr = 4, desc = "' '"),
				}
			),
			@EquivalentCondition(
				name    = "whether file exist",
				valid   = @Condition(nbr = 5, desc = "yes"),
				invalid = @Condition(nbr = 6, desc = "no")
			)
		},
		boundaries = {
			@Boundary(nbr = 1, desc = "null"),
			@Boundary(nbr = 2, desc = "''"),
			@Boundary(nbr = 3, desc = "' '"),
		}
	),
	@MethodAnalysis(
		method = "usedBy(InputStreamUser)",
		equivalentConditions = {
			@EquivalentCondition(
				name    = "user of stream",
				valid   = @Condition(nbr = 1, desc = "non-null"),
				invalid = @Condition(nbr = 2, desc = "null")
			)
		},
		boundaries = {
			@Boundary(nbr = 1, desc = "user of null"),
			@Boundary(nbr = 2, desc = "user closed the stream")
		}
	),
	@MethodAnalysis(
		method = "usedBy(Collection<InputStreamUser>)",
		equivalentConditions = {
			@EquivalentCondition(
				name    = "users of stream",
				valid   = @Condition(nbr = 1, desc = "non-null"),
				invalid = @Condition(nbr = 2, desc = "null")
			)
		},
		boundaries = {
			@Boundary(nbr = 1, desc = "users of null"),
			@Boundary(nbr = 2, desc = "users with empty elements"),
			@Boundary(nbr = 3, desc = "users with null elements")
		}
	)
})
public class InputStreamProviderTest {

	private static int ACTUAL_INVOKE_NUMBER = 0;
	
	@Cover(conditions = "StringUtils.isBlank(path)", invalidECs = 2, boundaries = 1)
	@Test(expected = IllegalArgumentException.class)
	public void testConditionInputStreamProvider1() throws FileNotFoundException {
		new InputStreamProvider(null);
	}
	
	@Cover(conditions = "!new File(path).exist()", invalidECs = 6)
	@Test(expected = FileNotFoundException.class)
	public void testConditionInputStreamProvider2() throws FileNotFoundException {
		new InputStreamProvider(FileUtil.ensureNonExistence("tc5.tmp"));
	}
	
	@Cover(validECs = {1,5})
	@Test
	public void testECInputStreamProvider1() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("tc1.tmp"));
		assertNotNull(provider);
	}
	
	@Cover(invalidECs = 3, boundaries = 2)
	@Test(expected = IllegalArgumentException.class)
	public void testECInputStreamProvider2() throws FileNotFoundException {
		new InputStreamProvider("");
	}
	
	@Cover(invalidECs = 4, boundaries = 3)
	@Test(expected = IllegalArgumentException.class)
	public void testECInputStreamProvider3() throws FileNotFoundException {
		new InputStreamProvider(" ");
	}
	
	@Cover(validECs = 1)
	@Test
	public void testECUsedBy1() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBy1.tmp"));
		provider.usedBy(new InputStreamUser() {
			@Override
			public void use(InputStream in) throws IOException {
				// do nothing and let the stream provide to close
			}
		});
	}
	
	@Cover(invalidECs = 2, boundaries = 1)
	@Test(expected = NullPointerException.class)
	public void testECUsedBy2() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBy2.tmp"));
		provider.usedBy((InputStreamUser)null);
	}
	
	@Cover(boundaries = 2)
	@Test
	public void testBoundaryUsedBy1() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBy3.tmp"));
		provider.usedBy(new InputStreamUser() {
			@Override
			public void use(InputStream in) throws IOException {
				// close the stream in the client code
				in.close();
			}
		});
	}

	@Cover(validECs = 1)
	@Test
	public void testECUsedBySeveralUsers1() throws FileNotFoundException {
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
	
	@Cover(invalidECs = 2, boundaries = 1)
	@Test(expected = NullPointerException.class)
	public void testECUsedBySeveralUsers2() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers2.tmp"));
		provider.usedBy((Collection<InputStreamUser>)null);
	}
	
	@Cover(boundaries = 2)
	@Test
	public void testBoundaryUsedBySeveralUsers1() throws FileNotFoundException {
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers3.tmp"));
		provider.usedBy(Collections.<InputStreamUser>emptySet());
	}
	
	@Cover(boundaries = 3)
	@Test
	public void testBoundaryUsedBySeveralUsers2() throws FileNotFoundException {
		List<InputStreamUser> usersWithNullElements = new ArrayList<InputStreamUser>(16);
		Collections.fill(usersWithNullElements, null);
		
		InputStreamProvider provider = new InputStreamProvider(FileUtil.ensureExistence("usedBySeveralUsers4.tmp"));
		provider.usedBy(usersWithNullElements);
	}
	
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
