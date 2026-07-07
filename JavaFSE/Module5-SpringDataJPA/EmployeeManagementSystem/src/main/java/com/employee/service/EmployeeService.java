package com.employee.service;

import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.projection.EmployeeNameEmailProjection;
import com.employee.projection.EmployeeSummary;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EX4  - Service layer for Employee CRUD operations.
 * EX6  - Pagination and sorting via Pageable.
 * EX8  - Projection methods returning interface and DTO projections.
 * EX10 - Batch insert via saveAll().
 * Uses constructor injection (no @Autowired on field).
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Creates and persists a new employee.
     * If the employee's department is provided with an ID, the department is resolved first.
     *
     * @param employee the employee to create
     * @return the saved employee with generated ID
     */
    public Employee createEmployee(Employee employee) {
        // Resolve department if only the ID is provided
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(employee.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Department not found with id: " + employee.getDepartment().getId()));
            employee.setDepartment(dept);
        }
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the employee ID
     * @return the employee entity
     * @throws RuntimeException if not found
     */
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    /**
     * Retrieves all employees.
     *
     * @return list of all employees
     */
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Updates an existing employee's name, email, and department.
     *
     * @param id              the ID of the employee to update
     * @param updatedEmployee the employee object containing updated fields
     * @return the updated and saved employee
     * @throws RuntimeException if employee or referenced department not found
     */
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());

        if (updatedEmployee.getDepartment() != null && updatedEmployee.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedEmployee.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Department not found with id: " + updatedEmployee.getDepartment().getId()));
            existing.setDepartment(dept);
        }

        return employeeRepository.save(existing);
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the employee ID
     * @throws RuntimeException if employee not found
     */
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // ---- EX6: Pagination & Sorting ----

    /**
     * Returns a paginated and sorted list of all employees.
     *
     * @param page   zero-based page index
     * @param size   number of records per page
     * @param sortBy the entity field to sort by (e.g. "name", "email")
     * @return a Page of Employee objects
     */
    @Transactional(readOnly = true)
    public Page<Employee> getPaginatedEmployees(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return employeeRepository.findAll(pageable);
    }

    /**
     * Returns a paginated search result for employees whose name contains the keyword.
     *
     * @param keyword case-insensitive substring to search in employee names
     * @param page    zero-based page index
     * @param size    number of records per page
     * @return a Page of matching Employee objects
     */
    @Transactional(readOnly = true)
    public Page<Employee> searchEmployees(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    // ---- EX5: Department-based retrieval ----

    /**
     * Retrieves all employees belonging to a department by department name.
     *
     * @param deptName the exact name of the department
     * @return list of employees in that department
     */
    @Transactional(readOnly = true)
    public List<Employee> getByDepartment(String deptName) {
        return employeeRepository.findByDepartmentName(deptName);
    }

    // ---- EX8: Projections ----

    /**
     * Returns interface-based projections (name + email + computed field) for a department.
     *
     * @param deptName the department name to filter by
     * @return list of EmployeeNameEmailProjection proxies
     */
    @Transactional(readOnly = true)
    public List<EmployeeNameEmailProjection> getProjections(String deptName) {
        return employeeRepository.findProjectedByDepartmentName(deptName);
    }

    /**
     * Returns class-based DTO projections (id, name, email, departmentName) for a department.
     *
     * @param deptName the department name to filter by
     * @return list of EmployeeSummary DTOs
     */
    @Transactional(readOnly = true)
    public List<EmployeeSummary> getSummaries(String deptName) {
        return employeeRepository.findSummaryByDepartmentName(deptName);
    }

    // ---- EX10: Batch Insert ----

    /**
     * Persists a list of employees in a single batch operation.
     * Hibernate's batch_size (configured in application.properties) controls
     * how many INSERT statements are sent per JDBC round-trip.
     *
     * @param employees the list of employees to insert
     * @return the saved list with generated IDs
     */
    public List<Employee> batchInsert(List<Employee> employees) {
        // Resolve departments for each employee before batch save
        employees.forEach(emp -> {
            if (emp.getDepartment() != null && emp.getDepartment().getId() != null) {
                Department dept = departmentRepository.findById(emp.getDepartment().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Department not found with id: " + emp.getDepartment().getId()));
                emp.setDepartment(dept);
            }
        });
        return employeeRepository.saveAll(employees);
    }
}
