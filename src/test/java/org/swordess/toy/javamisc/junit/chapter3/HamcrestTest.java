package org.swordess.toy.javamisc.junit.chapter3;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Ignore as this is only a demo for illustrating the Hamcrest matcher")
public class HamcrestTest {

	private List<String> values;
	
	@Before
	public void setUpList() {
		values = new ArrayList<>();
		values.add("x");
		values.add("y");
		values.add("z");
	}
	
	@Test
	public void testWithoutHamcrest() {
		assertTrue(values.contains("one")
				|| values.contains("two")
				|| values.contains("three"));
	}
	
	@Test
	public void testWithHamcrest() {
		assertThat(values, hasItem(anyOf(
				equalTo("one"),
				equalTo("two"),
				equalTo("three")
			)));
	}
	
}
