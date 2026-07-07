package com.employee.repository;

import com.employee.entity.Employee;
import com.employee.projection.EmployeeNameEmailProjection;
import com.employee.projection.EmployeeSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * EX3  - Basic CRUD and derived query methods.
 * EX5  - @Query JPQL, native SQL, and named queries.
 * EX6  - Pagination and sorting via Pageable.
 * EX8  - Interface-based and class-based (DTO) projections.
 * EX10 - saveAll() used in batch insert.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ---- EX3/EX5: Derived query methods ----

    List<Employee> findByName(String name);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentName(String departmentName);

    List<Employee> findByNameContainingIgnoreCase(String keyword);

    // ---- EX5: @Query — JPQL ----

    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
    List<Employee> findByDepartmentId(@Param("deptId") Long deptId);

    // ---- EX5: @Query — Native SQL ----

    @Query(value = "SELECT * FROM employees WHERE email LIKE %:domain%", nativeQuery = true)
    List<Employee> findByEmailDomain(@Param("domain") String domain);

    // ---- EX5: Named queries declared in Employee entity ----

    @Query(name = "Employee.findByEmailNQ")
    Optional<Employee> findByEmailNamedQuery(@Param("email") String email);

    @Query(name = "Employee.findByDeptNQ")
    List<Employee> findByDeptNamedQuery(@Param("deptName") String deptName);

    // ---- EX6: Pagination ----

    // findAll(Pageable) is inherited from JpaRepository — kept here for clarity
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByDepartmentName(String departmentName, Pageable pageable);

    Page<Employee> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // ---- EX8: Interface-based projection ----

    List<EmployeeNameEmailProjection> findProjectedByDepartmentName(String departmentName);

    // ---- EX8: Class-based (DTO) projection via constructor expression ----

    @Query("SELECT new com.employee.projection.EmployeeSummary(e.id, e.name, e.email, e.department.name) " +
           "FROM Employee e WHERE e.department.name = :deptName")
    List<EmployeeSummary> findSummaryByDepartmentName(@Param("deptName") String deptName);
}
