package org.swordess.toy.javamisc.springdata.jpa.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.swordess.toy.javamisc.springdata.jpa.model.Employee;
import org.swordess.toy.javamisc.springdata.jpa.model.Employee_;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    class Specs {

        public static Specification<Employee> olderThan(int age) {
            return (root, query, cb) -> cb.gt(root.get(Employee_.age), age);
        }

    }

}
