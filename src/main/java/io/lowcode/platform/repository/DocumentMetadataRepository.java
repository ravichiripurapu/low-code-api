package io.lowcode.platform.repository;

import io.lowcode.platform.domain.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {}
