package org.swordess.toy.javamisc.slf4j.logback.chapters.appenders;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class SMTPAppenderTest {

    private final Logger logger = LoggerFactory.getLogger(SMTPAppenderTest.class);

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            logger.debug("debug message from unit test");
        }
        logger.warn("this is a warning message");
        logger.error("this is an error message");
    }

}
