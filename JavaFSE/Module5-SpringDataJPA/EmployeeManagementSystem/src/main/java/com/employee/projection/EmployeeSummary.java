package com.employee.projection;

/**
 * EX8 - Class-based (DTO) projection.
 *
 * Used with a JPQL constructor expression:
 *   SELECT new com.employee.projection.EmployeeSummary(e.id, e.name, e.email, e.department.name)
 *
 * Unlike interface projections, this is a plain Java class — no proxy overhead.
 * All fields are immutable (final) because the data is read-only.
 */
public class EmployeeSummary {

    private final Long id;
    private final String name;
    private final String email;
    private final String departmentName;

    /**
     * Constructor required by JPQL constructor expression.
     * Parameter order must match the SELECT clause exactly.
     */
    public EmployeeSummary(Long id, String name, String email, String departmentName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public String toString() {
        return "EmployeeSummary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
