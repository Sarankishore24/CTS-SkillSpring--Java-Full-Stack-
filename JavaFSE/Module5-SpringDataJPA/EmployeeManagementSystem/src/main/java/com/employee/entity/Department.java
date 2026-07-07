package com.employee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * EX2  - Entity mapping with @OneToMany relationship.
 * EX5  - @NamedQuery for JPQL named query.
 * EX7  - JPA Auditing fields (createdDate, lastModifiedDate, createdBy, lastModifiedBy).
 * EX10 - Participates in batch insert via saveAll().
 */
@Entity
@Table(name = "departments")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(
        name = "Department.findByNameNQ",
        query = "SELECT d FROM Department d WHERE d.name = :name"
)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * EX2: One department has many employees.
     * CascadeType.ALL ensures child employees are persisted/removed with the department.
     * FetchType.LAZY avoids loading all employees unless explicitly accessed.
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;

    // ---- EX7: Auditing fields ----

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
