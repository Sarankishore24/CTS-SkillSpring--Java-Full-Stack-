package com.employee.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * EX8 - Interface-based projection.
 *
 * Spring Data JPA generates a proxy at runtime that implements this interface.
 * Only the projected columns are fetched, reducing data transfer.
 *
 * The @Value annotation uses SpEL to compute a virtual/derived field
 * by combining existing entity fields — no extra DB column required.
 */
public interface EmployeeNameEmailProjection {

    /** Maps to Employee.name */
    String getName();

    /** Maps to Employee.email */
    String getEmail();

    /**
     * EX8: SpEL expression — virtual field combining name and email.
     * 'target' refers to the underlying entity proxy.
     */
    @Value("#{target.name + ' <' + target.email + '>'}")
    String getNameWithEmail();
}
