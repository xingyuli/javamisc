package org.swordess.toy.javamisc.springdata.jpa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:org/swordess/toy/javamisc/springdata/jpa/applicationContext.xml")
@Transactional
public class SpringDataJPAUnitTest {
}
