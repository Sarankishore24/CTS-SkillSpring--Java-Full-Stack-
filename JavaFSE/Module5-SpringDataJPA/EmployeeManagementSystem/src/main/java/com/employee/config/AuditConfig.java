package com.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * EX7 - Enables JPA Auditing for the application.
 *
 * @EnableJpaAuditing activates Spring Data JPA's auditing infrastructure,
 * which automatically populates @CreatedDate, @LastModifiedDate,
 * @CreatedBy, and @LastModifiedBy fields on entities annotated with
 * @EntityListeners(AuditingEntityListener.class).
 *
 * auditorAwareRef points to the "auditorProvider" bean defined in AuditorAwareImpl.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {
}
