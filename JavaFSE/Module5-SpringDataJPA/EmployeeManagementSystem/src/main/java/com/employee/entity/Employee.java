package com.employee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * EX2  - Entity mapping with @ManyToOne relationship to Department.
 * EX5  - @NamedQueries for JPQL named queries.
 * EX7  - JPA Auditing fields.
 * EX8  - Used in interface/class-based projections.
 * EX10 - Participates in batch insert via saveAll().
 */
@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "Employee.findByEmailNQ",
                query = "SELECT e FROM Employee e WHERE e.email = :email"
        ),
        @NamedQuery(
                name = "Employee.findByDeptNQ",
                query = "SELECT e FROM Employee e WHERE e.department.name = :deptName"
        )
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * EX2: Many employees belong to one department.
     * FetchType.LAZY prevents unnecessary JOIN when loading employees individually.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

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
