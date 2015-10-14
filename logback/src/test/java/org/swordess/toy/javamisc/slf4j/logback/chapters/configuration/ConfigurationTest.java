package org.swordess.toy.javamisc.slf4j.logback.chapters.configuration;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationTest {

    private final Logger logger = LoggerFactory.getLogger(ConfigurationTest.class);

    @Test
    public void test() {
        logger.info("Entering application.");

        // won't be logged as the level of package o.s.t.j.s.Chapters.configuration is set to INFO
        logger.debug("just do something...");

        logger.info("Exiting application.");
    }

}
