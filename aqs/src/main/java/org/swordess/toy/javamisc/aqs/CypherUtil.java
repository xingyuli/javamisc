package org.swordess.toy.javamisc.aqs;

import java.util.concurrent.atomic.AtomicLong;

public enum CypherUtil {

    INSTANCE;

    private static AtomicLong lastTimestamp = new AtomicLong();

    public long getUniqueTimestamp() {
        long latestTimestamp;
        for (;;) {
            long oldLastTimestamp = lastTimestamp.get();
            latestTimestamp = System.currentTimeMillis();
            if (latestTimestamp != oldLastTimestamp && lastTimestamp.compareAndSet(oldLastTimestamp, latestTimestamp)) {
                break;
            }
        }
        return latestTimestamp;
    }

}
