package org.belev.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByShipId(Long shipId);
}
