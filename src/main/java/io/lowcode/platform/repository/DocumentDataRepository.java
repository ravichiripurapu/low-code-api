package io.lowcode.platform.repository;

import io.lowcode.platform.domain.DocumentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentDataRepository extends JpaRepository<DocumentData, Long> {}
