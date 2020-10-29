package org.swordess.toy.javamisc.exception;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.*;

public class UncaughtExceptionTest {

    @Test
    public void withinExecutorTest() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        // the exception will be recorded in FutureTask.outcome, just like a normal result
        // thus won't impact the executing Thread
        Future<Void> future = executorService.submit(() -> {
            System.out.println("do sth.");
            throw new IllegalArgumentException("ex demo");
        });

        // wait for task completion
        Thread.sleep(1000);

        // task with exception still be counted as completed
        Assert.assertEquals("1 submitted task should be completed", 1, executorService.getCompletedTaskCount());

        Assert.assertTrue("future treated as done", future.isDone());

        try {
            // the exception will be reported when `get()`
            future.get();
            Assert.fail("won't be reached");
        } catch (ExecutionException e) {
            Assert.assertThat(e.getCause(), is(IllegalArgumentException.class));
            Assert.assertEquals("", "ex demo", e.getCause().getMessage());
        }

        Assert.assertEquals("thread num in pool will not be impacted by exception",1, executorService.getPoolSize());

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }

}
