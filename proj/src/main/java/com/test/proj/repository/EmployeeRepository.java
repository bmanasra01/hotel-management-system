package com.test.proj.repository;

import com.test.proj.compositekeys.EmployeeKey;
import com.test.proj.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeKey> {
}
