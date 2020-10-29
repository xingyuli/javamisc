package org.swordess.toy.javamisc.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationTest {

    private List<String> list = new ArrayList<>();

    @Before
    public void prepare() {
        list.add("one");
        list.add("two");
        list.add("three");
    }

    @After
    public void tearDown() {
        list.clear();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testModificationWithinSameThread() {
        Iterator<String> iter = list.iterator();

        list.add("four");
        iter.next();
    }

}
