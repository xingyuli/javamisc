package org.swordess.toy.javamisc.springdata.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swordess.toy.javamisc.springdata.jpa.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
