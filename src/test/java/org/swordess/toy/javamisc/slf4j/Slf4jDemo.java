package org.swordess.toy.javamisc.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jDemo {

    private final Logger logger = LoggerFactory.getLogger(Slf4jDemo.class);

    @Test
    public void testHelloWorld() {
        logger.info("Hello World");
    }

    @Test
    public void testTypicalUsagePattern() {
        int t = 0;
        int oldT = -7;
        logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);
    }

    @Test
    public void testMisc() {
        logger.debug("Set {1,2} differs from {}", 3);
        logger.debug("Set {1,2} differs from {{}}", 3);
        logger.debug("Set \\{} differs from {}", 3);
        logger.debug("Something went wrong when scenario is {}", "TestingScenario", new RuntimeException("test"));
    }

}
