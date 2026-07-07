package com.employee.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * EX7 - AuditorAware implementation for JPA Auditing.
 *
 * Spring Data JPA calls getCurrentAuditor() whenever an entity annotated with
 * @CreatedBy or @LastModifiedBy is persisted or updated.
 *
 * In a real application, this would delegate to Spring Security:
 *   SecurityContextHolder.getContext().getAuthentication().getName()
 *
 * For this exercise, a static "system" user is returned.
 *
 * The bean name "auditorProvider" must match the auditorAwareRef in @EnableJpaAuditing.
 */
@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // In production, replace with: SecurityContextHolder.getContext().getAuthentication().getName()
        return Optional.of("system");
    }
}
