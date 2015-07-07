package org.swordess.toy.javamisc.slf4j.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackDemo {

    @Test
    public void test_Chapter1_PrintLoggerStatus() {
        // Logback can report information about its internal state using a
        // built-in status system. Important events occurring during logback's
        // lifetime can be accessed through a component called StatusManager.
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        // Note that in the above example we have instructed logback to print
        // its internal state by invoking the StatusPrinter.print() method.
        // Logback's internal status information can be very useful in
        // diagnosing logback-related problems.

        // Logback explains that having failed to find the logback-test.xml and
        // logback.xml configuration files (discussed later), it configured
        // itself using its default policy, which is a basic ConsoleAppender.
    }

    @Test
    public void test_Chapter2_LevelInheritance() {
        // If a given logger is not assigned a level, then it inherits one from
        // its closest ancestor with an assigned level.

        // To ensure that all loggers can eventually inherit a level, the root
        // logger always has an assigned level. By default, this level is
        // DEBUG.
        Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        Assert.assertTrue(rootLogger.isDebugEnabled());
    }

    @Test
    public void test_Chapter2_RetrieveLoggerWithTheSameName() {
        Logger l1 = LoggerFactory.getLogger("swordess");
        Logger l2 = LoggerFactory.getLogger("swordess");
        Assert.assertSame(l1, l2);
    }

}
