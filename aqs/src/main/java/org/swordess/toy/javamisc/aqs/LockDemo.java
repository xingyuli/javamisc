package org.swordess.toy.javamisc.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private static int concurrencyLevel = 10;

    private static long start = System.currentTimeMillis();
    private static ReentrantLock lock = new ReentrantLock();

    private static CountDownLatch ready = new CountDownLatch(concurrencyLevel);
    private static CountDownLatch go = new CountDownLatch(1);
    private static CountDownLatch done = new CountDownLatch(concurrencyLevel);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < concurrencyLevel; i++) {
            spawnThread("thread-" + i).start();
        }

//        ready.await();
//        go.countDown();
        done.await();

        System.out.println("main done");
    }

    private static Thread spawnThread(String name) {
        return new Thread(() -> {
//            ready.countDown();

//            try {
//                go.await();
//            } catch (InterruptedException e) {
//            }

            System.out.println((System.currentTimeMillis() - start) + " " + name + " try lock");
            lock.lock();
            System.out.println((System.currentTimeMillis() - start) + " lock acquired by " + name);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            done.countDown();
        }, name);
    }

}
