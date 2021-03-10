package org.swordess.toy.javamisc.threading;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        int poolSize = 2;
        int queueSize = 64;

        // the AbortPolicy will populate the RejectedExecutionException to the caller,
        //   - if this exception is not caught, then the caller thread will be propagated; the successfully submitted tasks might be halt as well
        //   - if this exception is caught, then the caller can still reach the end; the successfully submitted tasks could be handled properly
        runTasksWith("use (default) AbortPolicy", poolSize, queueSize, () -> useDefaultRejectedHandler(poolSize, queueSize));

        // no special setup needed, the task will always be completed
        runTasksWith("use CallerRunsPolicy", poolSize, queueSize, () -> usePolicy(poolSize, queueSize, new ThreadPoolExecutor.CallerRunsPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                System.out.println("task handled by caller");
                super.rejectedExecution(r, e);
            }
        }));

        // DiscardPolicy will leads to task timeout, which impact the `Future.get()`.
        // `Future.get(timeout: Long, unit: TimeUnit)` will survive this.
        //   - if `Future.get()` is used, then the caller thread would be halt, as the rejected task will never be completed
        //   - if `Future.get(timeout, unit)` is used, then the caller thread could be notified by the `TimeoutException`
        runTasksWith("use DiscardPolicy", poolSize, queueSize, () -> usePolicy(poolSize, queueSize, new ThreadPoolExecutor.DiscardPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                System.out.println("task discarded");
                super.rejectedExecution(r, e);
            }
        }));
    }

    private static void runTasksWith(String desc, int poolSize, int queueSize, Supplier<ThreadPoolExecutor> supplier) {
        ThreadPoolExecutor threadPoolExecutor = supplier.get();

        System.out.println(">>> " + desc + " <<<");

        try {
            Exception e = runTasks(desc, poolSize, queueSize, threadPoolExecutor);

            if (e == null) {
                System.out.println("[" + desc + "] reaches the end");
            } else {
                System.out.println("[" + desc + "] didn't reach the end, the cause is: " + stackTraceToString(e));
            }

        } finally {
            System.out.println("[" + desc + "] " + threadPoolExecutor.getCompletedTaskCount() + " tasks completed");

            if (!threadPoolExecutor.isShutdown()) {
                System.out.println("[" + desc + "] need extra shutdown");
                threadPoolExecutor.shutdownNow();
            }

            System.out.println();
        }
    }

    private static Exception runTasks(String desc, int poolSize, int queueSize, ThreadPoolExecutor threadPoolExecutor) {
        List<Future<?>> futures = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(queueSize);

        int triggerMaxQueSize = queueSize + poolSize + 1;

        try {
            for (int i = 0; i < triggerMaxQueSize; i++) {
                try {
                    futures.add(threadPoolExecutor.submit(() -> {
                        try {
                            latch.await();

                            // workload
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            System.out.println("[" + desc + "] interrupted when Runnable.run, the cause is: " + stackTraceToString(e));
                        }
                    }));
                } catch (RejectedExecutionException e) {
                    System.out.println("[" + desc + "] interrupted when ThreadPoolExecutor.submit, the cause is: " + stackTraceToString(e));
                }
                latch.countDown();
            }
        } catch (Exception e) {
            return e;
        }

        System.out.println("[" + desc + "] " + futures.size() + " tasks submitted");

        for (int i = 0; i < futures.size(); i++) {
            Future<?> f = futures.get(i);
            try {
                f.get(5, TimeUnit.SECONDS);

            } catch (InterruptedException | ExecutionException e) {
                System.out.println("[" + desc + "] i = " + i + ", interrupted when Future.get, the cause is: " + stackTraceToString(e));

            } catch (TimeoutException e) {
                System.out.println("[" + desc + "] i = " + i + ", timeout when Future.get, the cause is: " + stackTraceToString(e));
            }
        }

        threadPoolExecutor.shutdownNow();

        return null;
    }

    private static ThreadPoolExecutor useDefaultRejectedHandler(int poolSize, int queueSize) {
        // the default handler is AbortPolicy
        return new ThreadPoolExecutor(poolSize, poolSize, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueSize));
    }

    private static ThreadPoolExecutor usePolicy(int poolSize, int queueSize, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(poolSize, poolSize, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueSize), Executors.defaultThreadFactory(), handler);
    }

    private static String stackTraceToString(Exception e) {
        StringWriter string = new StringWriter();
        PrintWriter writer = new PrintWriter(string);
        e.printStackTrace(writer);
        return string.toString();
    }

}
