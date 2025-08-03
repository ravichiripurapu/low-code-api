package io.lowcode.platform.repository;

import io.lowcode.platform.domain.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentStatusRepository extends JpaRepository<DocumentStatus, String> {}
