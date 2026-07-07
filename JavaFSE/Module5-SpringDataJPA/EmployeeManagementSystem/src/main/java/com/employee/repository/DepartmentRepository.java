package com.employee.repository;

import com.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * EX3 - Basic CRUD via JpaRepository.
 * EX3 - Derived query methods (findByName, findByNameContainingIgnoreCase).
 * EX5 - Named query reference via @Query(name = ...).
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // EX3: Derived query — Spring Data generates SQL from method name
    Optional<Department> findByName(String name);

    // EX3: Derived query — case-insensitive partial match
    List<Department> findByNameContainingIgnoreCase(String keyword);

    // EX5: Named query — delegates to @NamedQuery declared in Department entity
    @Query(name = "Department.findByNameNQ")
    Optional<Department> findByNameNamedQuery(@Param("name") String name);
}
