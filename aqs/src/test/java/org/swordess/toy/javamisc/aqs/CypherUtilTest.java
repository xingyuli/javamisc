package org.swordess.toy.javamisc.aqs;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class CypherUtilTest {

    @Test
    public void testGenerateTimestampConcurrently() {
        int concurrencyLevel = 100;
        final CountDownLatch prepared = new CountDownLatch(concurrencyLevel);
        final CountDownLatch go = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrencyLevel);
        final Set<Long> timestamps = Collections.synchronizedSet(new HashSet<Long>());

        for (int i = 0; i < concurrencyLevel; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    prepared.countDown();
                    try { go.await(); } catch (InterruptedException e) { fail("unexpected interruption"); }
                    timestamps.add(CypherUtil.INSTANCE.getUniqueTimestamp());
                    done.countDown();
                }
            }).start();
        }

        try {
            prepared.await();
            go.countDown();
            done.await();
        } catch (InterruptedException e) {
            fail("unexpected interruption");
        }

        assertEquals("the count of timestamps not match", concurrencyLevel, timestamps.size());
    }

}
