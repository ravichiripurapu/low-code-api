package io.lowcode.platform.repository;

import io.lowcode.platform.domain.DocumentMetadataVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentMetadataVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentMetadataVersionRepository extends JpaRepository<DocumentMetadataVersion, String> {}
