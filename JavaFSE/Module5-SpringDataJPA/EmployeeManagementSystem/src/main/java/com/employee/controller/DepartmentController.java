package com.employee.controller;

import com.employee.entity.Department;
import com.employee.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EX4 - REST Controller exposing CRUD endpoints for Department.
 * Base path: /api/departments
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * POST /api/departments
     * Creates a new department.
     *
     * @param department request body with department data
     * @return 201 Created with the saved department
     */
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department saved = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * GET /api/departments
     * Retrieves all departments.
     *
     * @return 200 OK with list of departments
     */
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    /**
     * GET /api/departments/{id}
     * Retrieves a single department by ID.
     *
     * @param id path variable — the department ID
     * @return 200 OK with the department, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(departmentService.getDepartmentById(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PUT /api/departments/{id}
     * Updates an existing department.
     *
     * @param id         path variable — the department ID to update
     * @param department request body with updated department data
     * @return 200 OK with the updated department, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
                                                       @RequestBody Department department) {
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(id, department));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/departments/{id}
     * Deletes a department by ID.
     *
     * @param id path variable — the department ID to delete
     * @return 204 No Content on success, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
