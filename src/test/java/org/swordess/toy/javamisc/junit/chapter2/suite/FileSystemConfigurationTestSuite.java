package org.swordess.toy.javamisc.junit.chapter2.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({FileConfigurationTest.class, FolderConfigurationTest.class})
public class FileSystemConfigurationTestSuite {

}
