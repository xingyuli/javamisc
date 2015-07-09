package org.swordess.toy.javamisc.springdata.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.swordess.toy.javamisc.springdata.jpa.model.Employee;
import org.swordess.toy.javamisc.springdata.jpa.model.Scoped.Scope;
import org.swordess.toy.javamisc.springdata.jpa.repositories.EmployeeRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.jpa.domain.Specifications.where;
import static org.swordess.toy.javamisc.springdata.jpa.Specs.hasScope;
import static org.swordess.toy.javamisc.springdata.jpa.repositories.EmployeeRepository.Specs.olderThan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:org/swordess/toy/javamisc/springdata/jpa/applicationContext.xml")
@Transactional
public class SpecificationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testHasScopeSpec() {
        employeeRepository.save(newEmployee("vic", 26, "swordess"));
        employeeRepository.save(newEmployee("jack", 40, "titanic"));

        List<Employee> found = employeeRepository.findAll(hasScope(Scope.CLUB_UUID, "swordess"));
        assertEquals(1, found.size());
        assertEquals("vic", found.get(0).getName());
    }

    @Test
    public void testOlderThanSpec() {
        employeeRepository.save(newEmployee("vic", 26, "swordess"));
        employeeRepository.save(newEmployee("jack", 40, "titanic"));

        List<Employee> found = employeeRepository.findAll(olderThan(20), new Sort(Sort.Direction.ASC, "id"));
        assertEquals(2, found.size());
        assertEquals("vic", found.get(0).getName());
        assertEquals("jack", found.get(1).getName());
    }

    @Test
    public void testCombinedSpec() {
        employeeRepository.save(newEmployee("vic", 26, "swordess"));
        employeeRepository.save(newEmployee("jack", 40, "titanic"));

        List<Employee> found1 = employeeRepository.findAll(
                where(olderThan(20)).and(hasScope(Scope.CLUB_UUID, "swordess")));
        assertEquals(1, found1.size());
        assertEquals("vic", found1.get(0).getName());

        List<Employee> found2 = employeeRepository.findAll(
                where(olderThan(30)).and(hasScope(Scope.CLUB_UUID, "swordess")));
        assertEquals(0, found2.size());
    }

    private Employee newEmployee(String name, int age, String clubUUID) {
        Employee e = new Employee();
        e.setName(name);
        e.setAge(age);
        e.setScope(Scope.CLUB_UUID, clubUUID);
        return e;
    }

}
