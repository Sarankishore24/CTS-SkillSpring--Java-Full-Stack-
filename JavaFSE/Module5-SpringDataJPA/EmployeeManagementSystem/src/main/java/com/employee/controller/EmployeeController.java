package com.employee.controller;

import com.employee.entity.Employee;
import com.employee.projection.EmployeeNameEmailProjection;
import com.employee.projection.EmployeeSummary;
import com.employee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EX4  - REST Controller for Employee CRUD.
 * EX6  - Pagination and sorting endpoints.
 * EX8  - Projection endpoints.
 * EX10 - Batch insert endpoint.
 * Base path: /api/employees
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ---- EX4: CRUD ----

    /**
     * POST /api/employees
     * Creates a new employee.
     * Request body should include department.id to link the employee to a department.
     *
     * @param employee request body
     * @return 201 Created with the saved employee
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * GET /api/employees
     * Retrieves all employees.
     *
     * @return 200 OK with a list of employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * GET /api/employees/{id}
     * Retrieves a single employee by ID.
     *
     * @param id the employee ID
     * @return 200 OK with the employee, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PUT /api/employees/{id}
     * Updates an existing employee's name, email, and department.
     *
     * @param id       the employee ID to update
     * @param employee request body with updated fields
     * @return 200 OK with updated employee, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/employees/{id}
     * Deletes an employee by ID.
     *
     * @param id the employee ID to delete
     * @return 204 No Content on success, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ---- EX6: Pagination & Sorting ----

    /**
     * GET /api/employees/paginated?page=0&size=10&sortBy=name
     * Returns a paginated and sorted list of all employees.
     *
     * @param page   zero-based page index (default 0)
     * @param size   records per page (default 10)
     * @param sortBy entity field to sort by (default "name")
     * @return 200 OK with a Page of employees
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<Employee>> getPaginatedEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(employeeService.getPaginatedEmployees(page, size, sortBy));
    }

    /**
     * GET /api/employees/search?keyword=john&page=0&size=10
     * Searches employees by name (case-insensitive, partial match), paginated.
     *
     * @param keyword substring to search in employee names
     * @param page    zero-based page index (default 0)
     * @param size    records per page (default 10)
     * @return 200 OK with a Page of matching employees
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(employeeService.searchEmployees(keyword, page, size));
    }

    // ---- EX5: Department-based retrieval ----

    /**
     * GET /api/employees/department/{name}
     * Retrieves all employees belonging to the specified department.
     *
     * @param name the exact department name
     * @return 200 OK with list of employees
     */
    @GetMapping("/department/{name}")
    public ResponseEntity<List<Employee>> getByDepartment(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.getByDepartment(name));
    }

    // ---- EX8: Projections ----

    /**
     * GET /api/employees/projections/{deptName}
     * Returns interface-based projections (name, email, nameWithEmail) for a department.
     *
     * @param deptName the department name to filter by
     * @return 200 OK with list of EmployeeNameEmailProjection
     */
    @GetMapping("/projections/{deptName}")
    public ResponseEntity<List<EmployeeNameEmailProjection>> getProjections(
            @PathVariable String deptName) {
        return ResponseEntity.ok(employeeService.getProjections(deptName));
    }

    /**
     * GET /api/employees/summaries/{deptName}
     * Returns class-based DTO projections (id, name, email, departmentName) for a department.
     *
     * @param deptName the department name to filter by
     * @return 200 OK with list of EmployeeSummary
     */
    @GetMapping("/summaries/{deptName}")
    public ResponseEntity<List<EmployeeSummary>> getSummaries(@PathVariable String deptName) {
        return ResponseEntity.ok(employeeService.getSummaries(deptName));
    }

    // ---- EX10: Batch Insert ----

    /**
     * POST /api/employees/batch
     * Batch-inserts a list of employees using saveAll().
     * Hibernate batches INSERT statements according to spring.jpa.properties.hibernate.jdbc.batch_size.
     *
     * @param employees list of employees to insert
     * @return 201 Created with the saved list
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Employee>> batchInsert(@RequestBody List<Employee> employees) {
        List<Employee> saved = employeeService.batchInsert(employees);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
