package io.lowcode.platform.repository;

import io.lowcode.platform.domain.PublicationFormVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PublicationFormVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationFormVersionRepository extends JpaRepository<PublicationFormVersion, String> {}
