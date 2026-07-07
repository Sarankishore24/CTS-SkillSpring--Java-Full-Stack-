package com.employee.service;

import com.employee.entity.Department;
import com.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EX4 - Service layer for Department CRUD operations.
 * Uses constructor injection (no @Autowired on field).
 */
@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Creates and persists a new department.
     *
     * @param department the department to create
     * @return the saved department with generated ID
     */
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * Retrieves a department by its ID.
     *
     * @param id the department ID
     * @return the department entity
     * @throws RuntimeException if no department found with the given ID
     */
    @Transactional(readOnly = true)
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    /**
     * Retrieves all departments.
     *
     * @return list of all departments
     */
    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Updates an existing department's name.
     *
     * @param id                the ID of the department to update
     * @param updatedDepartment the department object containing updated fields
     * @return the updated and saved department
     * @throws RuntimeException if no department found with the given ID
     */
    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        existing.setName(updatedDepartment.getName());
        return departmentRepository.save(existing);
    }

    /**
     * Deletes a department by its ID.
     *
     * @param id the ID of the department to delete
     * @throws RuntimeException if no department found with the given ID
     */
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
